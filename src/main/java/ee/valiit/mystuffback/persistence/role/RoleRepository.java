package ee.valiit.mystuffback.persistence.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query("select (count(r) > 0) from Role r where r.id = :id")
    boolean findBy(Integer id);

    Integer id(Integer id);
}
