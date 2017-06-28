package be.ordina.spring.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@EnableScheduling
@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
@EnableBinding(OutputChannels.class)
public class MrMeeseeksApplication {

	public static void main(String[] args) {
		SpringApplication.run(MrMeeseeksApplication.class, args);
	}
}
