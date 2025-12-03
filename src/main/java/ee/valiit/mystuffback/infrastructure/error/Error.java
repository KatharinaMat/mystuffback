package ee.valiit.mystuffback.infrastructure.error;

import lombok.Getter;

@Getter
public enum Error {
    INCORRECT_CREDENTIALS("Incorrect username or password", 111),
    INVALID_ITEM_ID("Couldn't find primary key 'itemId' with value: %s", 777);

    private final String message;
    private final Integer errorCode;

    Error(String message, Integer errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }
}
