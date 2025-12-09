package ee.valiit.mystuffback.persistence.itemimage;

import ee.valiit.mystuffback.persistence.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ItemImageRepository extends JpaRepository<ItemImage, Integer> {
    @Query("select i from ItemImage i where i.item.id = :itemId")
    Optional<ItemImage> findItemImageBy(Integer itemId);

    @Transactional
    @Modifying
    @Query("delete from ItemImage  i where i.item = :item")
    int deleteItemImagesBy(Item item);
}
