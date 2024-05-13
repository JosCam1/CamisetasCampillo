package es.campillo.BackCamisetas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "es.campillo")
@EntityScan(basePackages = "es.campillo")
@ComponentScan(basePackages = "es.campillo")
@EnableJpaRepositories(basePackages = "es.campillo")
public class BackCamisetasApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackCamisetasApplication.class, args);
	}

}
