package cat.xtec.ioc.podcat.Service;

import cat.xtec.ioc.podcat.Model.Canal;
import cat.xtec.ioc.podcat.Model.Usuari;
import cat.xtec.ioc.podcat.Repository.CanalRepository;
import cat.xtec.ioc.podcat.Repository.UsuariRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;

@Service
@Transactional
public class CanalService {

    @Autowired
    private CanalRepository canalRepository;

    @Autowired
    private UsuariRepository usuariRepository;

    public List<Canal> getAllCanals() {
        return canalRepository.findAll();
    }

    public Optional<Canal> getCanalById(Long id) {
        return canalRepository.findById(id);
    }

    public Canal addCanal(Canal canal) {

//        Usuari usuari = usuariRepository.findById(idUsuari)
//                .orElseThrow(() -> new EntityNotFoundException("Usuari no trobat"));
//
//        canal.setUsuari(usuari);
//        Canal savedCanal = canalRepository.save(canal);
//
//        usuari.getCanals().add(savedCanal);
//
//        return savedCanal;

        return canalRepository.save(canal);
    }

    public Canal updateCanalById(Canal request, Long id) {
        Canal canal = canalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Canal no trobat"));

        canal.setTitol(request.getTitol());
        canal.setDescripcio(request.getDescripcio());
        canal.setImatge(request.getImatge());

        return canalRepository.save(canal);
    }

    public Canal updateCanalAndUsuari(Canal canal, Long id, Long idUsuari) {

        Usuari usuari = usuariRepository.findById(idUsuari)
                .orElseThrow(() -> new EntityNotFoundException("Usuari no trobat"));

        // Actualitzar l'usuari associat al canal
        canal.setUsuari(usuari);
        canalRepository.save(canal);

        // Actualitzar el canal en la llista de canals de l'usuari
        List<Canal> canals = usuari.getCanals();
        canals.removeIf(c -> c.getId().equals(canal.getId()));
        canals.add(canal);
        usuari.setCanals(canals);
        usuariRepository.save(usuari);

        return canal;
    }

    public Boolean deleteCanalById(Long id) {
//        Canal canal = canalRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Canal no trobat"));
//
//        Usuari usuari = canal.getUsuari();
//        usuari.getCanals().remove(canal);
//
//        canalRepository.deleteById(id);
        try {
            canalRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
