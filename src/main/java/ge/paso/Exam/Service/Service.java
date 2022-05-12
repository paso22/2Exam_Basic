package ge.paso.Exam.Service;

import ge.paso.Exam.dto.RegistrationDto;

public interface Service {

    String registration(RegistrationDto registrationDto);

    void verifyRegistration(int userId,String verificationCode);
}
