package cat.xtec.ioc.podcat.Controller;

import cat.xtec.ioc.podcat.Model.Canal;
import cat.xtec.ioc.podcat.Model.Podcast;
import cat.xtec.ioc.podcat.Service.PodcastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:8080, http://localhost:8080")
@RequestMapping(path = "api/v1/podcasts")
public class PodcastController {

    @Autowired
    private PodcastService podcastService;

    @GetMapping()
    public List<Podcast> getPodcasts() {
        return podcastService.getPodcasts();
    }

    @GetMapping(path ="/canals-usuaris")
    public List<Podcast> getPodcastsWithCanalsAndUsuaris() {
        return podcastService.getPodcastWithCanalsAndUsuaris();
    }

    @GetMapping(path ="/canals/{id}")
    public List<Podcast> getPodcastsByCanalId(@PathVariable Long id) {
        Canal canal = new Canal();
        canal.setId(id);
        return podcastService.findByCanal(canal);
    }

    @GetMapping(path ="/{id}")
    public ResponseEntity<Podcast> getPodcastById(@PathVariable Long id) {
        Optional<Podcast> podcast = podcastService.findById(id);
        return podcast.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(path ="/generes")
    public List<String> getAllGeneres() {
        return podcastService.getAllGeneres();
    }

    @GetMapping(path ="/data-publicacio")
    public List<LocalDateTime> getDataPublicacio() {
        return podcastService.getDataPublicacio();
    }

    @PostMapping()
    public ResponseEntity<Podcast> addPodcast(@Valid @RequestBody Podcast podcast) {
        Podcast savedPodcast = podcastService.addPodcast(podcast);
        return ResponseEntity.created(URI.create("/podcasts/" + savedPodcast.getId())).body(savedPodcast);
    }

    @PutMapping(path ="/{id}")
    public ResponseEntity<Podcast> updatePodcastById(@Valid @RequestBody Podcast request, @PathVariable Long id) {
        Podcast updatedPodcast = podcastService.updatePodcast(request, id);
        return ResponseEntity.ok(updatedPodcast);
    }

    @DeleteMapping(path ="/{id}")
    public ResponseEntity<Void> deletePodcastById(@PathVariable Long id) {
        if (podcastService.deletePodcastById(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
