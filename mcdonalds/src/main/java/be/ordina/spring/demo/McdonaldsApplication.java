package be.ordina.spring.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
@EnableBinding({ InputChannels.class, OutputChannels.class })
public class McdonaldsApplication {

	public static void main(String[] args) {
		SpringApplication.run(McdonaldsApplication.class, args);
	}
}
