package ee.valiit.mystuffback.controller.item;


import ee.valiit.mystuffback.controller.item.dto.ItemBasicInfo;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor

public class ItemController {
    private final ItemService itemService;

    @PostMapping("/item")
    @Operation(summary = "Addition of a new item", description = "itemName and Date are mandatory fields")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "403", description = "Item with this name already exists (errorCode 333)",
                content = @Content(schema = @Schema(implementation = ApiError.class)))})
    public void addItem(@RequestParam Integer userId, @RequestBody @Valid ItemDto itemDto) {
        itemService.addItem(userId, itemDto);
    }

    @GetMapping("/items")
    @Operation(summary = "Returns a list of user's items by date and name.")
    public List<ItemBasicInfo> findItems(@RequestParam Integer userId) {
        return itemService.findItems(userId);
    }

    @GetMapping("/item")
    @Operation(summary = "Returns all details of a chosen item")
    public ItemDto findItem(@RequestParam Integer itemId) {
        return itemService.findItem(itemId);
    }

}

