package ee.valiit.mystuffback.controller.item;


import ee.valiit.mystuffback.controller.item.dto.ItemBasicInfo;
import ee.valiit.mystuffback.controller.item.dto.ItemDetails;
import ee.valiit.mystuffback.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor

public class ItemController {
    private final ItemService itemService;

    @GetMapping("/items")
    @Operation(summary = "Returns a list of user's items by date and name.")
    public List<ItemBasicInfo> findItems(@RequestParam Integer userId) {
        return itemService.findItems(userId);
    }

    @GetMapping("/item")
    @Operation(summary = "Returns all details of a chosen item")
    public ItemDetails findItemDetails(@RequestParam Integer itemId) {
        return itemService.findItemDetails(itemId);
    }

}

