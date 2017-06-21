package be.ordina.spring.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@EnableScheduling
@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
public class MrMeeseeksApplication {

	public static void main(String[] args) {
		SpringApplication.run(MrMeeseeksApplication.class, args);

		log.info("I'M MR. MEESEEKS LOOK AT MEEEEE!!!");
	}

	@Scheduled(fixedRate = 10000, initialDelay = 5000)
	public void restateTheObvious() {
		log.info("LOOK AT ME, I'M MR. MEESEEKS!!!");
	}
}
