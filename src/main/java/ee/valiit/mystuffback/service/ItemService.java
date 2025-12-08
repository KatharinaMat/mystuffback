package ee.valiit.mystuffback.service;

import ee.valiit.mystuffback.controller.item.dto.ItemBasicInfo;
import ee.valiit.mystuffback.controller.item.dto.ItemDetails;
import ee.valiit.mystuffback.infrastructure.error.Error;
import ee.valiit.mystuffback.infrastructure.exception.DataNotFoundException;
import ee.valiit.mystuffback.infrastructure.exception.PrimaryKeyNotFoundException;
import ee.valiit.mystuffback.infrastructure.util.BytesConverter;
import ee.valiit.mystuffback.persistence.item.Item;
import ee.valiit.mystuffback.persistence.item.ItemMapper;
import ee.valiit.mystuffback.persistence.item.ItemRepository;
import ee.valiit.mystuffback.persistence.itemimage.Image;
import ee.valiit.mystuffback.persistence.itemimage.ItemImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {

    @Value("${mystuff.server.address}")
    private String serverAddress;

    @Value("${mystuff.item.path}")
    private String itemPath;

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;
    private final ItemImageRepository itemImageRepository;

    public List<ItemBasicInfo> findItems(Integer userId) {
        List<Item> items = itemRepository.findActiveItemsBy(userId);
        return itemMapper.toItemBasicInfos(items);
    }

    public ItemDetails findItemDetails(Integer itemId) {
        Item item = getValidItem(itemId);
        ItemDetails itemDetails = itemMapper.toItemDetails(item);
        itemDetails.setImageQrLink(constructImageQrLink(itemId));
        handleAddImageDataToItemDetails(itemId, itemDetails);
        // todo> imageData ja imageQR mappimata
        return itemDetails;
    }

    private void handleAddImageDataToItemDetails(Integer itemId, ItemDetails itemDetails) {
        Optional<Image> optionalItemImage = itemImageRepository.findItemImageBy(itemId);
        if (optionalItemImage.isPresent()) {
            Image itemImage = optionalItemImage.get();
            addImageDataToItemDetails(itemImage, itemDetails);
        }
    }

    private void addImageDataToItemDetails(Image itemImage, ItemDetails itemDetails) {
        byte[] itemImageData = itemImage.getImageData();
        itemDetails.setImageData(BytesConverter.bytesToString(itemImageData));
    }

    private String constructImageQrLink(Integer itemId) {
        return serverAddress + itemPath + itemId;
    }

    public Item getValidItem(Integer itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new PrimaryKeyNotFoundException("itemId", itemId.toString()));
    }
}
