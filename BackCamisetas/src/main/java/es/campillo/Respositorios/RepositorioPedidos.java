package es.campillo.Respositorios;

import es.campillo.Entidades.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioPedidos extends JpaRepository<Pedido, Long> {
}
