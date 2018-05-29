package be.ordina.spring.demo;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface McDonaldsChannels {

	@Input SubscribableChannel mcdonalds();

	@Output MessageChannel meeseeks();
}
