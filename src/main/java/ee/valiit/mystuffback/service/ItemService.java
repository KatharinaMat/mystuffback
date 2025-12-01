package ee.valiit.mystuffback.service;


import ee.valiit.mystuffback.controller.item.dto.ItemBasicInfo;
import ee.valiit.mystuffback.controller.item.dto.ItemDetails;
import ee.valiit.mystuffback.infrastructure.exception.PrimaryKeyNotFoundException;
import ee.valiit.mystuffback.persistence.item.Item;
import ee.valiit.mystuffback.persistence.item.ItemMapper;
import ee.valiit.mystuffback.persistence.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    public List<ItemBasicInfo> findItemBy(Integer userId) {
        List<Item> items = itemRepository.findActiveItemsBy(userId);
        List<ItemBasicInfo> itemBasicInfos = itemMapper.toItemBasicInfos(items);
        return itemBasicInfos;

    }

    public ItemDetails findItemDetailsBy(Integer itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new PrimaryKeyNotFoundException("itemId", itemId));
        ItemDetails itemDetails = itemMapper.toItemDetails(item);

        //    @Mapping(source = "ON VAJA AGA EI SAA HETKEL", target = "imageData")
//    @Mapping(source = "ON VAJA AGA EI SAA HETKEL", target = "imageQR")


        return itemDetails;
    }


}
