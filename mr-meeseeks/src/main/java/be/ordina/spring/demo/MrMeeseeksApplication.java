package be.ordina.spring.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@EnableScheduling
@EnableEurekaClient
@SpringBootApplication
public class MrMeeseeksApplication {

    public static void main(String[] args) {
        SpringApplication.run(MrMeeseeksApplication.class, args);

        log.info("I'M MR. MEESEEKS LOOK AT MEEEEE!!!");
    }

    @Scheduled(fixedDelay = 10000, fixedRate = 10000)
    public void restateTheObvious() {
        log.info("LOOK AT ME, I'm MR MEESEEKS");
    }
}
