package ge.paso.Exam.exceptions;

import org.springframework.http.HttpStatus;

import java.time.LocalDate;

public class ApiException {

    private String message;
    private HttpStatus httpStatus;
    private LocalDate date;

    public ApiException(String message, HttpStatus httpStatus, LocalDate date) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
