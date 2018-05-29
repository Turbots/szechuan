package be.ordina.spring.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
@EnableBinding(InputChannels.class)
public class MortyApplication {

	public static void main(String[] args) {
		SpringApplication.run(MortyApplication.class, args);
	}
}
