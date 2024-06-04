package es.campillo.Respositorios;

import es.campillo.Entidades.Liga;
import es.campillo.Entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepositorioUsuarios  extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
}
