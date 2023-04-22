package cat.xtec.ioc.podcat.Controller;

import cat.xtec.ioc.podcat.Model.Canal;
import cat.xtec.ioc.podcat.Service.CanalService;
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
    
    // Pàgina de perfil
    @RequestMapping("perfil")
    public String perfil(Model model, @Param("keyword") String keyword) {
        List<Canal>listsCanals = canalService.listAll(keyword);
        // List<Canal>listsCanals = canalService.getCanalsByUsuari(usuari.getId());
        model.addAttribute("llistaCanals", listsCanals);
        model.addAttribute("keyword", keyword);
        return "perfil";
    }
    
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
}
