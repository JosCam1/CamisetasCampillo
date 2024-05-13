package es.campillo.Respositorios;

import es.campillo.Entidades.Pedido;
import es.campillo.Entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RespositorioPedidos extends JpaRepository<Pedido, Long> {
}
