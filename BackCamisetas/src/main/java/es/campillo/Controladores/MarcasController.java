package es.campillo.Controladores;

import es.campillo.Entidades.Camiseta;
import es.campillo.Entidades.Liga;
import es.campillo.Entidades.Marca;
import es.campillo.Respositorios.RepositorioCamisetas;
import es.campillo.Respositorios.RepositorioMarcas;
import es.campillo.Servicios.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/marcas/")
@CrossOrigin(origins = "http://localhost:4200")
public class MarcasController {
    @Autowired
    RepositorioMarcas repositorioMarcas;

    @Autowired
    RepositorioCamisetas repositorioCamisetas;

    @Autowired
    Session session;

    @PostMapping("/")
    public Marca insertarMarca(@RequestBody Marca marca) {
        if (session.getUsuario().getRol().getId() == 1 || session.getUsuario().getRol().getId() == 2) {
            String fotoBase64 = Base64.getEncoder().encodeToString(marca.getFoto());
            String fotoBase64Limpia = fotoBase64.replaceAll("\\s+", "");

            try {
                byte[] imagenDecodificada = Base64.getDecoder().decode(fotoBase64Limpia);
                marca.setFoto(imagenDecodificada);
                return repositorioMarcas.save(marca);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                throw new IllegalArgumentException("Error al decodificar la imagen Base64");
            }
        }
        return null;
    }

    @GetMapping("/")
    public List<Marca> listarTodasMarcas() {
        return repositorioMarcas.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarMarca(@PathVariable Long id) {
        if (session.getUsuario().getRol().getId() == 1 || session.getUsuario().getRol().getId() == 2) {
            if (repositorioMarcas.existsById(id)) {
                // Eliminar todas las camisetas asociadas a la marca
                List<Camiseta> camisetasAsociadas = repositorioCamisetas.findByMarcaId(id);
                repositorioCamisetas.deleteAll(camisetasAsociadas);

                // Eliminar la marca
                repositorioMarcas.deleteById(id);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Marca> obtenerMarcaById(@PathVariable Long id) {
        Optional<Marca> marcaOptional = repositorioMarcas.findById(id);
        if (marcaOptional.isPresent()) {
            Marca marca = marcaOptional.get();
            return ResponseEntity.ok().body(marca);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public Marca actualizarMarca(@PathVariable Long id, @RequestBody Liga marcaActualizada) {
        if (session.getUsuario().getRol().getId() == 1 || session.getUsuario().getRol().getId() == 2) {
            Optional<Marca> marcaOptional = repositorioMarcas.findById(id);
            if (marcaOptional.isPresent()) {
                Marca marca = marcaOptional.get();
                marca.setNombre(marcaActualizada.getNombre());
                marca.setFoto(marcaActualizada.getFoto());
                return repositorioMarcas.save(marca);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Marca no encontrada");
            }
        }
        return null;
    }
}