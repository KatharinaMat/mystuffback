package ee.valiit.mystuffback.infrastructure.error;

import lombok.Getter;

@Getter
public enum Error {
    INCORRECT_CREDENTIALS("Incorrect username or password", 111),
    USERNAME_UNAVAILABLE("This username already exists", 222);
    private final String message;
    private final Integer errorCode;

    Error(String message, Integer errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }
}
