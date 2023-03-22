package cat.xtec.ioc.podcat.Repository;

import cat.xtec.ioc.podcat.Model.Usuari;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface UsuariRepository extends JpaRepository <Usuari, Long> {

    Usuari findByUsernameAndPassword(String username, String password);

    Usuari findByEmail(String email);
}
