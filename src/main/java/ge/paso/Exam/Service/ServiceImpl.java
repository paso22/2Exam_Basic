package ge.paso.Exam.Service;


import ge.paso.Exam.dto.RegistrationDto;
import ge.paso.Exam.email.EmailSenderService;
import ge.paso.Exam.exceptions.ErrorEnum;
import ge.paso.Exam.exceptions.GeneralException;
import ge.paso.Exam.users.UserService;
import ge.paso.Exam.users.User;
import ge.paso.Exam.repositories.AppRepository;
import ge.paso.Exam.verification.VerificationCode;
import ge.paso.Exam.verification.VerificationCodesRepository;
import ge.paso.Exam.verification.VerificationService;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class ServiceImpl implements Service {

    private static final String VALIDATION_LINK = "localhost:8089/api/verify?userId=%s&verificationCode=";
    private static final String EMAIL_SUBJECT = "Registration Verification";
    private static final String EMAIL_BODY = "Your Verification Code : ";

    private final AppRepository appRepository;
    private final UserService userService;
    private final EmailSenderService emailSenderService;
    private final VerificationCodesRepository verificationCodesRepository;
    private final VerificationService verificationService;

    @Override
    public String registration(RegistrationDto registrationDto) {
        User user = dtoToEntity(registrationDto);
        appRepository.save(user);
        String verificationCode = userService.signUpUser(user);
        String text = String.format(VALIDATION_LINK, user.getId());
        emailSenderService.sendMail(registrationDto.getEmail(), EMAIL_SUBJECT, EMAIL_BODY + verificationCode);
        return "to enter your code, go to : " +  text + " and pass your code to it";
    }

    @Override
    public void verifyRegistration(int userId,String verificationCode) {
        VerificationCode vc = verificationCodesRepository.findByCode(verificationCode)
                .orElseThrow( () -> new GeneralException(ErrorEnum.VERIFICATION_CODE_NOT_FOUND));
        if(vc.getId() != userId) {
            throw new GeneralException(ErrorEnum.VERIFICATION_CODE_DO_NOT_MATCH_ID);
        }
        if(vc.getConfirmedAt() != null) {
            throw new GeneralException(ErrorEnum.ALREADY_CONFIRMED);
        }
        if(vc.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new GeneralException(ErrorEnum.CODE_IS_INVALID);
        }
        verificationService.setCodeDetails(vc);
        userService.enableUser(userId);

    }

    private User dtoToEntity(RegistrationDto registrationDto) {
        return new User(registrationDto.getFirstName(),
                         registrationDto.getLastName(),
                         registrationDto.getUserName(),
                         registrationDto.getPassword(),
                         registrationDto.getAge(),
                         registrationDto.getBirthDate(),
                         registrationDto.getEmail(),
                         registrationDto.getRole(),
                         false,
                         false);
    }
}
