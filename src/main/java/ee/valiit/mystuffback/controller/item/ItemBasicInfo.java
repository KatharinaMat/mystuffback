package ee.valiit.mystuffback.controller.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link ee.valiit.mystuffback.persistence.item.Item}
 */
@AllArgsConstructor
@Getter
@ToString
public class ItemBasicInfo implements Serializable {
    private final Integer itemId;
    private final String itemName;
    private final LocalDate itemDate;

}