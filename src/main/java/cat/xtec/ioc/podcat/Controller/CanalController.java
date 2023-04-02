package cat.xtec.ioc.podcat.Controller;

import cat.xtec.ioc.podcat.Model.Canal;
import cat.xtec.ioc.podcat.Service.CanalService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping()
    public List<Canal> getAllCanals() {
        return canalService.getAllCanals();
    }

    @GetMapping(path ="/{id}")
    public Optional<Canal> getCanalById(@PathVariable("id") Long id) {
        return this.canalService.getCanalById(id);
    }

    @PostMapping()
    public Canal addCanal(@RequestBody Canal canal) {
        return this.canalService.addCanal(canal);
    }

    @PutMapping(path ="/{id}")
    public Canal updateCanalById(@RequestBody Canal request, @PathVariable("id") Long id) {
        return this.canalService.updateCanalById(request,id);
    }

    @PatchMapping(path ="/{id}")
    public ResponseEntity<Canal> updateCanalAndUsuari(@RequestBody Canal canal,
                                                      @PathVariable("id") Long id,
                                                      @RequestParam Long idUsuari) {
        Canal updatedCanal = canalService.updateCanalAndUsuari(canal, id, idUsuari);
        return ResponseEntity.ok(updatedCanal);
    }

    @DeleteMapping(path ="/{id}")
    public String deleteCanalById(@PathVariable("id") Long id) {

        boolean okDelete = this.canalService.deleteCanalById(id);

        if (okDelete){
            return "Canal amb id: "+ id +" ha sigut eliminat!";
        } else {
            return "No es pot eliminar el canal. Error.";
        }
    }
}
