package cat.xtec.ioc.podcat.Controller;

import cat.xtec.ioc.podcat.Model.Usuari;
import cat.xtec.ioc.podcat.Service.UsuariService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class LoginController {

    @Autowired
    private UsuariService usuariService;

    @PostMapping("/login")
    public RedirectView login(@RequestParam("username") String username, @RequestParam("password") String password, Model model) {

        Usuari usuari = usuariService.getUserByUsernameAndPassword(username, password);
        if (usuari != null) {
            // Determini el rol de l'usuari
            String rol = usuari.getRol();

            // Redirigeix a l'usuari a la pàgina corresponent segons el seu rol
            if ("admin".equals(rol)) {
                // Agrega l'atribut a la sessió HTTP
                model.addAttribute("usuariLogOn", usuari);
                return new RedirectView("/admin");
            } else if ("usuari".equals(rol)) {
                // Agrega l'atribut a la sessió HTTP
                model.addAttribute("usuariLogOn", usuari);
                return new RedirectView("/");
            } else {
                // Si el rol és desconegut, mostra un missatge d'error
                model.addAttribute("error", "Rol desconegut");
                return new RedirectView("/guia_estil.html");
            }
        } else {
            // Si les credencials no són vàlides, mostra un missatge d'error
            model.addAttribute("error", "Credencials incorrectes");
            return new RedirectView("/login?error");
        }
    }
}