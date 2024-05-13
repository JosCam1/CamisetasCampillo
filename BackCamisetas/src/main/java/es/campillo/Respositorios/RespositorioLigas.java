package es.campillo.Respositorios;

import es.campillo.Entidades.Liga;
import es.campillo.Entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RespositorioLigas extends JpaRepository<Liga, Long> {
}
