package cat.xtec.ioc.podcat.Controller;

import cat.xtec.ioc.podcat.Model.Canal;
import cat.xtec.ioc.podcat.Model.Podcast;
import cat.xtec.ioc.podcat.Model.Usuari;
import cat.xtec.ioc.podcat.Repository.PodcastRepository;
import cat.xtec.ioc.podcat.Service.AudioService;
import cat.xtec.ioc.podcat.Service.CanalService;
import cat.xtec.ioc.podcat.Service.PodcastService;
import cat.xtec.ioc.podcat.Service.UsuariService;
import cat.xtec.ioc.podcat.Repository.CanalRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
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
    private UsuariService usuariService;

    @Autowired
    private CanalRepository canalRepository;

    @Autowired
    private PodcastRepository podcastRepository;

    @Autowired
    private AudioService audioService;
    
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
        canal.setUsuari(usuari);
        canal.setTitol(titol);
        canal.setDescripcio(descripcio);
        canalRepository.save(canal);

        ModelAndView modelAndView = new ModelAndView();
        model.addAttribute("iTitol", titol);
        model.addAttribute("iDesc", descripcio);
        modelAndView.setViewName("perfil");
        return modelAndView;
    }

    // Nou Podcast
    @PostMapping("nouPodcast")
    public ModelAndView nouPodcast (    @RequestParam("titol") String titol,
                                        @RequestParam("descripcio") String descripcio,
                                        @RequestParam("genere") String genere,
                                        @RequestParam("etiquetes") String etiquetes,
                                        @RequestParam("imatge") MultipartFile imatge,
                                        @RequestParam("audio") MultipartFile audio,
                                        Model model, HttpSession session){

        Usuari usuari = (Usuari) session.getAttribute("usuari");
        Canal canal = (Canal) session.getAttribute("canal");
        Podcast podcast = new Podcast();

        audioService.uploadAudio(podcast, audio);

        podcast.setUsuari(usuari);
        podcast.setCanal(canal);
        podcast.setTitol(titol);
        podcast.setDescripcio(descripcio);
        podcast.setGenere(genere);
        podcast.setEtiquetes(etiquetes);
        podcast.setImatge(imatge);
        podcast.setAudio(audio);
        podcastRepository.save(podcast);

        ModelAndView modelAndView = new ModelAndView();
        model.addAttribute("titol", titol);
        model.addAttribute("descripcio", descripcio);
        model.addAttribute("genere", genere);
        model.addAttribute("etiquetes", etiquetes);
        model.addAttribute("imatge", imatge);
        model.addAttribute("audio", audio);
        modelAndView.setViewName("perfil");
        return modelAndView;

    }

    // Eliminar Canal
    @PostMapping("eliminaCanal")
    public ModelAndView eliminaCanal(   @RequestParam("eliminaIDcanal") Long eliminaIDcanal,
                                        @RequestParam("eliminaNomCanal") String eliminaNomCanal,
                                        Model model) {
        // Eliminem el Canal
        canalService.deleteCanalById(eliminaIDcanal);
        // Creem el model posterior
        ModelAndView modelAndView = new ModelAndView();
        model.addAttribute("eCanal", eliminaNomCanal);
        modelAndView.setViewName("perfil");
        return modelAndView;
    }

    // Modificar usuari
    @PostMapping("modUsuari")
    public ModelAndView modUsuari(  @RequestParam("username") String username,
                                    @RequestParam("nom") String nom,
                                    @RequestParam("cognom") String cognom,
                                    @RequestParam("email") String email,
                                    HttpSession session, Model model) {
        // Obtenim l'usuari de la sessió
        Usuari usuari = (Usuari) session.getAttribute("usuari");

        usuari.setUsername(username);
        usuari.setNom(nom);
        usuari.setCognom(cognom);
        usuari.setEmail(email);

        // Modifiquem l'usuari
        usuariService.updateUsuariFieldById(usuari, usuari.getId());

        // Set sessió usuari actualitzat
        session.setAttribute("usuari", usuari);

        // Pàgina amb avis d'actualització
        ModelAndView modelAndView = new ModelAndView();
        model.addAttribute("modUsername", username);
        modelAndView.setViewName("perfil");
        return modelAndView;

        // Redirect /perfil
        // return new ModelAndView("redirect:/perfil");

    }
}
