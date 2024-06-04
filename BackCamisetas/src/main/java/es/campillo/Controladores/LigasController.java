package es.campillo.Controladores;

import es.campillo.Entidades.Liga;
import es.campillo.Respositorios.RepositorioLigas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/ligas/")
@CrossOrigin(origins = "http://localhost:4200")
public class LigasController {
    @Autowired
    RepositorioLigas repositorioLigas;

    @PostMapping("/")
    public Liga insertarLiga(@RequestBody Liga liga) {
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

    @GetMapping("/")
    public List<Liga> listarTodasLigas(){
        return repositorioLigas.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarLiga(@PathVariable Long id) {
        if (repositorioLigas.existsById(id)) {
            repositorioLigas.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
