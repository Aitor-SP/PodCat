package cat.xtec.ioc.podcat.Service;

import cat.xtec.ioc.podcat.Model.Podcast;
import cat.xtec.ioc.podcat.Repository.PodcastRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class AudioService {

    @Autowired
    private PodcastRepository podcastRepository;

    public void uploadAudio(Podcast podcast, MultipartFile audioFile) throws IOException {

        // Desar l'arxiu d'àudio en el servidor
        String audioFileName = StringUtils.cleanPath(Objects.requireNonNull(audioFile.getOriginalFilename()));
        Path audioPath = Paths.get("classpath:static/audios/" + audioFileName);
        Files.copy(audioFile.getInputStream(), audioPath, StandardCopyOption.REPLACE_EXISTING);

        // Establir la ruta de l'arxiu en la propietat "àudio" de l'objecte Podcast
        podcast.setAudio("classpath:static/audios/" + audioFileName);

        // Guardar l'objecte Podcast en la base de dades
        podcastRepository.save(podcast);
    }

    public void updateAudio(Long id, MultipartFile audioFile) throws IOException {

        // Obtenir el Podcast per ID
        Optional<Podcast> optionalPodcast = podcastRepository.findById(id);

        if (optionalPodcast.isPresent()) {
            Podcast podcast = optionalPodcast.get();

            // Desar l'arxiu d'àudio en el servidor
            String audioFileName = StringUtils.cleanPath(Objects.requireNonNull(audioFile.getOriginalFilename()));
            Path audioPath = Paths.get("classpath:static/audios/" + audioFileName);
            Files.copy(audioFile.getInputStream(), audioPath, StandardCopyOption.REPLACE_EXISTING);

            // Establir la ruta de l'arxiu en la propietat "àudio" de l'objecte Podcast
            podcast.setAudio("classpath:static/audios/" + audioFileName);

            // Actualitzar l'objecte Podcast en la bbdd
            podcastRepository.save(podcast);

        } else {
            throw new EntityNotFoundException("No es va trobar el Podcast amb ID " + id);
        }
    }
}
