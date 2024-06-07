package es.campillo.Controladores;

import es.campillo.Entidades.Usuario;
import es.campillo.Servicios.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/session")
@CrossOrigin(origins = "http://localhost:4200")
public class SessionController {

    @Autowired
    private Session session;

    @PostMapping("/iniciar")
    public ResponseEntity<Map<String, Object>> iniciarSesion(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        String password = payload.get("password");
        String resultado = session.iniciarSesion(email, password);

        Map<String, Object> response = new HashMap<>();
        response.put("message", resultado);

        if ("Inicio de sesión exitoso".equals(resultado)) {
            response.put("usuario", session.getUsuario()); // Agregar el usuario a la respuesta
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout() {
        String resultado = session.logout();
        Map<String, String> response = new HashMap<>();
        response.put("message", resultado);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/usuario")
    public ResponseEntity<Usuario> getUsuario() {
        Usuario usuario = session.getUsuario();
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
}