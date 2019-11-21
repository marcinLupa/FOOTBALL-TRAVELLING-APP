package exceptions;

import lombok.Getter;

import java.time.LocalDateTime;
@Getter

public class ExceptionInfo {

    private String message;
    private LocalDateTime exceptionTime;

    public ExceptionInfo(String message) {
        this.message = message;
        this.exceptionTime=LocalDateTime.now();
    }
}
