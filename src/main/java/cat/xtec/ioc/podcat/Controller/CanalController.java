package cat.xtec.ioc.podcat.Controller;

import cat.xtec.ioc.podcat.Model.Canal;
import cat.xtec.ioc.podcat.Model.Podcast;
import cat.xtec.ioc.podcat.Model.Usuari;
import cat.xtec.ioc.podcat.Service.CanalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:8080, http://localhost:8080")
@RequestMapping(path = "api/v1/canals")
public class CanalController {

    @Autowired
    private CanalService canalService;

    @GetMapping()
    public List<Canal> getCanals() {
        return canalService.getCanals();
    }

    @GetMapping(path ="/canalsUsuaris")
    public List<Canal> getCanalsWithUsuaris() {
        return canalService.getCanalsWithUsuaris();
    }

    @GetMapping(path ="/{id}")
    public ResponseEntity<Canal> getCanalById(@PathVariable Long id) {
        Optional<Canal> canal = canalService.getCanalById(id);
        return canal.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(path ="/usuari/{id}")
    public List<Canal> getCanalsByUser(@PathVariable Long id) {
        Usuari usuari = new Usuari();
        usuari.setId(id);
        return canalService.getCanalsByUsuari(usuari);
    }

    @GetMapping(path ="/{id}/podcasts")
    public List<Podcast> getPodcastsByCanal(@PathVariable Long id) {
        return canalService.getPodcastsByCanal(id);
    }

    @PostMapping
    public ResponseEntity<Canal> addCanal(@RequestBody Canal canal) {
        Canal addedCanal = canalService.addCanal(canal);
        return ResponseEntity.created(URI.create("/canals/" + addedCanal.getId())).body(addedCanal);
    }

    @PutMapping(path ="/{id}")
    public ResponseEntity<Canal> updateCanalById(@RequestBody Canal request, @PathVariable Long id) {
        Canal updatedCanal = canalService.updateCanal(request, id);
        return ResponseEntity.ok(updatedCanal);
    }

    @DeleteMapping(path ="/{id}")
    public ResponseEntity<Void> deleteCanalById(@PathVariable Long id) {
        if (canalService.deleteCanalById(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
