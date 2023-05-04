package cat.xtec.ioc.podcat.Controller;

import cat.xtec.ioc.podcat.Model.Podcast;
import cat.xtec.ioc.podcat.Service.AudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://127.0.0.1:8080", "http://localhost:8080"})
@RequestMapping(path = "api/v1/audio")
public class AudioController {

    @Autowired
    private AudioService audioService;

    @GetMapping()
    public List<String> getAudios() {

        List<String> audios = new ArrayList<>();
        ClassLoader classLoader = getClass().getClassLoader();
        File folder = new File(classLoader.getResource("static/audios/").getFile());
        File[] fileList = folder.listFiles();
        for (File file : fileList) {
            if (file.isFile()) {
                audios.add("static/audios/" + file.getName());
            }
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