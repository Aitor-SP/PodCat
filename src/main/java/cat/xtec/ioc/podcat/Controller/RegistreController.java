package cat.xtec.ioc.podcat.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RegistreController {

    @RequestMapping("/registre")
    public ModelAndView index () {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("registre.html");
        return modelAndView;
    }

    /*
    @RequestMapping("/registre")
    @ResponseBody
    public String registre00() {
        return "Wello Horld!";
    }
    
    @RequestMapping(value = "/registre", method = RequestMethod.GET)
    public String registre00(ModelMap model) {
        model.addAttribute("salutacio", "Hola Mon de nou Spring amb Java configuration");
        return "registre";
    }
    */
}
