package be.ordina.spring.demo.meeseeksbox;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface OutputChannels {

	@Output MessageChannel microverse();
}
