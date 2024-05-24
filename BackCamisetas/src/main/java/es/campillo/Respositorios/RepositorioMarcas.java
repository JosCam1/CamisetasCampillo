package es.campillo.Respositorios;

import es.campillo.Entidades.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioMarcas extends JpaRepository<Marca, Long> {
}
