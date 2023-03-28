package cat.xtec.ioc.podcat.Controller;

import cat.xtec.ioc.podcat.Model.Usuari;
import cat.xtec.ioc.podcat.Service.UsuariService;
import jakarta.servlet.http.HttpSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.Model;
import org.springframework.web.servlet.view.RedirectView;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LoginControllerTest {

    @Mock
    private UsuariService usuariService;

    @Mock
    private Model model;

    @Mock
    private HttpSession session;

    @InjectMocks
    private LoginController loginController;

    @Test
    public void loginWithValidCredentialsAdminTest() {

        // SET UP
        Usuari usuari = new Usuari();
        usuari.setRol("admin");

        // WHEN
        when(usuariService.getUserByUsernameAndPassword("admin", "admin")).thenReturn(usuari);

        // EXECUTE
        RedirectView redirectView = loginController.login("admin", "admin", (Model) model, (HttpSession) session);

        // ASSERT
        assertEquals("/admin", redirectView.getUrl());
        verify(session).setAttribute("usuari", usuari);
    }

    @Test
    public void loginWithValidCredentialsUsuariTest() {

        // SET UP
        Usuari usuari = new Usuari();
        usuari.setRol("usuari");

        // WHEN
        when(usuariService.getUserByUsernameAndPassword("usuari1", "12345")).thenReturn(usuari);

        // EXECUTE
        RedirectView redirectView = loginController.login("usuari1", "12345", (Model) model, (HttpSession) session);

        // ASSERT
        assertEquals("/", redirectView.getUrl());
        verify(session).setAttribute("usuari", usuari);
    }

    @Test
    public void loginWithInvalidCredentialsTest() {

        // SET UP

        // WHEN
        when(usuariService.getUserByUsernameAndPassword("", "")).thenReturn(null);

        // EXECUTE
        RedirectView redirectView = loginController.login("", "", (Model) model, (HttpSession) session);

        // ASSERT
        assertEquals("/login?error", redirectView.getUrl());
        verify(session, never()).setAttribute(eq("usuariLogOn"), any(Usuari.class));
    }

    @Test
    public void loginWithUnknownRolTest() {

        // SET UP
        Usuari usuari = new Usuari();
        usuari.setRol("");

        // WHEN
        when(usuariService.getUserByUsernameAndPassword("usuari1", "12345")).thenReturn(usuari);

        // EXECUTE
        RedirectView redirectView = loginController.login("usuari1", "12345", (Model) model, (HttpSession) session);

        // ASSERT
        assertEquals("/login?error", redirectView.getUrl());
        verify(session).setAttribute("usuari", usuari);
    }
}
