package be.ordina.spring.demo;

import be.ordina.spring.feign.FeignConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "mcdonalds", fallback = McDonaldsResource.McDonaldsFallback.class, configuration = FeignConfiguration.class)
public interface McDonaldsResource {

	@PostMapping(path = "/order", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<OrderResult> orderSzechuanSauce(Order order);

	@Slf4j
	@Component class McDonaldsFallback implements McDonaldsResource {

		@Override public ResponseEntity<OrderResult> orderSzechuanSauce(Order order) {
			log.warn("I CAN'T TAKE IT ANYMORE! I JUST WANNA DIE!");

			return ResponseEntity.notFound().build();
		}
	}
}
