package cat.xtec.ioc.podcat.Service;

import cat.xtec.ioc.podcat.Model.Usuari;
import cat.xtec.ioc.podcat.Repository.UsuariRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UsuariService {

    private final UsuariRepository usuariRepository;

    @Autowired
    public UsuariService(UsuariRepository usuariRepository) {
        this.usuariRepository = usuariRepository;
    }

    public ArrayList<Usuari> getAllUsuaris() {
        return new ArrayList<>(usuariRepository.findAll());
    }

    public Optional<Usuari> getUsuariById(Long id) {
        return usuariRepository.findById(id);
    }

    public Usuari findUserByEmail(String email) {
        return usuariRepository.findByEmail(email);
    }

    public Usuari addUsuari(Usuari usuari) {
        return usuariRepository.save(usuari);
    }

    public Usuari updateFullUsuariById(Usuari request, Long id) {
        Usuari usuari = usuariRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Usuari no trobat"));
        // Aquí podries fer validacions dels camps de l'objecte request abans d'actualitzar-los
        usuari.setId(request.getId());
        usuari.setUsername(request.getUsername());
        usuari.setPassword(request.getPassword());
        usuari.setNom(request.getNom());
        usuari.setCognom(request.getCognom());
        usuari.setEmail(request.getEmail());
        usuari.setRol(request.getRol());

        return usuariRepository.save(usuari);
    }

    public Usuari updateUsuariFieldById(Usuari request, Long id) {
        Usuari usuari = usuariRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Usuari no trobat"));
        // Aquí podries fer validacions dels camps de l'objecte request abans d'actualitzar-los
        usuari.setUsername(request.getUsername());
        usuari.setNom(request.getNom());
        usuari.setCognom(request.getCognom());
        usuari.setEmail(request.getEmail());

        return usuariRepository.save(usuari);
    }

    public Boolean deleteUsuariById(Long id) {
        try {
            usuariRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Usuari getUserByUsernameAndPassword(String username, String password) {
        return usuariRepository.findByUsernameAndPassword(username, password);
    }
}
