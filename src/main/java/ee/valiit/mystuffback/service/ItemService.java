package ee.valiit.mystuffback.service;

import ee.valiit.mystuffback.controller.item.dto.ItemBasicInfo;
import ee.valiit.mystuffback.controller.item.dto.ItemDetails;
import ee.valiit.mystuffback.controller.item.dto.ItemDto;
import ee.valiit.mystuffback.infrastructure.exception.ForbiddenException;
import ee.valiit.mystuffback.infrastructure.exception.PrimaryKeyNotFoundException;
import ee.valiit.mystuffback.infrastructure.util.BytesConverter;
import ee.valiit.mystuffback.persistence.item.Item;
import ee.valiit.mystuffback.persistence.item.ItemMapper;
import ee.valiit.mystuffback.persistence.item.ItemRepository;
import ee.valiit.mystuffback.persistence.itemimage.ItemImage;
import ee.valiit.mystuffback.persistence.itemimage.ItemImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static ee.valiit.mystuffback.infrastructure.error.Error.ITEM_NAME_UNAVAILABLE;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final UserService userService;
    @Value("${mystuff.server.address}")
    private String serverAddress;

    @Value("${mystuff.item.path}")
    private String itemPath;

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;
    private final ItemImageRepository itemImageRepository;

    @Transactional
    public void addItem(ItemDto itemDto) {
        validateItemNameIsAvailable(itemDto.getItemName());
        Item item = createAndAddItem(itemDto);
        handleAddItemImage(item, itemDto.getImageData());
    }

    private Item createAndAddItem(ItemDto itemDto) {
        Item item = itemMapper.toItem(itemDto);
        itemRepository.save(item);
        return item;
    }
    private void handleAddItemImage(Item item, String imageData) {
        if (hasImage(imageData)) {
            createAndSaveItemImage(item, imageData);
        }
    }
    private static boolean hasImage(String imageData) {
        return !imageData.isEmpty();
    }

    private void createAndSaveItemImage(Item item, String imageData) {
        ItemImage itemImage = createItemImage(item, imageData);
        itemImageRepository.save(itemImage);
    }

    private static ItemImage createItemImage(Item item, String imageData) {
        ItemImage itemImage = new ItemImage();
        itemImage.setItem(item);
        itemImage.setImageData(BytesConverter.stringToBytes(imageData));
        return itemImage;
    }

    private void validateItemNameIsAvailable(String itemName) {
        boolean itemExists = itemRepository.itemExistsBy(itemName);
        if (itemExists) {
            throw new ForbiddenException(ITEM_NAME_UNAVAILABLE.getMessage(), ITEM_NAME_UNAVAILABLE.getErrorCode());
        }
    }

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
        Optional<ItemImage> optionalItemImage = itemImageRepository.findItemImageBy(itemId);
        if (optionalItemImage.isPresent()) {
            ItemImage itemImage = optionalItemImage.get();
            addImageDataToItemDetails(itemImage, itemDetails);
        }
    }

    private void addImageDataToItemDetails(ItemImage itemImage, ItemDetails itemDetails) {
        byte[] itemImageData = itemImage.getImageData();
        itemDetails.setImageData(BytesConverter.bytesToString(itemImageData));
    }

    private String constructImageQrLink(Integer itemId) {
        return serverAddress + itemPath + itemId;
    }

    public Item getValidItem(Integer itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new PrimaryKeyNotFoundException("itemId", itemId));
    }


}
