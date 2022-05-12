package ge.paso.Exam.users;

import ge.paso.Exam.repositories.AppRepository;
import ge.paso.Exam.verification.VerificationCode;
import ge.paso.Exam.verification.VerificationService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private static final String USER_NOT_EXISTS_MSG = "User with username - %s , Does not Exist!";

    private final AppRepository appRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final VerificationService verificationService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = appRepository.findByUserName(username).orElse(null);
        if(user == null) {
            throw new IllegalStateException(String.format(USER_NOT_EXISTS_MSG, username));
        }
        return user;
    }

    public String signUpUser(User user) {
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        appRepository.save(user);

        Random rd = new Random();
        String randomCode = String.format("%04d", rd.nextInt(10000));
//        String randomCode = "1234";
        verificationService.addCode(new VerificationCode(randomCode, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), null, user.getId()));
        return randomCode;
    }

    public void enableUser(int userId) {
        User user =  appRepository.findById(userId).orElse(null);
        if(user != null) {
            user.setEnabled(true);
            appRepository.save(user);
        }
    }
}
