package cat.xtec.ioc.podcat.Controller;

import cat.xtec.ioc.podcat.Model.Usuari;
import cat.xtec.ioc.podcat.Repository.UsuariRepository;
import cat.xtec.ioc.podcat.Service.UsuariService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:8080, http://localhost:8080")
@RequestMapping(path = "api/v1/usuaris")
public class UsuariController {

    @Autowired
    private UsuariService usuariService;

    @Autowired
    private UsuariRepository usuariRepository;

    @GetMapping()
    public ArrayList<Usuari> getAllUsuaris() {
        return this.usuariService.getAllUsuaris();
    }

    @GetMapping(path = "/{id}")
    public Optional<Usuari> getUsuariById(@PathVariable("id") Long id) {
        return this.usuariService.getUsuariById(id);
    }

    @PostMapping
    public Usuari addUsuari(@RequestBody Usuari usuari){
        return this.usuariService.addUsuari(usuari);
    }

    @PutMapping(path = "/{id}")
    public Usuari updateFullUsuariById(@RequestBody Usuari request,@PathVariable("id") Long id){
        return this.usuariService.updateFullUsuariById(request,id);
    }

    @PatchMapping(path = "/{id}")
    public Usuari updateUsuariFieldById(@RequestBody Usuari request,@PathVariable("id") Long id){
        return this.usuariService.updateUsuariFieldById(request,id);
    }

    @DeleteMapping(path = "/{id}")
    public String deleteUsuariById(@PathVariable("id") Long id){
        boolean okDelete = this.usuariService.deleteUsuariById(id);

        if (okDelete){
            return "Usuari amb id: "+ id +" ha sigut eliminat!";
        } else {
            return "No es pot eliminar l'usuari. Error.";
        }
    }

    @PostMapping("/registre")
    @Transactional
    public RedirectView registreUsuari(@RequestParam("username") String username,
                                       @RequestParam("nom") String nom,
                                       @RequestParam("cognom") String cognom,
                                       @RequestParam("email") String email,
                                       @RequestParam("password") String password) {
        Usuari usuari = new Usuari();
        usuari.setUsername(username);
        usuari.setNom(nom);
        usuari.setCognom(cognom);
        usuari.setEmail(email);
        usuari.setPassword(password);
        usuari.setRol("usuari"); // rol per defecte
        usuariRepository.save(usuari);
        return new RedirectView("/guia_estil.html");
    }
}

