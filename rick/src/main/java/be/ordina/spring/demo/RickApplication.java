package be.ordina.spring.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
@EnableBinding(RickChannels.class)
public class RickApplication {

	public static void main(String[] args) {
		SpringApplication.run(RickApplication.class, args);
	}
}
