package es.campillo.Respositorios;

import es.campillo.Entidades.Marca;
import es.campillo.Entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RespositorioMarcas extends JpaRepository<Marca, Long> {
}
