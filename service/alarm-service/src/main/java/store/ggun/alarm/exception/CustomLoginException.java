package store.ggun.alarm.exception;

import lombok.Getter;
import store.ggun.alarm.domain.vo.ExceptionStatus;

@Getter
public class CustomLoginException extends RuntimeException {
    private final ExceptionStatus status;

    public CustomLoginException(ExceptionStatus status, String message) {
        super(message);
        this.status = status;
    }
}
