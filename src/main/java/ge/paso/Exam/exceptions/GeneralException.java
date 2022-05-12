package ge.paso.Exam.exceptions;

public class GeneralException extends RuntimeException {

    private ErrorEnum errorEnum;

    public GeneralException(ErrorEnum errorEnum) {
        super(errorEnum.getMessage());
        this.errorEnum = errorEnum;
    }

    public GeneralException(String txt) {
        super(txt);
        this.errorEnum = null;
    }

    public ErrorEnum getErrorEnum() {
        return this.errorEnum;
    }

}
