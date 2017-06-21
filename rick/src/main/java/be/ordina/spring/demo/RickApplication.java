package be.ordina.spring.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@EnableScheduling
@EnableEurekaClient
@EnableHystrix
@EnableFeignClients
@SpringBootApplication
public class RickApplication {

	private final MeeseeksResource meeseeksResource;

	@Autowired
	public RickApplication(MeeseeksResource meeseeksResource) {
		this.meeseeksResource = meeseeksResource;
	}

	public static void main(String[] args) {
		SpringApplication.run(RickApplication.class, args);
	}

	@Scheduled(fixedRate = 5000)
	public void reportCurrentTime() {
		Wish wish = new Wish();
		wish.setItem("1988 MULAN SZECHUAN SAUCE");

		log.info("Mr. Meeseeks, get me that delicious Szechuan sauce from McDonalds!");
		ResponseEntity<WishResult> result = this.meeseeksResource.makeWish(wish);

		if (result.getStatusCode() == HttpStatus.OK) {
			if (result.getBody() == WishResult.GRANTED) {
				log.info("HOLY SHIT THAT SAUCE IS GOOD");
			} else {
				log.info("{}??? You're gonna have to try harder Mr. Meeseeks", result.getBody());
			}
		} else {
			log.info("WHERE THE HELL ARE YOU MR. MEESEEKS?");
		}
	}
}
