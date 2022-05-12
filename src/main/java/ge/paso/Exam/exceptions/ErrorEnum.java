package ge.paso.Exam.exceptions;


import org.springframework.http.HttpStatus;


public enum ErrorEnum {
    USERNAME_ALREADY_EXISTS("მოცემული იუზერნეიმი უკვე არსებობს!", HttpStatus.BAD_REQUEST),
    USERNAME_NOT_EXISTS("გადმოცემული იუზერი არ არსებობს", HttpStatus.BAD_REQUEST),
    PASSWORD_ALREADY_USED("პაროლი უკვე გამოყენებულია", HttpStatus.BAD_REQUEST),
    EMAIL_ALREADY_EXISTS("ასეთი მეილი უკვე არსებობს", HttpStatus.BAD_REQUEST),
    INVALID_EMAIL("მეილი არასწორია!", HttpStatus.BAD_REQUEST),
    INVALID_DATE_FORMAT("დაბადების თარიღის ფორმატი არასწორია, უნდა იყოს ასეთ ფორმატში : (DD/MM/YYYY)", HttpStatus.BAD_REQUEST),
    INVALID_DATE_YEAR("დაბადების წელი არ თავსდება დაშვებულ რეინჯში (1940-2004)", HttpStatus.BAD_REQUEST),
    INVALID_ROLE("როლი არასწორია! როლი უნდა იყოს აქედან ერთ-ერთი - USER, ADMIN, SUPER_ADMIN, ქეისს არააქვს მნიშვნელობა!", HttpStatus.BAD_REQUEST),
    VERIFICATION_CODE_NOT_FOUND("კოდი არასწორია!", HttpStatus.NOT_FOUND),
    VERIFICATION_CODE_DO_NOT_MATCH_ID("მითითებული ვერიფიკაციის კოდი სხვა იუზერს ეკუთვნის!",HttpStatus.BAD_REQUEST),
    ALREADY_CONFIRMED("იუზერი უკვე ვერიფიცირებულია", HttpStatus.BAD_REQUEST),
    CODE_IS_INVALID("კოდს ვადა გაუვიდა!", HttpStatus.BAD_REQUEST),
    SERVER_WITH_THIS_NAME_ALREADY_EXISTS("სერვერი მოცემული სახელით უკვე არსებობს!", HttpStatus.BAD_REQUEST),
    INVALID_SERVER_DATE_FORMAT("სერვერის გაუქმების ვადა არასწორ ფორმატშია გადმოცემული, უნდა იყოს ამ ფორმატში : (DD/MM/YYYY)", HttpStatus.BAD_REQUEST),
    INVALID_SERVER_DATE("სერვერის თარიღი წარულ დროშია მითითებული!", HttpStatus.BAD_REQUEST),
    NO_ACCESS_TO_MODIFY_SERVER("ამ იუზერს არ აქვს სერვერის შექმნის ან სერვერთან კავშირის შეწყვეტის უფლება.", HttpStatus.FORBIDDEN),
    NO_FREE_SERVERS_FOUND("თავისუფალი სერვერი ამ ეტაპზე არ მოიძებნება!", HttpStatus.NOT_FOUND),
    SERVER_DO_NOT_EXISTS("მოცემული სახელით სერვერი არ არსებობს!", HttpStatus.NOT_FOUND),
    SERVER_IS_NOT_FREE("სერვერი ამ ეტაპზე არაა თავისუფალი", HttpStatus.FORBIDDEN),
    SERVER_IS_REMOVED("სერვერი წაშლილია!", HttpStatus.FORBIDDEN),
    SERVER_IS_OWNED_BY_OTHER("სერვერი არ არის მოცემული იუზერის საკუთრება", HttpStatus.FORBIDDEN),
    NO_ACCESS_TO_MODIFY_USER("გადმოწოდებულ იუზერს არ აქვს სხვა იუზერის წაშლის/როლის შეცვლის უფლება", HttpStatus.FORBIDDEN),
    ROLE_CURRENTLY_USING("ეს როლი მოცემულ იუზერს ისედაც აქვს!", HttpStatus.BAD_REQUEST);

    private String message;
    private HttpStatus status;

    ErrorEnum(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
