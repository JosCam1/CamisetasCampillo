package es.campillo.Respositorios;

import es.campillo.Entidades.Camiseta;
import es.campillo.Entidades.Equipo;
import es.campillo.Entidades.Liga;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositorioEquipos extends JpaRepository<Equipo, Long> {
    void deleteByLiga(Liga liga);
    Equipo findByNombre(String nombre);
    List<Equipo> findByLigaId(Long ligaId);
}