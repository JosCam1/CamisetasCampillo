package es.campillo.Respositorios;

import es.campillo.Entidades.Pedido;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface RepositorioPedidos extends JpaRepository<Pedido, Long> {

    List<Pedido> findByUsuarioId(Long usuarioId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM pedido_camiseta WHERE camiseta_id = :camisetaId", nativeQuery = true)
    void eliminarPedidosPorCamiseta(Long camisetaId);

}
