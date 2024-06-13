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
import java.util.*;

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
	private final RepositorioPedidos repositorioPedidos;
	private final PasswordEncoder passwordEncoder;



	@Autowired
	public DataLoader(RepositorioRoles repositorioRoles,RepositorioEquipos repositorioEquipos,
					  RepositorioUsuarios repositorioUsuarios, PasswordEncoder passwordEncoder,
					  RepositorioMarcas repositorioMarcas,RepositorioLigas repositorioLigas,
					  RepositorioCamisetas repositorioCamisetas, RepositorioPedidos repositorioPedidos) {
		this.repositorioRoles = repositorioRoles;
		this.repositorioUsuarios = repositorioUsuarios;
		this.repositorioMarcas = repositorioMarcas;
		this.repositorioLigas = repositorioLigas;
		this.repositorioCamisetas = repositorioCamisetas;
		this.repositorioEquipos=repositorioEquipos;
		this.repositorioPedidos=repositorioPedidos;
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
		if (repositorioCamisetas.count() == 0) {
			crearCamisetas();
		}
		if (repositorioPedidos.count() == 0) {
			List<Long> idsCamisetas = Arrays.asList(1L, 2L);
			crearPedidos(3L, idsCamisetas);
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
		Optional<Rol> rolClienteOptional = repositorioRoles.findByNombre("Cliente");

		if (rolAdminOptional.isPresent() && rolSuperAdminOptional.isPresent()) {
			Rol rolAdmin = rolAdminOptional.get();
			Rol rolSuperAdmin = rolSuperAdminOptional.get();
			Rol rolCliente = rolClienteOptional.get();

			byte[] fotoPorDefectoAdmin = cargarImagenPorDefecto("admin.jpg");
			byte[] fotoPorDefectoSuperAdmin = cargarImagenPorDefecto("superadmin.jpg");
			byte[] fotoPorDefectoCliente = cargarImagenPorDefecto("cliente.jpg");

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

			Usuario usuarioCliente = new Usuario();
			usuarioCliente.setEmail("pepe@gmail.com");
			usuarioCliente.setNombre("Pepe");
			usuarioCliente.setApellido("Diaz");
			usuarioCliente.setCiudad("Madrid");
			usuarioCliente.setCodigoPostal("42000");
			usuarioCliente.setTelefono(666666666);
			usuarioCliente.setDireccion("Avenida de los Gobernadores");
			usuarioCliente.setFoto(fotoPorDefectoCliente);
			usuarioCliente.setPassword(passwordEncoder.encode("123456"));
			usuarioCliente.setRol(rolCliente);

			repositorioUsuarios.save(usuarioAdmin);
			repositorioUsuarios.save(usuarioSuperAdmin);
			repositorioUsuarios.save(usuarioCliente);
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
		borussia.setNombre("Borussia Dortmund");
		borussia.setLiga(bundesliga);
		borussia.setFoto(fotoPorDefectoBorussia);
		repositorioEquipos.save(borussia);
	}

	public void crearCamisetas() {

		Optional<Equipo> realmadridOptional = repositorioEquipos.findById(1L);
		Optional<Equipo> barcelonaOptional = repositorioEquipos.findById(2L);
		Optional<Equipo> cityOptional = repositorioEquipos.findById(3L);
		Optional<Equipo> liverpoolOptional = repositorioEquipos.findById(4L);
		Optional<Equipo> jueventusOptional = repositorioEquipos.findById(5L);
		Optional<Equipo> interOptional = repositorioEquipos.findById(6L);
		Optional<Equipo> monacoOptional = repositorioEquipos.findById(7L);
		Optional<Equipo> psgOptional = repositorioEquipos.findById(8L);
		Optional<Equipo> bayernOptional = repositorioEquipos.findById(9L);
		Optional<Equipo> borussiaOptional = repositorioEquipos.findById(10L);

		if (realmadridOptional.isPresent()) {
			Equipo realmadrid = realmadridOptional.get();
			Equipo barcelona = barcelonaOptional.get();
			Equipo city = cityOptional.get();
			Equipo liverpool = liverpoolOptional.get();
			Equipo juventus = jueventusOptional.get();
			Equipo inter = interOptional.get();
			Equipo monaco = monacoOptional.get();
			Equipo psg = psgOptional.get();
			Equipo bayern = bayernOptional.get();
			Equipo borussia = borussiaOptional.get();

			Marca adidas = repositorioMarcas.findByNombre("Adidas");
			Marca nike = repositorioMarcas.findByNombre("Nike");
			Marca puma = repositorioMarcas.findByNombre("Puma");
			Marca kappa = repositorioMarcas.findByNombre("Kappa");

			byte[] fotoPorDefectoMadrid = cargarImagenPorDefecto("camisetareal.jpg");
			Camiseta camisetamadrid = new Camiseta();
			camisetamadrid.setNombre("Camiseta Real Madrid 2023/2024");
			camisetamadrid.setDescripcion("1ª Equipación del Real Madrid de la temporada 2023/2024");
			camisetamadrid.setFoto(fotoPorDefectoMadrid);
			camisetamadrid.setMarca(adidas);
			camisetamadrid.setPrecio(35);
			camisetamadrid.setEquipo(realmadrid);
			repositorioCamisetas.save(camisetamadrid);

			byte[] fotoPorDefectoBarcelona = cargarImagenPorDefecto("camisetabarcelona.jpg");
			Camiseta casmietabarcelona = new Camiseta();
			casmietabarcelona.setNombre("Camiseta FC Barcelona 2023/2024");
			casmietabarcelona.setDescripcion("1ª Equipación del FC Barcelona de la temporada 2023/2024");
			casmietabarcelona.setFoto(fotoPorDefectoBarcelona);
			casmietabarcelona.setMarca(nike);
			casmietabarcelona.setPrecio(34);
			casmietabarcelona.setEquipo(barcelona);
			repositorioCamisetas.save(casmietabarcelona);

			byte[] fotoPorDefectoCity = cargarImagenPorDefecto("camisetacity.png");
			Camiseta camisetacity = new Camiseta();
			camisetacity.setNombre("Camiseta Manchester City 2023/2024");
			camisetacity.setDescripcion("1ª Equipación del Manchester City de la temporada 2023/2024");
			camisetacity.setFoto(fotoPorDefectoCity);
			camisetacity.setMarca(nike);
			camisetacity.setPrecio(37);
			camisetacity.setEquipo(city);
			repositorioCamisetas.save(camisetacity);

			byte[] fotoPorDefectoLiverpool = cargarImagenPorDefecto("camisetaliverpool.png");
			Camiseta camisetaliverpool = new Camiseta();
			camisetaliverpool.setNombre("Camiseta Liverpool 2023/2024");
			camisetaliverpool.setDescripcion("1ª Equipación del Liverpool de la temporada 2023/2024");
			camisetaliverpool.setFoto(fotoPorDefectoLiverpool);
			camisetaliverpool.setMarca(kappa);
			camisetaliverpool.setPrecio(35);
			camisetaliverpool.setEquipo(liverpool);
			repositorioCamisetas.save(camisetaliverpool);

			byte[] fotoPorDefectoPSG = cargarImagenPorDefecto("camisetapsg.png");
			Camiseta camisetapsg = new Camiseta();
			camisetapsg.setNombre("Camiseta Paris Saint Germain 2023/2024");
			camisetapsg.setDescripcion("1ª Equipación del Paris Saint Germain de la temporada 2023/2024");
			camisetapsg.setFoto(fotoPorDefectoPSG);
			camisetapsg.setMarca(nike);
			camisetapsg.setPrecio(34);
			camisetapsg.setEquipo(psg);
			repositorioCamisetas.save(camisetapsg);

			byte[] fotoPorDefectoMonaco = cargarImagenPorDefecto("camisetamonaco.png");
			Camiseta casmietamonaco = new Camiseta();
			casmietamonaco.setNombre("Camiseta Monaco 2023/2024");
			casmietamonaco.setDescripcion("1ª Equipación del Monaco de la temporada 2023/2024");
			casmietamonaco.setFoto(fotoPorDefectoMonaco);
			casmietamonaco.setMarca(kappa);
			casmietamonaco.setPrecio(37.5);
			casmietamonaco.setEquipo(monaco);
			repositorioCamisetas.save(casmietamonaco);

			byte[] fotoPorDefectoJuve = cargarImagenPorDefecto("camisetajuve.png");
			Camiseta casmietajuve = new Camiseta();
			casmietajuve.setNombre("Camiseta Juventus 2023/2024");
			casmietajuve.setDescripcion("1ª Equipación de la Juventus de Turín de la temporada 2023/2024");
			casmietajuve.setFoto(fotoPorDefectoJuve);
			casmietajuve.setMarca(adidas);
			casmietajuve.setPrecio(34.7);
			casmietajuve.setEquipo(juventus);
			repositorioCamisetas.save(casmietajuve);

			byte[] fotoPorDefectoInter = cargarImagenPorDefecto("camisetainter.png");
			Camiseta camisetainter = new Camiseta();
			camisetainter.setNombre("Camiseta Inter de Milán 2023/2024");
			camisetainter.setDescripcion("1ª Equipación del Inter de Milán de la temporada 2023/2024");
			camisetainter.setFoto(fotoPorDefectoInter);
			camisetainter.setMarca(nike);
			camisetainter.setPrecio(34.99);
			camisetainter.setEquipo(inter);
			repositorioCamisetas.save(camisetainter);

			byte[] fotoPorDefectoBorussia = cargarImagenPorDefecto("camisetaborussia.png");
			Camiseta camisetaborussia = new Camiseta();
			camisetaborussia.setNombre("Camiseta Borussia Dortmund 2023/2024");
			camisetaborussia.setDescripcion("1ª Equipación del Borussia Dortmund de la temporada 2023/2024");
			camisetaborussia.setFoto(fotoPorDefectoBorussia);
			camisetaborussia.setMarca(puma);
			camisetaborussia.setPrecio(34);
			camisetaborussia.setEquipo(borussia);
			repositorioCamisetas.save(camisetaborussia);

			byte[] fotoPorDefectoBayern = cargarImagenPorDefecto("camisetabayern.png");
			Camiseta camisetabayern = new Camiseta();
			camisetabayern.setNombre("Camiseta Bayern de Munich 2023/2024");
			camisetabayern.setDescripcion("1ª Equipación del Bayern de Munich de la temporada 2023/2024");
			camisetabayern.setFoto(fotoPorDefectoBayern);
			camisetabayern.setMarca(adidas);
			camisetabayern.setPrecio(35.77);
			camisetabayern.setEquipo(bayern);
			repositorioCamisetas.save(camisetabayern);

		} else {
			System.out.println("No se encontró el equipo con el ID proporcionado.");
		}
	}

	public void crearPedidos(Long idUsuario, List<Long> idsCamisetas) {
		// Obtener el usuario por su ID
		Usuario usuario = new Usuario();
		usuario.setId(idUsuario);

		// Obtener las camisetas por sus IDs
		List<Camiseta> camisetas = new ArrayList<>();
		for (Long idCamiseta : idsCamisetas) {
			Optional<Camiseta> camisetaOptional = repositorioCamisetas.findById(idCamiseta);
			camisetaOptional.ifPresent(camisetas::add);
		}

		// Crear el objeto Pedido
		Pedido pedido = new Pedido();
		pedido.setFecha(new Date());  // Fecha actual
		pedido.setEstado("En proceso");  // Estado inicial
		pedido.setUsuario(usuario);  // Asignar el usuario
		pedido.setCamisetas(camisetas);  // Asignar las camisetas seleccionadas

		// Guardar el pedido en la base de datos
		repositorioPedidos.save(pedido);
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