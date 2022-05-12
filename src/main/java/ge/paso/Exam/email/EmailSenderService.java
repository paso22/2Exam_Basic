package ge.paso.Exam.email;

public interface EmailSenderService {

    void sendMail(String to, String subject, String body);
}
