package cat.xtec.ioc.podcat.Controller;

import cat.xtec.ioc.podcat.Model.Canal;
import cat.xtec.ioc.podcat.Model.Podcast;
import cat.xtec.ioc.podcat.Model.Usuari;
import cat.xtec.ioc.podcat.Service.CanalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:8080, http://localhost:8080")
@RequestMapping(path = "api/v1/canals")
public class CanalController {

    @Autowired
    private CanalService canalService;

    @GetMapping
    public List<Canal> getCanals() {
        return canalService.getCanals();
    }

    @GetMapping(path = "/{id}")
    public Optional<Canal> getCanalById(@PathVariable("id") Long id) {
        return this.canalService.getCanalById(id);
    }

    @GetMapping(path = "/usuari/{id}")
    public List<Canal> getCanalsByUsuari(@PathVariable("id") Long id) {
        Usuari usuari = new Usuari();
        usuari.setId(id);
        return canalService.getCanalsByUsuari(usuari);
    }

    @GetMapping(path = "/{id}/podcasts")
    public List<Podcast> getPodcastsByCanal(@PathVariable("id") Long id) {
        return canalService.getPodcastsByCanal(id);
    }

    @PostMapping()
    public Canal addCanal(@RequestBody Canal canal) {
        return this.canalService.addCanal(canal);
    }

    @PutMapping(path = "/{id}")
    public Canal updateFullCanalById(@RequestBody Canal request, @PathVariable("id") Long id) {
        return this.canalService.updateFullCanalById(request, id);
    }

    @PatchMapping(path = "/{id}")
    public Canal updateFieldCanalById(@RequestBody Canal request, @PathVariable("id") Long id) {
        return this.canalService.updateFieldCanalById(request, id);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteCanalById(@PathVariable("id") Long id) {

        boolean okDelete = canalService.deleteCanalById(id);

        if (okDelete) {
            return ResponseEntity.ok("Canal amb id: " + id + " ha sigut eliminat!");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No es pot eliminar el canal. Error.");
        }
    }
}
