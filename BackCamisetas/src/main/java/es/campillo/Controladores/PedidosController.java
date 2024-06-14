package es.campillo.Controladores;

import es.campillo.Entidades.Camiseta;
import es.campillo.Entidades.Pedido;
import es.campillo.Entidades.Usuario;
import es.campillo.Respositorios.RepositorioPedidos;
import es.campillo.Servicios.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/pedidos/")
@CrossOrigin(origins = "http://localhost:4200")
public class PedidosController {
    @Autowired
    RepositorioPedidos repositorioPedidos;

    @Autowired
    Session session;

    @GetMapping("/")
    public List<Pedido> listarTodaosLosPedidos(){
        return repositorioPedidos.findAll();    }

    @GetMapping("/usuario/{idUsuario}")
    public List<Pedido> obtenerPedidosPorUsuario(@PathVariable Long idUsuario) {
        return repositorioPedidos.findByUsuarioId(idUsuario);
    }

    @PostMapping("/realizar")
    public Pedido realizarPedido(@RequestBody Pedido pedido) {
        Usuario usuario = session.getUsuario();
        if (usuario == null) {
            throw new RuntimeException("Usuario no autenticado");
        }

        pedido.setFecha(new Date());
        pedido.setUsuario(usuario);

        // Guardar el pedido en la base de datos
        return repositorioPedidos.save(pedido);
    }

    @PutMapping("/{id}/cambiarEstado")
    public Pedido cambiarEstadoPedido(@PathVariable Long id) {
        // Verificar autenticación del usuario
        Usuario usuario = session.getUsuario();
        if (usuario == null) {
            throw new RuntimeException("Usuario no autenticado");
        }

        // Buscar el pedido por su ID
        Pedido pedido = repositorioPedidos.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));


        if (pedido.getEstado().equals("Pagado")) {
            pedido.setEstado("Pendiente de Pago");
        } else {
            pedido.setEstado("Pagado");
        }

        return repositorioPedidos.save(pedido);
    }

}