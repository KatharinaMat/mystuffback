package ee.valiit.mystuffback.controller.item;


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
    public List<ItemBasicInfo> findItemsBy(@RequestParam Integer userId) {
        List<ItemBasicInfo> itemBasicInfos = itemService.findItemBy(userId);
        return itemBasicInfos;
    }
}

