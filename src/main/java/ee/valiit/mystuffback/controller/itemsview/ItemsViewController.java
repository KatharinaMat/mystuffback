package ee.valiit.mystuffback.controller.itemsview;


import ee.valiit.mystuffback.service.ItemsViewService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor

public class ItemViewController {
    private final ItemsViewService itemsViewService {
        @GetMapping("/items")
        @Operation(summary = "Lists user's items by name and date.")
    //public ???? findValidItems(Integer userId) {
    // return itemsViewService.findValidItems(userId)}
    }
}
