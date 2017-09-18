package be.ordina.spring.demo;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface OutputChannels {

	@Output
	MessageChannel meeseeks();

	@Output
	MessageChannel microverse();
}
