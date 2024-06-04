package es.campillo.BackCamisetas;

import es.campillo.Entidades.Rol;
import es.campillo.Respositorios.RepositorioRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

@SpringBootApplication(scanBasePackages = "es.campillo")
@EntityScan(basePackages = "es.campillo")
@ComponentScan(basePackages = "es.campillo")
@EnableJpaRepositories(basePackages = "es.campillo")
public class BackCamisetasApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackCamisetasApplication.class, args);
	}
}

@Component
class DataLoader implements CommandLineRunner {

	private final RepositorioRoles repositorioRoles;

	@Autowired
	public DataLoader(RepositorioRoles repositorioRoles) {
		this.repositorioRoles = repositorioRoles;
	}

	@Override
	public void run(String... args) throws Exception {
		if (repositorioRoles.count() == 0) {
			crearRoles();
		}
	}

	private void crearRoles() {
		Rol rolCliente = new Rol();
		rolCliente.setNombre("Cliente");
		Rol rolAdmin = new Rol();
		rolAdmin.setNombre("Admin");
		Rol rolSuperAdmin = new Rol();
		rolSuperAdmin.setNombre("SuperAdmin");

		repositorioRoles.save(rolCliente);
		repositorioRoles.save(rolAdmin);
		repositorioRoles.save(rolSuperAdmin);
	}
}