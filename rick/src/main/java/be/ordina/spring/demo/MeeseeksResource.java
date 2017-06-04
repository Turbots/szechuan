package be.ordina.spring.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "meeseeks", serviceId = "meeseeks", fallback = MeeseeksResource.MeeseeksResourceFallback.class)
public interface MeeseeksResource {

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity postOrder(@RequestBody Order order);

    @Slf4j
    @Component
    class MeeseeksResourceFallback implements MeeseeksResource {

        @Override
        public ResponseEntity postOrder(@RequestBody Order order) {
            log.warn("Let me out! Let me out! This is not a dance...");
            return ResponseEntity.notFound().build();
        }
    }
}
