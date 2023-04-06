package cat.xtec.ioc.podcat.Service;

import cat.xtec.ioc.podcat.Model.Canal;
import cat.xtec.ioc.podcat.Model.Podcast;
import cat.xtec.ioc.podcat.Repository.PodcastRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PodcastService {

    @Autowired
    private PodcastRepository podcastRepository;

    public List<Podcast> getPodcasts() {
        return podcastRepository.findAll().stream()
                .map(podcast -> {
                   Podcast newPodcast = new Podcast();
                   newPodcast.setId(podcast.getId());
                   newPodcast.setTitol(podcast.getTitol());
                   newPodcast.setDescripcio(podcast.getDescripcio());
                   newPodcast.setGenere(podcast.getGenere());
                   newPodcast.setEtiquetes(podcast.getEtiquetes());
                   newPodcast.setDataPublicacio(podcast.getDataPublicacio());
                   newPodcast.setFil(podcast.getFil());
                   newPodcast.setImatge(podcast.getImatge());
                   newPodcast.setAudio(podcast.getAudio());
                   return newPodcast;
                }).collect(Collectors.toList());
    }

    public List<Podcast> getPodcastWithCanalsAndUsuaris() {
        return podcastRepository.findAll();
    }

    public List<String> getAllGeneres() {
        return podcastRepository.findByGeneres();
    }

    public List<LocalDateTime> getDataPublicacio() {
        return podcastRepository.findByDataPublicacio();
    }

    public Optional<Podcast> findById(Long id) {
        return podcastRepository.findById(id);
    }

    public List<Podcast> findByCanal(Canal canal) {
        return podcastRepository.findByCanal(canal);
    }

    public Podcast addPodcast(Podcast podcast) {
        return podcastRepository.save(podcast);
    }

    public Podcast updatePodcast(Podcast request, Long id) {
        Podcast podcast = podcastRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Podcast no trobat"));

        podcast.setTitol(request.getTitol());
        podcast.setDescripcio(request.getDescripcio());
        podcast.setGenere(request.getGenere());
        podcast.setEtiquetes(request.getEtiquetes());
        podcast.setDataPublicacio(request.getDataPublicacio());
        podcast.setFil(request.getFil());
        podcast.setImatge(request.getImatge());
        podcast.setAudio(request.getAudio());

        return podcastRepository.save(podcast);
    }

    public Boolean deletePodcastById(Long id) {
        try {
            podcastRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
