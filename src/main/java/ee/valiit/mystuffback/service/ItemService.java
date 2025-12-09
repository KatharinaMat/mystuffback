package ee.valiit.mystuffback.service;

import ee.valiit.mystuffback.controller.item.dto.ItemBasicInfo;
import ee.valiit.mystuffback.controller.item.dto.ItemDto;
import ee.valiit.mystuffback.controller.user.dto.UserDto;
import ee.valiit.mystuffback.infrastructure.exception.ForbiddenException;
import ee.valiit.mystuffback.infrastructure.exception.PrimaryKeyNotFoundException;
import ee.valiit.mystuffback.infrastructure.util.BytesConverter;
import ee.valiit.mystuffback.persistence.item.Item;
import ee.valiit.mystuffback.persistence.item.ItemMapper;
import ee.valiit.mystuffback.persistence.item.ItemRepository;
import ee.valiit.mystuffback.persistence.itemimage.ItemImage;
import ee.valiit.mystuffback.persistence.itemimage.ItemImageRepository;
import ee.valiit.mystuffback.persistence.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static ee.valiit.mystuffback.infrastructure.error.Error.ITEM_NAME_UNAVAILABLE;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;
    private final ItemImageRepository itemImageRepository;
    private final UserService userService;
    private final UserDto userDto;


    @Transactional
    public void addItem(Integer userId, ItemDto itemDto) {
        validateItemNameIsAvailable(itemDto.getItemName());
        User user = userService.getValidUser(userId);
        Item item = itemMapper.toItem(itemDto);
        item.setUser(user);
        itemRepository.save(item);
        handleAddItemImage(item, itemDto.getImageData());
    }

    private void handleAddItemImage(Item item, String imageData) {
        if (hasImage(imageData)) {
            createAndSaveItemImage(item, imageData);
        }
    }

    private static boolean hasImage(String imageData) {
        return imageData != null && !imageData.isBlank();
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

    public ItemDto findItem(Integer itemId) {
        Item item = getValidItem(itemId);
        ItemDto itemDto = itemMapper.toItemDto(item);
        handleAddImageDataToItemDto(itemId, itemDto);
        return itemDto;
    }

    private void handleAddImageDataToItemDto(Integer itemId, ItemDto itemDto) {
        Optional<ItemImage> optionalItemImage = itemImageRepository.findItemImageBy(itemId);
        if (optionalItemImage.isPresent()) {
            ItemImage itemImage = optionalItemImage.get();
            addImageDataToItemDto(itemImage, itemDto);
        }
    }

    private void addImageDataToItemDto(ItemImage itemImage, ItemDto itemDto) {
        byte[] itemImageData = itemImage.getImageData();
        itemDto.setImageData(BytesConverter.bytesToString(itemImageData));
    }


    public Item getValidItem(Integer itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new PrimaryKeyNotFoundException("itemId", itemId));
    }

    /*public void updateItem(Integer itemId, ItemDto itemDto) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found with id:" + itemId));
        item.setName(itemDto.getItemName());
        item.setDate(itemDto.getItemDate());
        item.setModel(itemDto.getModel());
        item.setComment(itemDto.getComment());

        itemRepository.save(item);
    }*/

    public void updateItemInfo(Integer itemId, ItemDto itemDto) {
        Item item = getValidItem(itemId);
        itemMapper.updateItem(item, itemDto);
        updateItemImage(itemDto, item);

    }

    private void updateItemImage(ItemDto itemDto, Item item) {
        itemImageRepository.deleteItemImagesBy(item);
        handleAddItemImage(item, itemDto.getImageData());
    }

}
