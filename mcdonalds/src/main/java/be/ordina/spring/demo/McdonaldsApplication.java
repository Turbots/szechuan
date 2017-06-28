package be.ordina.spring.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableEurekaClient
@SpringBootApplication
@EnableBinding(OutputChannels.class)
public class McdonaldsApplication {

	public static void main(String[] args) {
		SpringApplication.run(McdonaldsApplication.class, args);
	}
}
