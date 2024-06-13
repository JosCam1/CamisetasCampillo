package es.campillo.Controladores;

import es.campillo.Entidades.Pedido;
import es.campillo.Respositorios.RepositorioPedidos;
import es.campillo.Servicios.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos/")
@CrossOrigin(origins = "http://localhost:4200")
public class PedidosController {
    @Autowired
    RepositorioPedidos repositorioPedidos;

    @Autowired
    Session session;

    @GetMapping("/usuario/{idUsuario}")
    public List<Pedido> obtenerPedidosPorUsuario(@PathVariable Long idUsuario) {
        return repositorioPedidos.findByUsuarioId(idUsuario);
    }
}
