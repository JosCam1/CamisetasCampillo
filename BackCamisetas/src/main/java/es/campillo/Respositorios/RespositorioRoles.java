package es.campillo.Respositorios;

import es.campillo.Entidades.Rol;
import es.campillo.Entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RespositorioRoles extends JpaRepository<Rol, Long> {
}
