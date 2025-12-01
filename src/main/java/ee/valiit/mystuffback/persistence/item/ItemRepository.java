package ee.valiit.mystuffback.persistence.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository {
    public interface ItemRepository extends JpaRepository<Item, Integer> {
        @Query("select i from Item i  where i.user.id = :userId order by i.date, i.name")
        List<Item> findItemBy (Integer userId);
    }
}
