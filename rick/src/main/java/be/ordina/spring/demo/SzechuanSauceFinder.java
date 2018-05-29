package be.ordina.spring.demo;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import static be.ordina.spring.demo.RickAndMortyQuote.*;

@Component
public class SzechuanSauceFinder {

	private final RickChannels rickChannels;

	private static final int minimumRequestIntervalInMillis = 50;

	private static boolean SEARCHING = false;

	private static final String C_137 = "C-137";

	public SzechuanSauceFinder(RickChannels rickChannels) {
		this.rickChannels = rickChannels;
	}

	@StreamListener("rick")
	public void handleMessage(GlipGlop glipGlop) {
		if (glipGlop.getQuote() == ALL_DONE) {
			stopSearching();
			this.rickChannels.microverse().send(buildMessage(WUBBA_LUBBA_DUB_DUB, C_137));
		}
	}

	private Message<?> buildMessage(RickAndMortyQuote quote, String instanceId) {
		return MessageBuilder.withPayload(new GlipGlop(quote, instanceId)).build();
	}

	@Async
	public void findThatSauce() throws InterruptedException {
		if (!SEARCHING) {
			SEARCHING = true;
			int requestIntervalInMillis = 5000;

			while (SEARCHING) {
				this.rickChannels.meeseeks().send(buildMessage(I_WANT_MY_SZECHUAN_SAUCE, C_137));

				Thread.sleep(requestIntervalInMillis);

				requestIntervalInMillis = Math.max(minimumRequestIntervalInMillis, requestIntervalInMillis - 200);
			}

			SEARCHING = false;
		}
	}

	void stopSearching() {
		SEARCHING = false;
	}
}
