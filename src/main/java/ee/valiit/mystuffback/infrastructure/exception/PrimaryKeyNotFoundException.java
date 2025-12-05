package ee.valiit.mystuffback.infrastructure.exception;

import lombok.Getter;

@Getter
public class PrimaryKeyNotFoundException extends RuntimeException {
    private final String message;
    private final Integer errorCode;

    public PrimaryKeyNotFoundException(String fieldName, String fieldValue) {
        super("Couldn't find primary key '" + fieldName + "' with value: " + fieldValue);
        this.message = "Couldn't find primary key '" + fieldName + "' with value: " + fieldValue;
        this.errorCode = 777;
    }
}
