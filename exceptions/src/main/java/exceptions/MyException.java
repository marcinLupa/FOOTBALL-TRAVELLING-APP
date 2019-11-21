package exceptions;

import lombok.Getter;

@Getter
public class MyException extends RuntimeException {

    private ExceptionInfo exceptionInfo;

    public MyException(String message) {
        this.exceptionInfo = new ExceptionInfo(message);
    }

}
