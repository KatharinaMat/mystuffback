package ee.valiit.mystuffback.persistence.item;

import ee.valiit.mystuffback.controller.item.dto.ItemBasicInfo;
import ee.valiit.mystuffback.controller.item.dto.ItemDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
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
    ItemDetails toItemDetails(Item item);

}