package be.ordina.spring.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

	private final List<SseEmitter> emitters = new ArrayList<>();

	@Autowired
	public EventController(InputChannels inputChannels) {
		GlipGlopHandler glipGlopHandler = new GlipGlopHandler();
		inputChannels.rick().subscribe(glipGlopHandler);
		inputChannels.meeseeks().subscribe(glipGlopHandler);
		inputChannels.mcdonalds().subscribe(glipGlopHandler);
		inputChannels.microverse().subscribe(glipGlopHandler);
	}

	@GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public SseEmitter events() {
		SseEmitter emitter = new SseEmitter();
		emitters.add(emitter);
		emitter.onCompletion(() -> emitters.remove(emitter));
		emitter.onError(throwable -> emitters.remove(emitter));
		emitter.onTimeout(() -> emitters.remove(emitter));

		return emitter;
	}

	class GlipGlopHandler implements MessageHandler {

		@Override public void handleMessage(Message<?> m) throws MessagingException {
			emitters.forEach(emitter -> {
				try {
					emitter.send(m.getPayload());
				} catch (IOException e) {
					emitter.complete();
					emitters.remove(emitter);
				}
			});
		}
	}
}
