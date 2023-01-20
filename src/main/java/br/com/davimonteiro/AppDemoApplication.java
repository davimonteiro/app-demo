package br.com.davimonteiro;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AppDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppDemoApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(DatabaseInitializer databaseInitializer) {
		return args -> {
			// Initialize the database
			databaseInitializer.populate();
		};
	}

}
