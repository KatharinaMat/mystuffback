package ee.valiit.mystuffback.persistence.itemimage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ItemImageRepository extends JpaRepository <Image, Integer>{
    @Query("select i from Image i where i.item.id = :itemId")
    Optional<Image> findItemImageBy(Integer itemId);
}
