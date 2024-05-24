package es.campillo.Respositorios;

import es.campillo.Entidades.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioEquipos extends JpaRepository<Equipo, Long> {
}
