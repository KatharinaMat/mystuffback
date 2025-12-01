package ee.valiit.mystuffback.controller.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link ee.valiit.mystuffback.persistence.item.Item}
 */
@AllArgsConstructor
@Data
public class ItemDetails implements Serializable {
    private Integer itemId;
    private String itemName;
    private LocalDate itemDate;
    private String model;
    private String comment;
    private String imageData;
    private String imageQR;
}