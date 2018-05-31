package be.ordina.spring.demo;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.http.MediaType;
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

	@GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public SseEmitter events() {
		SseEmitter emitter = new SseEmitter();
		emitters.add(emitter);
		emitter.onCompletion(() -> emitters.remove(emitter));
		emitter.onError(throwable -> emitters.remove(emitter));
		emitter.onTimeout(() -> emitters.remove(emitter));

		return emitter;
	}

	@StreamListener("rick")
	public void handleRick(GlipGlop glipGlop) {
		handleGlipGlop(glipGlop);
	}

	@StreamListener("meeseeks")
	public void handleMeeseeks(GlipGlop glipGlop) {
		handleGlipGlop(glipGlop);
	}

	@StreamListener("mcdonalds")
	public void handleMcdonalds(GlipGlop glipGlop) {
		handleGlipGlop(glipGlop);
	}

	@StreamListener("microverse")
	public void handleMicroverse(GlipGlop glipGlop) {
		handleGlipGlop(glipGlop);
	}

	private void handleGlipGlop(GlipGlop glipGlop) {
		emitters.parallelStream().forEach(emitter -> {
			try {
				emitter.send(glipGlop);
			} catch (IOException e) {
				emitter.complete();
				emitters.remove(emitter);
			}
		});
	}
}
