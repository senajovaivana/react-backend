package pw.react.backend.reactbackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pw.react.backend.reactbackend.repository.UserRepository;

@SpringBootApplication
public class ReactBackendApplication {
	@Autowired
	UserRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(ReactBackendApplication.class, args);
	}

}
