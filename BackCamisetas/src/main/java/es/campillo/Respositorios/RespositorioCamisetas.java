package es.campillo.Respositorios;

import es.campillo.Entidades.Camiseta;
import es.campillo.Entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RespositorioCamisetas extends JpaRepository<Camiseta, Long> {
}
