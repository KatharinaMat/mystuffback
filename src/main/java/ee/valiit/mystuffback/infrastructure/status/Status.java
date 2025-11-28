package ee.valiit.mystuffback.infrastructure.status;

import lombok.Getter;

@Getter
public enum Status {
    ACTIVE("A"),
    SOFT_DELETED("D");

    private final String code;

    Status(String code) {
        this.code = code;
    }

}
