package be.ordina.spring.feign;

import feign.Feign;
import feign.Logger;
import feign.hystrix.HystrixFeign;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfiguration {

    @Bean
    Logger.Level feignLogger() {
        return Logger.Level.BASIC;
    }

    @Bean
    Feign.Builder feignBuilder() {
        return HystrixFeign.builder();
    }
}
