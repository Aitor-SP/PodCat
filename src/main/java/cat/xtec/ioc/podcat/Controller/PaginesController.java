package cat.xtec.ioc.podcat.Controller;

import cat.xtec.ioc.podcat.Model.Canal;

import cat.xtec.ioc.podcat.Model.Podcast;
import cat.xtec.ioc.podcat.Service.CanalService;

import cat.xtec.ioc.podcat.Service.PodcastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/")
public class PaginesController {

    @Autowired
    private CanalService canalService;

    @Autowired
    private PodcastService podcastService;

    @RequestMapping("/")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index.html");
        return modelAndView;
    }

    @RequestMapping("/admin")
    public ModelAndView admin() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin.html");
        return modelAndView;
    }

    @RequestMapping("/contacte")
    public ModelAndView contacte() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("contacte.html");
        return modelAndView;
    }

    @RequestMapping("/podcast")
    public String viewCanalPodcast(Model model) {
        List<Canal>listaCanal = canalService.getCanals();
        List<Podcast>listaPodcast = podcastService.getPodcasts();
        model.addAttribute("listaCanal", listaCanal);
        model.addAttribute("listaPodcast", listaPodcast);
        return "podcast";
    }

    @RequestMapping("/filtre")
    public String mostrarFiltrePodcasts(Model model, @Param("keyword")String keyword) {
        List<Canal>listaCanal=canalService.listAll(keyword);
        model.addAttribute("listaCanal",listaCanal);
        model.addAttribute("keyword",keyword);
        return "filtre";
    }

    /*
    @RequestMapping("/filtre")
    public String mostrarFiltrePodcasts(Model model) {
        List<Podcast>listaPodcast = podcastService.getPodcasts();
        List<Canal>listaCanal = canalService.getCanals();
        model.addAttribute("listaPodcast", listaPodcast);
        model.addAttribute("listaCanal", listaCanal);
        return "filtre";
    }
*/
}
