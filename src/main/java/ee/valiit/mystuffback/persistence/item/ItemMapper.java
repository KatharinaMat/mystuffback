package ee.valiit.mystuffback.persistence.item;

import ee.valiit.mystuffback.controller.item.dto.ItemBasicInfo;
import ee.valiit.mystuffback.controller.item.dto.ItemDetails;
import ee.valiit.mystuffback.controller.item.dto.ItemDto;
import ee.valiit.mystuffback.infrastructure.status.Status;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", imports = {Status.class})
public interface ItemMapper {


    @Mapping(source = "id", target = "itemId")
    @Mapping(source = "name", target = "itemName")
    @Mapping(source = "date", target = "itemDate")
    ItemBasicInfo toItemBasicInfo(Item item);


    List<ItemBasicInfo> toItemBasicInfos(List<Item> items);


    @Mapping(source = "id", target = "itemId")
    @Mapping(source = "name", target = "itemName")
    @Mapping(source = "date", target = "itemDate")
    @Mapping(source = "model", target = "model")
    @Mapping(source = "comment", target = "comment")
    @Mapping(constant = "", target = "imageData")
    ItemDetails toItemDetails(Item item);

    @Mapping(target = "id", ignore = true)      // new item, id generated
    @Mapping(target = "user", ignore = true)    // we set it in service
    @Mapping(source = "itemName", target= "name")
    @Mapping(source = "itemDate", target= "date")
    @Mapping(source = "model", target= "model")
    @Mapping(source = "comment", target= "comment")
    @Mapping(expression = "java(Status.ACTIVE.getCode())", target = "status")
    Item toItem(ItemDto itemDto);
}