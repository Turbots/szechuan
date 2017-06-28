package be.ordina.spring.demo;

import be.ordina.spring.feign.FeignConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "meeseeks", path = "/wish", configuration = FeignConfiguration.class, fallback = MeeseeksResource.MeeseeksResourceFallback.class)
public interface MeeseeksResource {

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<WishResult> makeWish(@RequestBody Wish wish);

	@Slf4j @Component class MeeseeksResourceFallback implements MeeseeksResource {

		@Override
		public ResponseEntity<WishResult> makeWish(@RequestBody Wish wish) {
			log.warn("Fallback triggered");
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
		}
	}
}
