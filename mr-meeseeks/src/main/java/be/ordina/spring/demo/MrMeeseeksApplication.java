package be.ordina.spring.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@EnableBinding({ InputChannels.class, OutputChannels.class })
public class MrMeeseeksApplication {

	public static void main(String[] args) {
		SpringApplication.run(MrMeeseeksApplication.class, args);
	}
}
