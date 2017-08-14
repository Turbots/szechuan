package be.ordina.spring.demo;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface InputChannels {

	@Input
	SubscribableChannel rick();
}
