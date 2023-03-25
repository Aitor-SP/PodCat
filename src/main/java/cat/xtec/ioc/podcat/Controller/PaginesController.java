package cat.xtec.ioc.podcat.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class PaginesController {

    @RequestMapping("/")
    public ModelAndView index () {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index.html");
        return modelAndView;
    }

    @RequestMapping("/admin")
    public ModelAndView admin () {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin.html");
        return modelAndView;
    }

    @RequestMapping("/contacte")
    public ModelAndView contacte () {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("contacte.html");
        return modelAndView;
    }

}
