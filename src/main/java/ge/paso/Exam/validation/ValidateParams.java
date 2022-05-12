package ge.paso.Exam.validation;

import ge.paso.Exam.dto.RegistrationDto;
import ge.paso.Exam.dto.ServerCreationDto;
import ge.paso.Exam.entities.Server;
import ge.paso.Exam.exceptions.ErrorEnum;
import ge.paso.Exam.exceptions.GeneralException;
import ge.paso.Exam.repositories.AppRepository;
import ge.paso.Exam.repositories.ServerRepository;
import ge.paso.Exam.users.User;
import ge.paso.Exam.users.UserRoles;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.routines.EmailValidator;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Aspect
@Component
@Slf4j
@AllArgsConstructor
public class ValidateParams {

    private static final int MIN_DATE = 1940;
    private static final int MAX_DATE = 2004;

    private final AppRepository appRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ServerRepository serverRepository;

    @Before(value = "(execution(* ge.paso.Exam.controller.RegistrationController.registration(..)) && args(registrationDto, b))", argNames = "registrationDto, b")
    public void validateRegistration(RegistrationDto registrationDto, BindingResult b) {
        notNullChecker(b);
        registrationChecker(registrationDto);
    }

    @Before(value = "(execution(* ge.paso.Exam.controller.ServerController.createServer(..)) && args(serverCreationDto, userName, b))", argNames = "serverCreationDto,userName, b")
    public void validateServerCreation(ServerCreationDto serverCreationDto, String userName, BindingResult b) {
        verifyUser(userName, "ADMIN");
        notNullChecker(b);
        serverCreationChecker(serverCreationDto);
    }

    @Before(value = "(execution(* ge.paso.Exam.controller.ServerController.chooseServer(..)) && args(userName, serverName))", argNames = "userName,serverName")
    public void validateChoosingServer(String userName, String serverName) {
        verifyUser(userName, "USER");
        choosingServerChecker(serverName);
    }

    @Before(value = "(execution(* ge.paso.Exam.controller.ServerController.releaseServer(..)) && args(userName, serverName))", argNames = "userName,serverName")
    public void validateServerRelease(String userName, String serverName) {
        verifyUser(userName, "USER");
        releasingServerChecker(userName, serverName);
    }

    @Before(value = "(execution(* ge.paso.Exam.controller.ServerController.deleteUser(..)) && args(superAdminName, toDeleteUser))", argNames = "superAdminName,toDeleteUser")
    public void validateDeletingUser(String superAdminName, String toDeleteUser) {
        verifyUserForDeletion(superAdminName, toDeleteUser);
    }

    @Before(value = "(execution(* ge.paso.Exam.controller.ServerController.changeRole(..)) && args(superAdminName, secondUser, role))", argNames = "superAdminName,secondUser, role")
    public void validateChangeRole(String superAdminName, String secondUser, String role) {
        verifyUserForDeletion(superAdminName, secondUser);
        User u = appRepository.findByUserName(secondUser).orElse(null);
        roleChecker(role, u.getRole());
    }

    private void roleChecker(String role, String currRole) {
        if(!role.equalsIgnoreCase(UserRoles.USER.name()) && !role.equalsIgnoreCase(UserRoles.ADMIN.name()) && !role.equalsIgnoreCase(UserRoles.SUPER_ADMIN.name())) {
            throw new GeneralException(ErrorEnum.INVALID_ROLE);
        }
        else if(role.equalsIgnoreCase(currRole)) {
            throw new GeneralException(ErrorEnum.ROLE_CURRENTLY_USING);
        }
    }

    private void verifyUserForDeletion(String superAdminName, String toDeleteUser) {
        User u = appRepository.findByUserName(superAdminName).orElse(null);
        User u2 = appRepository.findByUserName(toDeleteUser).orElse(null);
        if(u == null || u2 == null) {
            throw new GeneralException(ErrorEnum.USERNAME_NOT_EXISTS);
        }

        if(!u.getRole().equalsIgnoreCase("SUPER_ADMIN")) {
            throw new GeneralException(ErrorEnum.NO_ACCESS_TO_MODIFY_USER);
        }
    }

    private void releasingServerChecker(String userName, String serverName) {
        Server s = serverRepository.findByName(serverName).orElse(null);
        User u = appRepository.findByUserName(userName).orElse(null);
        if(s == null) {
            throw new GeneralException(ErrorEnum.SERVER_DO_NOT_EXISTS);
        }
        if(s.getUserId() == null || s.getUserId() != u.getId()) {
            throw new GeneralException(ErrorEnum.SERVER_IS_OWNED_BY_OTHER);
        }
    }

    private void choosingServerChecker(String serverName) {
        Server s = serverRepository.findByName(serverName).orElse(null);
        if(s == null) {
            throw new GeneralException(ErrorEnum.SERVER_DO_NOT_EXISTS);
        }
        if(s.getUserId() != null) {
            throw new GeneralException(ErrorEnum.SERVER_IS_NOT_FREE);
        }
        if(s.getStatus().equalsIgnoreCase("R")) {
            throw new GeneralException(ErrorEnum.SERVER_IS_REMOVED);
        }
    }

    private void verifyUser(String userName, String role) {
        User u = appRepository.findByUserName(userName).orElse(null);
        if(u == null) {
            throw new GeneralException(ErrorEnum.USERNAME_NOT_EXISTS);
        }
        if(!u.getRole().equalsIgnoreCase(role)) {
            throw new GeneralException(ErrorEnum.NO_ACCESS_TO_MODIFY_SERVER);
        }
    }


    private void serverCreationChecker(ServerCreationDto serverCreationDto) {
        String name = serverCreationDto.getName();
        String endDate = serverCreationDto.getDeleteDate();

        if(serverRepository.findByName(name).isPresent()) {
            throw new GeneralException(ErrorEnum.SERVER_WITH_THIS_NAME_ALREADY_EXISTS);
        }
        if(!GenericValidator.isDate(endDate, "dd/MM/yyyy", true)) {
            throw new GeneralException(ErrorEnum.INVALID_SERVER_DATE_FORMAT);
        }
        int day = Integer.parseInt(endDate.substring(0,2));
        int month = Integer.parseInt(endDate.substring(3,5));
        int year = Integer.parseInt(endDate.substring(6));
        if(LocalDateTime.of(year,month,day,0,0).isBefore(LocalDateTime.now())) {
            throw new GeneralException(ErrorEnum.INVALID_SERVER_DATE);
        }

    }

    private void registrationChecker(RegistrationDto registrationDto) {
        String username = registrationDto.getUserName();
        String email = registrationDto.getEmail();
        String password = registrationDto.getPassword();
        String date = registrationDto.getBirthDate();

        if(appRepository.findByUserName(username).isPresent()) {
            throw new GeneralException(ErrorEnum.USERNAME_ALREADY_EXISTS);
        }
        List<String> passwords = appRepository.getAllPassword();
        for(String p : passwords) {
            if(bCryptPasswordEncoder.matches(password, p)) {
                throw new GeneralException(ErrorEnum.PASSWORD_ALREADY_USED);
            }
        }

        if(appRepository.findByEmail(email).isPresent()) {
            throw new GeneralException(ErrorEnum.EMAIL_ALREADY_EXISTS);
        }
        checkMailAndGenderAndDate(registrationDto);
    }

    private void checkMailAndGenderAndDate(RegistrationDto registrationDto) {
        String email = registrationDto.getEmail();
        String role = registrationDto.getRole();
        String birthdate = registrationDto.getBirthDate();

        if(!role.equalsIgnoreCase("user") && !role.equalsIgnoreCase("admin")
          && !role.equalsIgnoreCase("super_admin")  ) {
            throw  new GeneralException(ErrorEnum.INVALID_ROLE);
        }
        if(!EmailValidator.getInstance().isValid(email)) {
            throw new GeneralException(ErrorEnum.INVALID_EMAIL);
        }
        int userBirthDate = Integer.parseInt(birthdate.substring(birthdate.length() - 4));
        if(!GenericValidator.isDate(birthdate, "dd/MM/yyyy", true)) {
            throw new GeneralException(ErrorEnum.INVALID_DATE_FORMAT);
        }
        if(userBirthDate < MIN_DATE || userBirthDate > MAX_DATE) {
            throw new GeneralException(ErrorEnum.INVALID_DATE_YEAR);
        }
        // ლამაზად და ერთ სტილში რომ იყოს მეტნაკლებად ცხრილში ჩანაწერები :)
        registrationDto.setEmail(email.toLowerCase());
        registrationDto.setRole(role.toUpperCase());
        registrationDto.setFirstName(registrationDto.getFirstName().toUpperCase());
        registrationDto.setLastName(registrationDto.getLastName().toUpperCase());

    }

    private void notNullChecker(BindingResult b) {
        if(b.hasErrors()) {
            String resError;
            for(Object o : b.getAllErrors()) {
                if(o instanceof FieldError) {
                    FieldError fe = (FieldError) o;
                    resError = fe.getDefaultMessage();
                    throw new GeneralException(fe.getField() + " - " + resError);
                }
            }
        }
    }

}
