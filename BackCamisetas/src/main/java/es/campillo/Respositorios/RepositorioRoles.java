package es.campillo.Respositorios;

import es.campillo.Entidades.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepositorioRoles extends JpaRepository<Rol, Long> {
    Optional<Rol> findByNombre(String nombre);
}
