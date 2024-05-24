package es.campillo.Respositorios;

import es.campillo.Entidades.Camiseta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioCamisetas extends JpaRepository<Camiseta, Long> {
}
