package es.campillo.Controladores;

import es.campillo.Entidades.Rol;
import es.campillo.Respositorios.RepositorioRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles/")
@CrossOrigin(origins = "http://localhost:4200")
public class RolesController {
    @Autowired
    RepositorioRoles repositorioRoles;

    @GetMapping("/")
    public List<Rol> listarRoles(){
        return  repositorioRoles.findAll();
    }
}
