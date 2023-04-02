package cat.xtec.ioc.podcat.Controller;

import cat.xtec.ioc.podcat.Model.Podcast;
import cat.xtec.ioc.podcat.Service.PodcastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:8080, http://localhost:8080")
@RequestMapping(path = "api/v1/podcasts")
public class PodcastController {

    @Autowired
    private PodcastService podcastService;

    @GetMapping()
    public List<Podcast> getAllPodcasts() {
        return podcastService.getAllPodcasts();
    }

    @GetMapping(path ="/{id}")
    public Optional<Podcast> getPodcastById(@PathVariable("id") Long id) {
        return this.podcastService.getPodcastById(id);
    }

    @PostMapping()
    public Podcast addPodcast(@RequestBody Podcast podcast) {
        return this.podcastService.addPodcast(podcast);
    }

    @PutMapping(path ="/{id}")
    public Podcast updatePodcastById(@RequestBody Podcast request, @PathVariable("id") Long id) {
        return this.podcastService.updatePodcastById(request, id);
    }

    @DeleteMapping(path ="/{id}")
    public String deletePodcastById(@PathVariable("id") Long id) {

        Boolean okDelete = podcastService.deletePodcastById(id);

        if (okDelete){
            return "Podcast amb id: "+ id +" ha sigut eliminat!";
        } else {
            return "No es pot eliminar el podcast. Error.";
        }
    }
}
