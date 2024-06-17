package es.campillo.Respositorios;

import es.campillo.Entidades.Liga;
import es.campillo.Entidades.Marca;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioMarcas extends JpaRepository<Marca, Long> {
    Marca findByNombre(String nombre);
}
