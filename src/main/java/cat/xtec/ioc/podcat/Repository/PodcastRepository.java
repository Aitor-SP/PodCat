package cat.xtec.ioc.podcat.Repository;

import cat.xtec.ioc.podcat.Model.Podcast;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PodcastRepository extends JpaRepository<Podcast,Long> {
}
