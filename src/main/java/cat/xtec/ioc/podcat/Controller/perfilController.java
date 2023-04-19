package cat.xtec.ioc.podcat.Controller;

import cat.xtec.ioc.podcat.Model.Canal;
import cat.xtec.ioc.podcat.Model.Podcast;
import cat.xtec.ioc.podcat.Model.Usuari;

import cat.xtec.ioc.podcat.Service.CanalService;
import cat.xtec.ioc.podcat.Service.PodcastService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequestMapping("/")
public class perfilController {

    @Autowired
    private CanalService canalService;
    /*
    @Autowired
    private PodcastService podcastService;
    */
    
    // Pàgina de perfil
    @RequestMapping("perfil")
    public String perfil(Model model, @Param("keyword") String keyword) {
        List<Canal>listsCanals = canalService.listAll(keyword);
        // List<Canal>listsCanals = canalService.getCanalsByUsuari(usuari.getId());
        model.addAttribute("llistaCanals", listsCanals);
        model.addAttribute("keyword", keyword);
        return "perfil";
    }

    /*
    @RequestMapping("filtre")
    public String mostrarFiltrePodcasts(Model model, @Param("keyword") String keyword) {
        List<Canal>listaCanal=canalService.listAll(keyword);
        model.addAttribute("listaCanal",listaCanal);
        model.addAttribute("keyword",keyword);
        return "filtre";
    }

    // Pàgina de perfil ANTERIOR
    @RequestMapping("perfil")
    public String perfil(Model model) {
        return "perfil";
    }
    */
    
    // Nou Canal
    @PostMapping("nouCanal")
    public ModelAndView nouCanal(   @RequestParam("titol") String titol,
                                    @RequestParam("descripcio") String descripcio,
                                    Model model) {
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


    /*
    @PostMapping("modUsuari")
    public RedirectView modUsuari(@RequestParam("username") String username, @RequestParam("password") String password, Model model, HttpSession session) {
        Usuari usuari = usuariService.getUserByUsernameAndPassword(username, password);
        
        if (usuari != null) {
            // Determini el rol de l'usuari
            String rol = usuari.getRol();

            // Agrega l'usuari a la sessió HTTP
            session.setAttribute("usuari", usuari);

            // Redirigeix a l'usuari a la pàgina corresponent segons el seu rol
            if ("admin".equals(rol)) {
                // Agrega l'atribut a la sessió HTTP
                model.addAttribute("usuari", usuari);
                return new RedirectView("/");
            }
        } else {
            // Si les credencials no són vàlides, mostra un missatge d'error
            model.addAttribute("error", "Credencials incorrectes");
            return new RedirectView("/login?error");
        }
    }
    */
    
}
