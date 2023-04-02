package cat.xtec.ioc.podcat.Service;

import cat.xtec.ioc.podcat.Model.Canal;
import cat.xtec.ioc.podcat.Model.Podcast;
import cat.xtec.ioc.podcat.Model.Usuari;
import cat.xtec.ioc.podcat.Repository.CanalRepository;
import cat.xtec.ioc.podcat.Repository.PodcastRepository;
import cat.xtec.ioc.podcat.Repository.UsuariRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;


@Service
public class PodcastService {

    @Autowired
    private PodcastRepository podcastRepository;

    @Autowired
    private UsuariRepository usuariRepository;

    @Autowired
    private CanalRepository canalRepository;

    public List<Podcast> getAllPodcasts() {
        return podcastRepository.findAll();
    }

    public Optional<Podcast> getPodcastById(Long id) {
        return podcastRepository.findById(id);
    }

    public Podcast addPodcast(Podcast podcast) {

        Usuari usuari = usuariRepository.findById(podcast.getUsuari().getId())
                .orElseThrow(() -> new EntityNotFoundException("Usuari no trobat"));

        Canal canal = canalRepository.findById(podcast.getCanal().getId())
                .orElseThrow(() -> new EntityNotFoundException("Canal no trobat"));

        podcast.setUsuari(usuari);
        podcast.setCanal(canal);

        return podcastRepository.save(podcast);
    }

    public Podcast updatePodcastById(Podcast request, Long id) {
        Podcast podcast = podcastRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Podcast no trobat"));

        Usuari usuari = usuariRepository.findById(request.getUsuari().getId())
                .orElseThrow(() -> new EntityNotFoundException("Usuari no trobat"));

        Canal canal = canalRepository.findById(request.getCanal().getId())
                .orElseThrow(() -> new EntityNotFoundException("Canal no trobat"));

        podcast.setUsuari(usuari);
        podcast.setCanal(canal);

        if (request.getFil() != null) {
            podcast.setFil(request.getFil());
        }

        if (request.getTitol() != null && !request.getTitol().isEmpty()) {
            podcast.setTitol(request.getTitol());
        }

        if (request.getDescripcio() != null && !request.getDescripcio().isEmpty()) {
            podcast.setDescripcio(request.getDescripcio());
        }

        if (request.getEtiquetes() != null && !request.getEtiquetes().isEmpty()) {
            podcast.setEtiquetes(request.getEtiquetes());
        }

        if (request.getDataPublicacio() != null) {
            podcast.setDataPublicacio(request.getDataPublicacio());
        } else {
            throw new IllegalArgumentException("La data de publicació no pot ser nul·la");
        }

        if (request.getImatge() != null && !request.getImatge().isEmpty()) {
            podcast.setImatge(request.getImatge());
        }

        if (request.getAudio() != null && !request.getAudio().isEmpty()) {
            podcast.setAudio(request.getAudio());
        }

        return podcastRepository.save(podcast);
    }

    public Boolean deletePodcastById(Long id) {
        try {
            podcastRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }
}
