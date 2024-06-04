package es.campillo.Controladores;

import es.campillo.Entidades.Usuario;
import es.campillo.Respositorios.RepositorioUsuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios/")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    @Autowired
    RepositorioUsuarios repositorioUsuarios;

    @PostMapping("/")
    public Usuario insertarUsuario(@RequestBody Usuario usuario){
        Optional<Usuario> usuarioExistente = repositorioUsuarios.findByEmail(usuario.getEmail());
        if (usuarioExistente.isPresent()) {
            throw new IllegalArgumentException("El correo electrónico ya está registrado");
        }

        String fotoBase64 = Base64.getEncoder().encodeToString(usuario.getFoto());
        String fotoBase64Limpia = fotoBase64.replaceAll("\\s+", "");
        try {
            byte[] imagenDecodificada = Base64.getDecoder().decode(fotoBase64Limpia);
            usuario.setFoto(imagenDecodificada);
            return repositorioUsuarios.save(usuario);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Error al decodificar la imagen Base64");
        }
    }

    @GetMapping("/buscarPorEmail/{email}")
    public boolean existeUsuarioPorEmail(@PathVariable String email) {
        List<Usuario> usuarios = repositorioUsuarios.findAll();
        return usuarios.stream().anyMatch(usuario -> usuario.getEmail().equals(email));
    }
}