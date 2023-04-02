package cat.xtec.ioc.podcat.Repository;

import cat.xtec.ioc.podcat.Model.Canal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CanalRepository extends JpaRepository<Canal, Long> {
}
