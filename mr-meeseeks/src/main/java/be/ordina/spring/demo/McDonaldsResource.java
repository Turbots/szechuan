package be.ordina.spring.demo;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import reactor.core.publisher.Mono;

@FeignClient(fallback = McDonaldsResource.McDonaldsFallback.class)
public interface McDonaldsResource {

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    RequestEntity<Mono<Order>> orderSzechuanSauce(Order order);

    @Component
    class McDonaldsFallback {
    }
}
