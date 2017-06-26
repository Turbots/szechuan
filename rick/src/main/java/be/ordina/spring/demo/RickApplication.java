package be.ordina.spring.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.support.MessageBuilder;

import javax.annotation.PostConstruct;

@Slf4j
@EnableEurekaClient
@EnableHystrix
@EnableFeignClients
@SpringBootApplication
@EnableBinding(OutputChannels.class)
public class RickApplication {

	public static void main(String[] args) {
		SpringApplication.run(RickApplication.class, args);
	}
}
