package es.campillo.Controladores;

import es.campillo.Entidades.Camiseta;
import es.campillo.Respositorios.RespositorioCamisetas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/camisetas")
@CrossOrigin(origins = "http://localhost:4200/")
public class CamisetasController {
    @Autowired
    RespositorioCamisetas respositorioCamisetas;

    @GetMapping("/")
    public List<Camiseta> listarTodasCamisetas(){
        return respositorioCamisetas.findAll();
    }


}
