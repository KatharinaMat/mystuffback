package ee.valiit.mystuffback.persistence.item;

import ee.valiit.mystuffback.controller.item.ItemBasicInfo;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ItemMapper {


    @Mapping(source = "id", target = "itemId")
    @Mapping(source = "name", target = "itemName")
    @Mapping(source = "date", target = "itemDate")
    ItemBasicInfo toItemBasicInfo(Item item);


   List<ItemBasicInfo> toItemBasicInfos(List<Item> items);

}