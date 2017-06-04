package be.ordina.spring.demo;

import be.ordina.spring.feign.FeignConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@EnableScheduling
@EnableFeignClients(defaultConfiguration = FeignConfiguration.class)
@EnableEurekaClient
@EnableHystrix
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
        Order order = new Order();
        order.setItem("SZECHUAN SAUCE");

        log.info("Mr. Meeseeks, get me that delicious Szechuan sauce from McDonalds!");
        this.meeseeksResource.postOrder(order);
    }
}
