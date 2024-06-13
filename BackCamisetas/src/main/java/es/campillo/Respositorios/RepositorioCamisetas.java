package es.campillo.Respositorios;

import es.campillo.Entidades.Camiseta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositorioCamisetas extends JpaRepository<Camiseta, Long> {
    List<Camiseta> findByMarcaId(Long marcaId);
    List<Camiseta> findByEquipoId(Long equipoId);
}
