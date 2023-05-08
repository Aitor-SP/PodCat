package cat.xtec.ioc.podcat.Controller;

import cat.xtec.ioc.podcat.Model.Podcast;
import cat.xtec.ioc.podcat.Service.ImageService;
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
@RequestMapping(path = "api/v1/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    private final static String PATH = "X:/X/X/X/X/X/PodCat/src/main/resources/static/images/";

    @GetMapping()
    public List<String> getImages() {
        List<String> images = new ArrayList<>();
        try (Stream<Path> files = Files.list(Paths.get(PATH))) {
            files.filter(Files::isRegularFile)
                    .map(Path::toString)
                    .forEach(images::add);
        } catch (IOException e) {
            return null;
        }
        return images;
    }

    @PostMapping("/upload")
    public String uploadImage(@ModelAttribute("podcast") Podcast podcast, @RequestParam("imatge") MultipartFile imageFile) {

        try {
            imageService.uploadImage(podcast, imageFile);
        } catch (IOException e) {
            e.printStackTrace();
            return "S'ha produït un error en crear Podcast.";
        }
        return "Podcast creat correctament.";
    }

    @PutMapping("/update/{id}")
    public String updateImage(@PathVariable Long id, @RequestParam("imatge") MultipartFile imageFile) throws IOException {

        // Cridar al mètode del servei per a actualitzar el Podcast amb el nou arxiu d'imatge
        imageService.updateImage(id, imageFile);

        // Retornar una resposta
        return "Podcast actualitzat correctament";
    }
}
