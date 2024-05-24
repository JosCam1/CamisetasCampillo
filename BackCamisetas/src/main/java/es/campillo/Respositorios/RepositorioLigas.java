package es.campillo.Respositorios;

import es.campillo.Entidades.Liga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioLigas extends JpaRepository<Liga, Long> {
}
