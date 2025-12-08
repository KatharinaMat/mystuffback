package ee.valiit.mystuffback.controller.item;


import ee.valiit.mystuffback.controller.item.dto.ItemBasicInfo;
import ee.valiit.mystuffback.controller.item.dto.ItemDetails;
import ee.valiit.mystuffback.infrastructure.error.ApiError;
import ee.valiit.mystuffback.controller.item.dto.ItemDto;
import ee.valiit.mystuffback.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor

public class ItemController {
    private final ItemService itemService;

    @PostMapping("/item")
    @Operation(summary = "Addition of a new item", description = "itemName and Date are mandatory fields")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "403", description = "Item with this name already exists",
                content = @Content(schema = @Schema(implementation = ApiError.class)))})
    public void addItem(@RequestParam @Valid ItemDto itemDto) {
        itemService.addItem(itemDto);
    }

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

