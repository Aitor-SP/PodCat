package cat.xtec.ioc.podcat.Controller;

import cat.xtec.ioc.podcat.Model.Canal;
import cat.xtec.ioc.podcat.Model.Podcast;
import cat.xtec.ioc.podcat.Model.Usuari;
import cat.xtec.ioc.podcat.Service.CanalService;
import cat.xtec.ioc.podcat.Service.PodcastService;
import cat.xtec.ioc.podcat.Repository.CanalRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/")
public class PerfilController {

    @Autowired
    private CanalService canalService;

    @Autowired
    private PodcastService podcastService;

    @Autowired
    private CanalRepository canalRepository;
    
    // Pàgina de perfil
    @RequestMapping("perfil")
    public String perfil(Model model, @Param("keyword") String keyword, HttpSession session) {
        Usuari usuari = (Usuari) session.getAttribute("usuari");
        List<Canal> listsCanals = canalService.getCanalsByUsuari(usuari);
        List<Podcast> listsPodcasts = podcastService.getPodcastsByUsuari(usuari);
        model.addAttribute("llistaCanals", listsCanals);
        model.addAttribute("llistaPodcasts", listsPodcasts);
        model.addAttribute("keyword", keyword);
        return "perfil";
    }
    
    // Nou Canal
    @PostMapping("nouCanal")
    public ModelAndView nouCanal(   @RequestParam("titol") String titol,
                                    @RequestParam("descripcio") String descripcio,
                                    Model model, HttpSession session) {
        Usuari usuari = (Usuari) session.getAttribute("usuari");
        Canal canal = new Canal();
        canal.setUsuari(usuari); // (!) Falta agafar la variable usuari o el username de la sessió
        canal.setTitol(titol);
        canal.setDescripcio(descripcio);
        canalRepository.save(canal);

        ModelAndView modelAndView = new ModelAndView();
        model.addAttribute("iTitol", titol);
        model.addAttribute("iDesc", descripcio);
        modelAndView.setViewName("perfil");
        return modelAndView;
    }

    // Modificar usuari
    @PostMapping("modUsuari")
    public ModelAndView modUsuari(  @RequestParam("username") String username,
                                    @RequestParam("nom") String nom,
                                    @RequestParam("cognom") String cognom,
                                    @RequestParam("email") String email,
                                    Model model) {
        ModelAndView modelAndView = new ModelAndView();
        model.addAttribute("uUsername", username);
        model.addAttribute("uNom", nom);
        model.addAttribute("uCognom", cognom);
        model.addAttribute("uEmail", email);
        modelAndView.setViewName("perfil");
        return modelAndView;
    }
}
