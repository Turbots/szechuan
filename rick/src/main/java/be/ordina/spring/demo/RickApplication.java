package be.ordina.spring.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.support.MessageBuilder;

@Slf4j
@EnableEurekaClient
@EnableHystrix
@EnableFeignClients
@SpringBootApplication
@EnableBinding(Rick.class)
public class RickApplication implements CommandLineRunner {

	private final MeeseeksResource meeseeksResource;
	private final Rick rick;

	@Autowired
	public RickApplication(MeeseeksResource meeseeksResource, Rick rick) {
		this.meeseeksResource = meeseeksResource;
		this.rick = rick;
	}

	public static void main(String[] args) {
		SpringApplication.run(RickApplication.class, args);
	}

	@Override public void run(String... strings) throws Exception {
		while (true) {

			Wish wish = new Wish("1988 MULAN SZECHUAN SAUCE");

			log.info("Mr. Meeseeks, get me that delicious Szechuan sauce from McDonalds!");
			ResponseEntity<WishResult> result = this.meeseeksResource.makeWish(wish);

			if (result.getStatusCode() == HttpStatus.OK) {
				rick.voice().send(MessageBuilder.withPayload("GOT RESPONSE").build());
				if (result.getBody() == WishResult.GRANTED) {
					log.info("HOLY SHIT THAT SAUCE IS GOOD");
					rick.voice().send(MessageBuilder.withPayload("HOLY SHIT THAT SAUCE IS GOOD").build());
				} else {
					log.info("You suck Mr. Meeseeks", result.getBody());
					rick.voice()
						.send(MessageBuilder.withPayload("You suck Mr. Meeseeks").build());
				}
			}

			Thread.sleep(1000);
		}
	}
}
