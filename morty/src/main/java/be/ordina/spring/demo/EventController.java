package be.ordina.spring.demo;

import com.google.common.collect.Lists;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
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
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/events")
public class EventController {

	private final List<SseEmitter> emitters = Lists.newArrayList();

	@Autowired
	public EventController(InputChannels inputChannels) {
		inputChannels.rick().subscribe(new MicroVerseMessageHandler(RickAndMortyCharacter.RICK));
		inputChannels.meeseeks().subscribe(new MicroVerseMessageHandler(RickAndMortyCharacter.MR_MEESEEKS));
		inputChannels.mcdonalds().subscribe(new MicroVerseMessageHandler(RickAndMortyCharacter.MCDONALDS));
	}

	@GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public SseEmitter events() {
		SseEmitter emitter = new SseEmitter();
		emitters.add(emitter);
		emitter.onCompletion(() -> emitters.remove(emitter));

		return emitter;
	}

	class MicroVerseMessageHandler implements MessageHandler {

		private final RickAndMortyCharacter character;

		MicroVerseMessageHandler(final RickAndMortyCharacter character) {
			this.character = character;
		}

		@Override public void handleMessage(Message<?> m) throws MessagingException {
			MicroVerseMessage microVerseMessage =
				MicroVerseMessage.builder()
					.origin(this.character)
					.message("" + m.getPayload())
					.build();

			emitters.forEach(emitter -> {
				try {
					emitter.send(microVerseMessage);
				} catch (IOException e) {
					emitter.complete();
					emitters.remove(emitter);
					log.error("IOException when trying to send event");
				}
			});
		}
	}

	@Getter
	@Builder
	private static class MicroVerseMessage {
		private RickAndMortyCharacter origin;

		private String message;
	}
}
