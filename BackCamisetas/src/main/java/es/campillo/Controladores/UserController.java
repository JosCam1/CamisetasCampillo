package es.campillo.Controladores;

import es.campillo.Entidades.Liga;
import es.campillo.Entidades.Usuario;
import es.campillo.Respositorios.RepositorioUsuarios;
import es.campillo.Servicios.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios/")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    @Autowired
    RepositorioUsuarios repositorioUsuarios;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    Session session;

    @PostMapping("/")
    public Usuario insertarUsuario(@RequestBody Usuario usuario){
        Optional<Usuario> usuarioExistente = repositorioUsuarios.findByEmail(usuario.getEmail());
        String passwordEncriptada = passwordEncoder.encode(usuario.getPassword());
        if (usuarioExistente.isPresent()) {
            throw new IllegalArgumentException("El correo electrónico ya está registrado");
        }

        String fotoBase64 = Base64.getEncoder().encodeToString(usuario.getFoto());
        String fotoBase64Limpia = fotoBase64.replaceAll("\\s+", "");
        try {
            byte[] imagenDecodificada = Base64.getDecoder().decode(fotoBase64Limpia);
            usuario.setFoto(imagenDecodificada);
            usuario.setPassword(passwordEncriptada);
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

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUserById(@PathVariable Long id) {
        Optional<Usuario> usuarioOptional = repositorioUsuarios.findById(id);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            return ResponseEntity.ok().body(usuario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public Usuario actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioActualizado) {
        Usuario usuarioSesion = session.getUsuario();
        if (usuarioSesion.getRol().getId() == 1 || usuarioSesion.getRol().getId() == 2) {
            Optional<Usuario> usuarioOptional = repositorioUsuarios.findById(id);
            if (usuarioOptional.isPresent()) {
                Usuario usuario = usuarioOptional.get();
                usuario.setNombre(usuarioActualizado.getNombre());
                usuario.setApellido(usuarioActualizado.getApellido());
                usuario.setCodigoPostal(usuarioActualizado.getCodigoPostal());
                usuario.setTelefono(usuarioActualizado.getTelefono());
                usuario.setCiudad(usuarioActualizado.getCiudad());
                usuario.setDireccion(usuarioActualizado.getDireccion());
                usuario.setEmail(usuarioActualizado.getEmail());
                usuario.setPassword(usuarioActualizado.getPassword());
                usuario.setFoto(usuarioActualizado.getFoto());
                usuario.setRol(usuarioActualizado.getRol());
                return repositorioUsuarios.save(usuario);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No tienes permisos para actualizar usuarios");
        }
    }

}