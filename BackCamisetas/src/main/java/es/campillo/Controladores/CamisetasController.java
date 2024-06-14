package es.campillo.Controladores;

import es.campillo.Entidades.Camiseta;
import es.campillo.Entidades.Equipo;
import es.campillo.Entidades.Liga;
import es.campillo.Entidades.Pedido;
import es.campillo.Respositorios.RepositorioCamisetas;
import es.campillo.Respositorios.RepositorioPedidos;
import es.campillo.Servicios.Session;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/camisetas/")
@CrossOrigin(origins = "http://localhost:4200")
public class CamisetasController {
    @Autowired
    RepositorioCamisetas respositorioCamisetas;

    @Autowired
    RepositorioPedidos repositorioPedidos;

    @Autowired
    Session session;

    @GetMapping("/")
    public List<Camiseta> listarTodasCamisetas(){
        return respositorioCamisetas.findAll();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> eliminarCamiseta(@PathVariable Long id) {
        if (session.getUsuario().getRol().getId() == 1 || session.getUsuario().getRol().getId() == 2) {
            Optional<Camiseta> camisetaOptional = respositorioCamisetas.findById(id);
            if (camisetaOptional.isPresent()) {
                Camiseta camiseta = camisetaOptional.get();

                // Eliminar todas las relaciones en la tabla intermedia
                repositorioPedidos.eliminarPedidosPorCamiseta(id);

                // Luego eliminar la camiseta
                respositorioCamisetas.deleteById(id);

                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }


@PostMapping("/")
    public Camiseta insertarCamiseta(@RequestBody Camiseta camiseta) {
        if (session.getUsuario().getRol().getId() == 1 || session.getUsuario().getRol().getId() == 2) {
            String fotoBase64 = Base64.getEncoder().encodeToString(camiseta.getFoto());
            String fotoBase64Limpia = fotoBase64.replaceAll("\\s+", "");

            try {
                byte[] imagenDecodificada = Base64.getDecoder().decode(fotoBase64Limpia);
                camiseta.setFoto(imagenDecodificada);
                return respositorioCamisetas.save(camiseta);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                throw new IllegalArgumentException("Error al decodificar la imagen Base64");
            }
        }
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Camiseta> obtenerCamisetaById(@PathVariable Long id) {
        Optional<Camiseta> camisetaOptional = respositorioCamisetas.findById(id);
        if (camisetaOptional.isPresent()) {
            Camiseta camiseta = camisetaOptional.get();
            return ResponseEntity.ok().body(camiseta);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Camiseta> actualizarCamiseta(@PathVariable Long id, @RequestBody Camiseta camisetaActualizada) {
        Optional<Camiseta> camisetaOptional = respositorioCamisetas.findById(id);
        if (camisetaOptional.isPresent()) {
            Camiseta camiseta = camisetaOptional.get();
            camiseta.setNombre(camisetaActualizada.getNombre());
            camiseta.setFoto(camisetaActualizada.getFoto());
            camiseta.setPrecio(camisetaActualizada.getPrecio());
            camiseta.setDescripcion(camisetaActualizada.getDescripcion());

            if (camisetaActualizada.getEquipo() != null) {
                camiseta.setEquipo(camisetaActualizada.getEquipo());
            }
            if (camisetaActualizada.getMarca() != null) {
                camiseta.setMarca(camisetaActualizada.getMarca());
            }

            Camiseta camisetaGuardada = respositorioCamisetas.save(camiseta);
            return ResponseEntity.ok(camisetaGuardada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}


