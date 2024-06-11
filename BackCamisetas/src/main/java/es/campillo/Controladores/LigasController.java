package es.campillo.Controladores;

import es.campillo.Entidades.Liga;
import es.campillo.Respositorios.RepositorioLigas;
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
@RequestMapping("/ligas/")
@CrossOrigin(origins = "http://localhost:4200")
public class LigasController {
    @Autowired
    RepositorioLigas repositorioLigas;

    @Autowired
    Session session;

    @PostMapping("/")
    public Liga insertarLiga(@RequestBody Liga liga) {
        if (session.getUsuario().getRol().getId() == 1 || session.getUsuario().getRol().getId() == 2) {
            String fotoBase64 = Base64.getEncoder().encodeToString(liga.getFoto());
            String fotoBase64Limpia = fotoBase64.replaceAll("\\s+", "");

            try {
                byte[] imagenDecodificada = Base64.getDecoder().decode(fotoBase64Limpia);
                liga.setFoto(imagenDecodificada);
                return repositorioLigas.save(liga);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                throw new IllegalArgumentException("Error al decodificar la imagen Base64");
            }
        }
        return null; // Add a return statement in case the condition is not met
    }

    @GetMapping("/")
    public List<Liga> listarTodasLigas() {
        return repositorioLigas.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarLiga(@PathVariable Long id) {
        if (session.getUsuario().getRol().getId() == 1 || session.getUsuario().getRol().getId() == 2) {
            Optional<Liga> ligaOptional = repositorioLigas.findById(id);
            if (ligaOptional.isPresent()) {
                Liga liga = ligaOptional.get();

                // Ahora elimina la liga
                repositorioLigas.deleteById(id);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        }
        return null; // Añade una declaración de retorno en caso de que la condición no se cumpla
    }

    @GetMapping("/{id}")
    public ResponseEntity<Liga> obtenerLigaById(@PathVariable Long id) {
        Optional<Liga> ligaOptional = repositorioLigas.findById(id);
        if (ligaOptional.isPresent()) {
            Liga liga = ligaOptional.get();
            return ResponseEntity.ok().body(liga);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public Liga actualizarLiga(@PathVariable Long id, @RequestBody Liga ligaActualizada) {
        if (session.getUsuario().getRol().getId() == 1 || session.getUsuario().getRol().getId() == 2) {
            Optional<Liga> ligaOptional = repositorioLigas.findById(id);
            if (ligaOptional.isPresent()) {
                Liga liga = ligaOptional.get();
                liga.setNombre(ligaActualizada.getNombre());
                liga.setFoto(ligaActualizada.getFoto());
                return repositorioLigas.save(liga);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Liga no encontrada");
            }
        }
        return null;
    }
}