package cat.xtec.ioc.podcat.Service;

import cat.xtec.ioc.podcat.Model.Canal;
import cat.xtec.ioc.podcat.Model.Podcast;
import cat.xtec.ioc.podcat.Model.Usuari;
import cat.xtec.ioc.podcat.Repository.PodcastRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PodcastServiceTest {

    @Mock
    private PodcastRepository podcastRepository;

    @InjectMocks
    private PodcastService podcastService;

    private Usuari usuari;

    private Canal canal;

    private Podcast podcast;

    private List<Canal> canals;

    private List<Podcast> podcasts;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    void setUp() {
        usuari = new Usuari();
        usuari.setId(1L);
        usuari.setNom("usuari_test");

        canal = new Canal();
        canal.setId(1L);
        canal.setUsuari(usuari);
        canal.setTitol("canal_test");
        canal.setDescripcio("descripcio_test");
        canal.setImatge("imatge_test");

        canals = new ArrayList<>();
        canals.add(canal);

        podcast = new Podcast();
        podcast.setId(1L);
        podcast.setTitol("podcast_test");
        podcast.setDescripcio("descripcio_test");
        podcast.setImatge("imatge_test");
        podcast.setCanal(canal);

        podcasts = new ArrayList<>();
        podcasts.add(podcast);
    }

    @Test
    void getPodcastsTest() {
        // SET UP

        // WHEN
        when(podcastRepository.findAll()).thenReturn(podcasts);

        // EXECUTE
        List<Podcast> result = podcastService.getPodcasts();

        // ASSERT
        assertEquals(podcasts, result);
    }

    @Test
    void getPodcastByIdTest() {
        // SET UP

        // WHEN
        when(podcastRepository.findById(1L)).thenReturn(Optional.of(podcasts.get(0)));

        // EXECUTE
        Optional<Podcast> result = podcastService.getPodcastById(1L);

        // ASSERT
        assertEquals(Optional.of(podcasts.get(0)), result);
    }

    @Test
    void getPodcastsByCanalTest() {
        // SET UP

        // WHEN
        when(podcastRepository.findByCanalId(1L)).thenReturn(podcasts);

        // EXECUTE
        List<Podcast> result = podcastService.getPodcastsByCanal(canal);

        // ASSERT
        assertEquals(podcasts, result);
    }

    @Test
    void getPodcastsByUsuariTest() {
        // SET UP
        Usuari usuari = new Usuari();
        usuari.setId(1L);

        // WHEN
        when(podcastRepository.findByUsuariId(1L)).thenReturn(podcasts);

        // EXECUTE
        List<Podcast> result = podcastService.getPodcastsByUsuari(usuari);

        // ASSERT
        assertEquals(podcasts, result);
    }

    @Test
    void getGeneresTest() {
        // SET UP
        List<String> generes = Arrays.asList("Test1", "Test2");

        // WHEN
        when(podcastRepository.findByGeneres()).thenReturn(generes);

        // EXECUTE
        List<String> result = podcastService.getGeneres();

        // ASSERT
        assertEquals(generes, result);
    }

    @Test
    void getDataPublicacioTest() {
        // SET UP
        List<LocalDateTime> data = Arrays.asList(LocalDateTime.now(), LocalDateTime.now().plusDays(1));

        // WHEN
        when(podcastRepository.findByDataPublicacio()).thenReturn(data);

        // EXECUTE
        List<LocalDateTime> result = podcastService.getDataPublicacio();

        // ASSERT
        assertEquals(data, result);
    }

    @Test
    void addPodcastTest() {
        // SET UP


        // WHEN
        when(podcastRepository.save(podcast)).thenReturn(podcast);

        // EXECUTE
        Podcast result = podcastService.addPodcast(podcast);

        // ASSERT
        assertEquals(podcast, result);
    }

    @Test
    void updateFullPodcastByIdTest() {
        // SET UP
        Podcast podcastUpdate = new Podcast();
        BeanUtils.copyProperties(podcast, podcastUpdate);
        podcastUpdate.setTitol("podcast_test_updated");
        podcastUpdate.setDescripcio("descripcio_test_updated");
        podcastUpdate.setImatge("imatge_test_updated");

        // WHEN
        when(podcastRepository.findById(podcast.getId())).thenReturn(Optional.of(podcast));
        when(podcastRepository.save(any(Podcast.class))).thenReturn(podcastUpdate);

        // EXECUTE
        Podcast result = podcastService.updateFullPodcastById(podcastUpdate, podcast.getId());

        // ASSERT
        verify(podcastRepository, times(1)).findById(podcast.getId());
        verify(podcastRepository, times(1)).save(any(Podcast.class));
        assertEquals(podcastUpdate.getTitol(), result.getTitol());
        assertEquals(podcastUpdate.getDescripcio(), result.getDescripcio());
        assertEquals(podcastUpdate.getImatge(), result.getImatge());
        assertEquals(podcast.getCanal(), result.getCanal());
    }

    @Test
    void updateFieldPodcastByIdTest() {
        // SET UP
        Podcast podcastUpdate = new Podcast();
        podcastUpdate.setId(podcast.getId());
        podcastUpdate.setTitol("podcast_test_updated");
        podcastUpdate.setDescripcio(podcast.getDescripcio());
        podcastUpdate.setImatge(podcast.getImatge());
        podcastUpdate.setCanal(podcast.getCanal());

        // WHEN
        when(podcastRepository.findById(podcast.getId())).thenReturn(Optional.of(podcast));
        when(podcastRepository.save(any(Podcast.class))).thenReturn(podcastUpdate);

        // EXECUTE
        Podcast result = podcastService.updateFieldPodcastById(podcastUpdate, podcast.getId());

        // ASSERT
        verify(podcastRepository, times(1)).findById(podcast.getId());
        verify(podcastRepository, times(1)).save(any(Podcast.class));
        assertEquals(podcastUpdate.getTitol(), result.getTitol());
        assertEquals(podcast.getDescripcio(), result.getDescripcio());
        assertEquals(podcast.getImatge(), result.getImatge());
        assertEquals(podcast.getCanal().getId(), result.getCanal().getId());
    }

    @Test
    void deletePodcastByIdTest() {
        // SET UP


        // WHEN
        doNothing().when(podcastRepository).deleteById(1L);

        // EXECUTE
        Boolean result = podcastService.deletePodcastById(1L);

        // ASSERT
        assertTrue(result);
    }
}
