package be.ordina.spring.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.stream.annotation.EnableBinding;

@Slf4j
@EnableEurekaClient
@SpringBootApplication
@EnableBinding(InputChannels.class)
public class MortyApplication {

	public static void main(String[] args) {
		SpringApplication.run(MortyApplication.class, args);
	}
}
