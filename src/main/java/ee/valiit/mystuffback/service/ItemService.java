package ee.valiit.mystuffback.service;


import ee.valiit.mystuffback.controller.item.ItemBasicInfo;
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

}
