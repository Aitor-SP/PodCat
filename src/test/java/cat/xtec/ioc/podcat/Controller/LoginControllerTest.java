package cat.xtec.ioc.podcat.Controller;

import cat.xtec.ioc.podcat.Model.Usuari;
import cat.xtec.ioc.podcat.Service.UsuariService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.Model;
import org.springframework.web.servlet.view.RedirectView;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginControllerTest {

    @Mock
    private UsuariService usuariService;

    @Mock
    private Model model;

    @InjectMocks
    private LoginController loginController;

    @Test
    public void testLoginWithValidCredentialsAdmin() {

        // SET UP
        Usuari usuari = new Usuari();
        usuari.setRol("admin");

        // WHEN
        when(usuariService.getUserByUsernameAndPassword("admin", "admin")).thenReturn(usuari);
        RedirectView redirectView = loginController.login("admin", "admin", model);

        // ASSERT
        assertEquals("/admin", redirectView.getUrl());
    }

    @Test
    public void testLoginWithValidCredentialsUsuari() {

        // SET UP
        Usuari usuari = new Usuari();
        usuari.setRol("usuari");

        // WHEN
        when(usuariService.getUserByUsernameAndPassword("usuari1", "12345")).thenReturn(usuari);
        RedirectView redirectView = loginController.login("usuari1", "12345", model);

        // ASSERT
        assertEquals("/", redirectView.getUrl());
    }

    @Test
    public void testLoginWithInvalidCredentials() {

        // SET UP


        // WHEN
        when(usuariService.getUserByUsernameAndPassword("", "")).thenReturn(null);
        RedirectView redirectView = loginController.login("", "", model);

        // ASSERT
        assertEquals("/login?error", redirectView.getUrl());
    }

    @Test
    public void testLoginWithUnknownRol() {

        // SET UP
        Usuari usuari = new Usuari();
        usuari.setRol("");

        // WHEN
        when(usuariService.getUserByUsernameAndPassword("usuari1", "12345")).thenReturn(usuari);
        RedirectView redirectView = loginController.login("usuari1", "12345", model);

        // ASSERT
        assertEquals("/guia_estil.html", redirectView.getUrl());
    }
}
