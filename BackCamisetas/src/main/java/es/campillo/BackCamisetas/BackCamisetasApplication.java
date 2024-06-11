package es.campillo.BackCamisetas;

import es.campillo.Entidades.*;
import es.campillo.Respositorios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@SpringBootApplication(scanBasePackages = "es.campillo")
@EntityScan(basePackages = "es.campillo")
@ComponentScan(basePackages = "es.campillo")
@EnableJpaRepositories(basePackages = "es.campillo")
public class BackCamisetasApplication {

	@Autowired
	PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(BackCamisetasApplication.class, args);
	}
}

@Component
class DataLoader implements CommandLineRunner {

	private final RepositorioRoles repositorioRoles;
	private final RepositorioUsuarios repositorioUsuarios;
	private final RepositorioMarcas repositorioMarcas;
	private final RepositorioLigas repositorioLigas;
	private final RepositorioEquipos repositorioEquipos;
	private final RepositorioCamisetas repositorioCamisetas;
	private final PasswordEncoder passwordEncoder;



	@Autowired
	public DataLoader(RepositorioRoles repositorioRoles,RepositorioEquipos repositorioEquipos,
					  RepositorioUsuarios repositorioUsuarios, PasswordEncoder passwordEncoder,
					  RepositorioMarcas repositorioMarcas,RepositorioLigas repositorioLigas,
					  RepositorioCamisetas repositorioCamisetas) {
		this.repositorioRoles = repositorioRoles;
		this.repositorioUsuarios = repositorioUsuarios;
		this.repositorioMarcas = repositorioMarcas;
		this.repositorioLigas = repositorioLigas;
		this.repositorioCamisetas = repositorioCamisetas;
		this.repositorioEquipos=repositorioEquipos;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void run(String... args) throws Exception {
		if (repositorioRoles.count() == 0) {
			crearRoles();
		}
		if (repositorioUsuarios.count() == 0) {
			crearUsuarios();
		}
		if (repositorioMarcas.count() == 0) {
			crearMarcas();
		}
		if (repositorioLigas.count() == 0) {
			crearLigas();
		}
		if (repositorioEquipos.count() == 0) {
			crearEquipos();
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

	private void crearUsuarios() {
		Optional<Rol> rolAdminOptional = repositorioRoles.findByNombre("Admin");
		Optional<Rol> rolSuperAdminOptional = repositorioRoles.findByNombre("SuperAdmin");

		if (rolAdminOptional.isPresent() && rolSuperAdminOptional.isPresent()) {
			Rol rolAdmin = rolAdminOptional.get();
			Rol rolSuperAdmin = rolSuperAdminOptional.get();

			byte[] fotoPorDefectoAdmin = cargarImagenPorDefecto("admin.jpg");
			byte[] fotoPorDefectoSuperAdmin = cargarImagenPorDefecto("superadmin.jpg");

			Usuario usuarioAdmin = new Usuario();
			usuarioAdmin.setEmail("admin@gmail.com");
			usuarioAdmin.setNombre("ADMIN");
			usuarioAdmin.setApellido("ADMIN");
			usuarioAdmin.setCiudad("ADMIN");
			usuarioAdmin.setCodigoPostal("ADMIN");
			usuarioAdmin.setTelefono(666666666);
			usuarioAdmin.setDireccion("ADMIN");
			usuarioAdmin.setFoto(fotoPorDefectoAdmin);
			usuarioAdmin.setPassword(passwordEncoder.encode("admin123"));
			usuarioAdmin.setRol(rolAdmin);

			Usuario usuarioSuperAdmin = new Usuario();
			usuarioSuperAdmin.setEmail("superadmin@gmail.com");
			usuarioSuperAdmin.setNombre("SUPERADMIN");
			usuarioSuperAdmin.setApellido("SUPERADMIN");
			usuarioSuperAdmin.setCiudad("SUPERADMIN");
			usuarioSuperAdmin.setCodigoPostal("SUPERADMIN");
			usuarioSuperAdmin.setTelefono(666666666);
			usuarioSuperAdmin.setDireccion("SUPERADMIN");
			usuarioSuperAdmin.setFoto(fotoPorDefectoSuperAdmin);
			usuarioSuperAdmin.setPassword(passwordEncoder.encode("superadmin123"));
			usuarioSuperAdmin.setRol(rolSuperAdmin);

			repositorioUsuarios.save(usuarioAdmin);
			repositorioUsuarios.save(usuarioSuperAdmin);
		} else {
			throw new RuntimeException("Roles not found in the database");
		}
	}
	private void crearMarcas() {
		try {
			byte[] fotoPorDefectoAdidas = cargarImagenPorDefecto("adidas.jpeg");
			Marca adidas = new Marca();
			adidas.setNombre("Adidas");
			adidas.setFoto(fotoPorDefectoAdidas);
			repositorioMarcas.save(adidas);
			System.out.println("Marca Adidas insertada correctamente.");

			byte[] fotoPorDefectoNike = cargarImagenPorDefecto("nike.jpg");
			Marca nike = new Marca();
			nike.setNombre("Nike");
			nike.setFoto(fotoPorDefectoNike);
			repositorioMarcas.save(nike);
			System.out.println("Marca Nike insertada correctamente.");

			byte[] fotoPorDefectoPuma = cargarImagenPorDefecto("puma.jpg");
			Marca puma = new Marca();
			puma.setNombre("Puma");
			puma.setFoto(fotoPorDefectoPuma);
			repositorioMarcas.save(puma);
			System.out.println("Marca Puma insertada correctamente.");

			byte[] fotoPorDefectoKappa = cargarImagenPorDefecto("kappa.jpg");
			Marca kappa = new Marca();
			kappa.setNombre("Kappa");
			kappa.setFoto(fotoPorDefectoKappa);
			repositorioMarcas.save(kappa);
			System.out.println("Marca Kappa insertada correctamente.");
		} catch (Exception e) {
			System.err.println("Error al insertar marcas: " + e.getMessage());
		}
	}

	private void crearLigas(){
		byte[] fotoPorDefectoLaLiga = cargarImagenPorDefecto("laliga.png");
		Liga laliga = new Liga();
		laliga.setNombre("LaLiga");
		laliga.setFoto(fotoPorDefectoLaLiga);
		repositorioLigas.save(laliga);

		byte[] fotoPorDefectoPremier = cargarImagenPorDefecto("premier.png");
		Liga premier = new Liga();
		premier.setNombre("Premier League");
		premier.setFoto(fotoPorDefectoPremier);
		repositorioLigas.save(premier);

		byte[] fotoPorDefectoSeriea = cargarImagenPorDefecto("seriea.png");
		Liga seriea = new Liga();
		seriea.setNombre("Serie A");
		seriea.setFoto(fotoPorDefectoSeriea);
		repositorioLigas.save(seriea);

		byte[] fotoPorDefectoBundes = cargarImagenPorDefecto("bundesliga.jpg");
		Liga bundes = new Liga();
		bundes.setNombre("Bundesliga");
		bundes.setFoto(fotoPorDefectoBundes);
		repositorioLigas.save(bundes);

		byte[] fotoPorDefectoLigue1 = cargarImagenPorDefecto("ligue1.png");
		Liga ligue1 = new Liga();
		ligue1.setNombre("Ligue 1");
		ligue1.setFoto(fotoPorDefectoLigue1);
		repositorioLigas.save(ligue1);
	}
	private void crearEquipos(){
		Liga laliga = new Liga();
		laliga = repositorioLigas.findByNombre("LaLiga");
		Liga premier = new Liga();
		premier = repositorioLigas.findByNombre("Premier League");
		Liga bundesliga = new Liga();
		bundesliga = repositorioLigas.findByNombre("Bundesliga");
		Liga ligue1 = new Liga();
		ligue1 = repositorioLigas.findByNombre("Ligue 1");
		Liga seriea = new Liga();
		seriea = repositorioLigas.findByNombre("Serie A");

		byte[] fotoPorDefectoMadrid = cargarImagenPorDefecto("madrid.png");
		Equipo madrid = new Equipo();
		madrid.setNombre("Real Madrid");
		madrid.setLiga(laliga);
		madrid.setFoto(fotoPorDefectoMadrid);
		repositorioEquipos.save(madrid);

		byte[] fotoPorDefectoBarcelona = cargarImagenPorDefecto("barcelona.png");
		Equipo barcelona = new Equipo();
		barcelona.setNombre("FC Barcelona");
		barcelona.setLiga(laliga);
		barcelona.setFoto(fotoPorDefectoBarcelona);
		repositorioEquipos.save(barcelona);

		byte[] fotoPorDefectoCity = cargarImagenPorDefecto("city.png");
		Equipo city = new Equipo();
		city.setNombre("Manchester City");
		city.setLiga(premier);
		city.setFoto(fotoPorDefectoCity);
		repositorioEquipos.save(city);

		byte[] fotoPorDefectoLiverpool = cargarImagenPorDefecto("liverpool.png");
		Equipo liverpool = new Equipo();
		liverpool.setNombre("Liverpool");
		liverpool.setLiga(premier);
		liverpool.setFoto(fotoPorDefectoLiverpool);
		repositorioEquipos.save(liverpool);

		byte[] fotoPorDefectoJuve = cargarImagenPorDefecto("juve.png");
		Equipo juve = new Equipo();
		juve.setNombre("Juventus");
		juve.setLiga(seriea);
		juve.setFoto(fotoPorDefectoJuve);
		repositorioEquipos.save(juve);

		byte[] fotoPorDefectoInter = cargarImagenPorDefecto("inter.png");
		Equipo inter = new Equipo();
		inter.setNombre("Inter de Milán");
		inter.setLiga(seriea);
		inter.setFoto(fotoPorDefectoInter);
		repositorioEquipos.save(inter);

		byte[] fotoPorDefectoMonco = cargarImagenPorDefecto("monaco.png");
		Equipo monaco = new Equipo();
		monaco.setNombre("Monaco");
		monaco.setLiga(ligue1);
		monaco.setFoto(fotoPorDefectoMonco);
		repositorioEquipos.save(monaco);

		byte[] fotoPorDefectoPSG = cargarImagenPorDefecto("psg.png");
		Equipo psg = new Equipo();
		psg.setNombre("Paris Saint Germain");
		psg.setLiga(ligue1);
		psg.setFoto(fotoPorDefectoPSG);
		repositorioEquipos.save(psg);

		byte[] fotoPorDefectoBayern = cargarImagenPorDefecto("bayern.png");
		Equipo bayern = new Equipo();
		bayern.setNombre("Bayern de Munich");
		bayern.setLiga(bundesliga);
		bayern.setFoto(fotoPorDefectoBayern);
		repositorioEquipos.save(bayern);

		byte[] fotoPorDefectoBorussia = cargarImagenPorDefecto("borussia.png");
		Equipo borussia = new Equipo();
		borussia.setNombre("Real Madrid");
		borussia.setLiga(bundesliga);
		borussia.setFoto(fotoPorDefectoBorussia);
		repositorioEquipos.save(borussia);
	}

	private byte[] cargarImagenPorDefecto(String nombreArchivo) {
		try (InputStream inputStream = getClass().getResourceAsStream("/static/images/" + nombreArchivo)) {
			if (inputStream == null) {
				throw new RuntimeException("Default profile image not found: " + nombreArchivo);
			}
			return inputStream.readAllBytes();
		} catch (IOException e) {
			throw new RuntimeException("Failed to load default profile image: " + nombreArchivo, e);
		}
	}
}