package br.com.erpsystem;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "ERP System",
				description = "Um projeto de estudo para desenvolvimento de um modelo de sistema ERP utilizando Java com Spring Boot e suas principais tecnologias.",
				version = "1.0",
				contact = @Contact(name = "linkedin", url = "https://www.linkedin.com/in/kawemorais/")
		),
		servers = @Server(url = "localhost:8080", description = "Aplicação local")
)
public class ErpSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ErpSystemApplication.class, args);
	}

}
