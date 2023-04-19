package cat.xtec.ioc.podcat.Controller;

import cat.xtec.ioc.podcat.Model.Canal;

import cat.xtec.ioc.podcat.Model.Podcast;
import cat.xtec.ioc.podcat.Service.CanalService;

import cat.xtec.ioc.podcat.Service.PodcastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequestMapping("/")
public class perfilController {
    
    // Nou Canal
    @PostMapping("nouCanal")
    public RedirectView nouCanal() {
        return new RedirectView("/perfil");
    }

    // Modificar usuari
    @PostMapping("modUsuari")
    public RedirectView modUsuari() {
        return new RedirectView("/perfil");
    }

}
