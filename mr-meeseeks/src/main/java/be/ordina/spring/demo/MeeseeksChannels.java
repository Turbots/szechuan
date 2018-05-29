package be.ordina.spring.demo;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface MeeseeksChannels {

	@Input SubscribableChannel meeseeks();

	@Output MessageChannel rick();

	@Output MessageChannel mcdonalds();

	@Output MessageChannel microverse();
}
