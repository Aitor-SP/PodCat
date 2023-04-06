package cat.xtec.ioc.podcat.Repository;

import cat.xtec.ioc.podcat.Model.Canal;
import cat.xtec.ioc.podcat.Model.Podcast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@EnableJpaRepositories
@Repository
public interface PodcastRepository extends JpaRepository<Podcast,Long> {

    List<Podcast> findByCanal(Canal canal);

    @Query("SELECT DISTINCT p.genere FROM Podcast p")
    List<String> findByGeneres();

    @Query("SELECT p.dataPublicacio FROM Podcast p")
    List<LocalDateTime> findByDataPublicacio();
}
