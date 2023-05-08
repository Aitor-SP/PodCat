package cat.xtec.ioc.podcat.Controller;

import cat.xtec.ioc.podcat.Model.Podcast;
import cat.xtec.ioc.podcat.Service.AudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@RestController
@CrossOrigin(origins = {"http://127.0.0.1:8080", "http://localhost:8080"})
@RequestMapping(path = "api/v1/audio")
public class AudioController {

    @Autowired
    private AudioService audioService;

    private final static String PATH = "X:/X/X/X/X/X/PodCat/src/main/resources/static/audios/";

    @GetMapping()
    public List<String> getAudios() {
        List<String> audios = new ArrayList<>();
        try (Stream<Path> files = Files.list(Paths.get(PATH))) {
            files.filter(Files::isRegularFile)
                    .map(Path::toString)
                    .forEach(audios::add);
        } catch (IOException e) {
            return null;
        }
        return audios;
    }

    @PostMapping("/upload")
    public String uploadAudio(@ModelAttribute("podcast") Podcast podcast, @RequestParam("audio") MultipartFile audioFile) {

        try {
            audioService.uploadAudio(podcast, audioFile);
        } catch (IOException e) {
            e.printStackTrace();
            return "S'ha produït un error en crear Podcast.";
        }
        return "Podcast creat correctament.";
    }

    @PutMapping("/update/{id}")
    public String updateAudio(@PathVariable Long id, @RequestParam("audio") MultipartFile audioFile) throws IOException {

        // Cridar al mètode del servei per a actualitzar el Podcast amb el nou arxiu d'àudio
        audioService.updateAudio(id, audioFile);

        // Retornar una resposta
        return "Podcast actualitzat correctament";
    }
}