package ee.valiit.mystuffback.service;

import ee.valiit.mystuffback.controller.item.dto.ItemBasicInfo;
import ee.valiit.mystuffback.controller.item.dto.ItemDetails;
import ee.valiit.mystuffback.infrastructure.error.Error;
import ee.valiit.mystuffback.infrastructure.exception.DataNotFoundException;
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
        return itemMapper.toItemBasicInfos(items);
    }

    public ItemDetails findItemDetailsBy(Integer itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() ->
                new DataNotFoundException(String.format(Error.INVALID_ITEM_ID.getMessage(), itemId), Error.INVALID_ITEM_ID.getErrorCode()));
        return itemMapper.toItemDetails(item);
    }
}
