package es.campillo.Servicios;

import es.campillo.Entidades.Usuario;
import es.campillo.Respositorios.RepositorioUsuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class Session {

    @Autowired
    private RepositorioUsuarios usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Usuario usuario;

    public String iniciarSesion(String email, String password) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            if (passwordEncoder.matches(password, usuario.getPassword())) {
                this.usuario = usuario;
                return "Inicio de sesión exitoso";
            } else {
                return "Contraseña incorrecta";
            }
        } else {
            return "Usuario no encontrado";
        }
    }

    public String logout() {
        this.usuario = null;
        return "Sesión cerrada exitosamente";
    }

    public Usuario getUsuario() {
        return usuario;
    }
}