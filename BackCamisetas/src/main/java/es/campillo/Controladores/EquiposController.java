package es.campillo.Controladores;

import es.campillo.Entidades.Camiseta;
import es.campillo.Entidades.Equipo;
import es.campillo.Entidades.Liga;
import es.campillo.Entidades.Pedido;
import es.campillo.Respositorios.RepositorioCamisetas;
import es.campillo.Respositorios.RepositorioEquipos;
import es.campillo.Respositorios.RepositorioPedidos;
import es.campillo.Servicios.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/equipos/")
@CrossOrigin(origins = "http://localhost:4200")
public class EquiposController {
    @Autowired
    RepositorioEquipos repositorioEquipos;

    @Autowired
    RepositorioCamisetas repositorioCamisetas;

    @Autowired
    RepositorioPedidos repositorioPedidos;

    @Autowired
    Session session;

    @PostMapping("/")
    public ResponseEntity<Equipo> insertarEquipo(@RequestBody Equipo equipo) {
        if (session.getUsuario().getRol().getId() == 1 || session.getUsuario().getRol().getId() == 2) {
            String fotoBase64 = Base64.getEncoder().encodeToString(equipo.getFoto());
            String fotoBase64Limpia = fotoBase64.replaceAll("\\s+", "");

            try {
                byte[] imagenDecodificada = Base64.getDecoder().decode(fotoBase64Limpia);
                equipo.setFoto(imagenDecodificada);
                Equipo equipoGuardado = repositorioEquipos.save(equipo);
                return ResponseEntity.status(HttpStatus.CREATED).body(equipoGuardado);
            } catch (IllegalArgumentException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error al decodificar la imagen Base64", e);
            }
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping("/")
    public ResponseEntity<List<Equipo>> listarTodosEquipos() {
        List<Equipo> equipos = repositorioEquipos.findAll();
        return ResponseEntity.ok(equipos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarEquipo(@PathVariable Long id) {
        if (session.getUsuario().getRol().getId() == 1 || session.getUsuario().getRol().getId() == 2) {
            Optional<Equipo> equipoOptional = repositorioEquipos.findById(id);

            if (equipoOptional.isPresent()) {
                Equipo equipo = equipoOptional.get();

                // Eliminar todas las camisetas asociadas al equipo
                List<Camiseta> camisetasAsociadas = repositorioCamisetas.findByEquipoId(id);
                repositorioCamisetas.deleteAll(camisetasAsociadas);

                // Eliminar los pedidos que contienen las camisetas asociadas al equipo
                for (Camiseta camiseta : camisetasAsociadas) {
                    List<Pedido> pedidosAsociados = repositorioPedidos.findByCamisetaId(camiseta.getId());
                    repositorioPedidos.deleteAll(pedidosAsociados);
                }

                // Finalmente, eliminar el equipo
                repositorioEquipos.deleteById(id);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Equipo> obtenerEquipoById(@PathVariable Long id) {
        Optional<Equipo> equipoOptional = repositorioEquipos.findById(id);
        return equipoOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Equipo> actualizarEquipo(@PathVariable Long id, @RequestBody Equipo equipoActualizado) {
        if (session.getUsuario().getRol().getId() == 1 || session.getUsuario().getRol().getId() == 2) {
            Optional<Equipo> equipoOptional = repositorioEquipos.findById(id);
            if (equipoOptional.isPresent()) {
                Equipo equipo = equipoOptional.get();

                // Actualizar nombre y foto del equipo
                equipo.setNombre(equipoActualizado.getNombre());
                equipo.setFoto(equipoActualizado.getFoto());

                // Obtener la liga actual del equipo
                Liga ligaActual = equipo.getLiga();

                // Obtener la liga seleccionada del equipoActualizado
                Liga ligaSeleccionada = equipoActualizado.getLiga();

                // Verificar si hay cambios en la liga
                if (ligaSeleccionada != null && !ligaSeleccionada.equals(ligaActual)) {
                    // Asignar la nueva liga al equipo
                    equipo.setLiga(ligaSeleccionada);
                }

                Equipo equipoGuardado = repositorioEquipos.save(equipo);
                return ResponseEntity.ok(equipoGuardado);
            } else {
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}