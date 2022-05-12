package ge.paso.Exam.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.time.LocalDate;

@ControllerAdvice()
@Slf4j
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(value = GeneralException.class)
    public ResponseEntity<Object> handleApiRequestException(GeneralException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        if(e.getErrorEnum() != null) {
            status = e.getErrorEnum().getStatus();
        }
        log.error(e.getMessage() + " , " + status + ", " + LocalDate.now());
        ApiException res = new ApiException(e.getMessage(), status, LocalDate.now());
        return new ResponseEntity<>(res, res.getHttpStatus());
    }
}
