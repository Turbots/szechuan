package be.ordina.spring.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import static be.ordina.spring.demo.RickAndMortyQuote.*;

@Slf4j
@Component
public class SzechuanSauceFinder {

	private final OutputChannels outputChannels;
	private final InputChannels inputChannels;

	private static final int minimumRequestIntervalInMillis = 50;

	private static boolean SEARCHING = false;

	private static final String C_137 = "C-137";

	@Autowired
	public SzechuanSauceFinder(InputChannels inputChannels, OutputChannels outputChannels) {
		this.outputChannels = outputChannels;
		this.inputChannels = inputChannels;
		this.inputChannels.meeseeks().subscribe((message -> {
			GlipGlop glipGlop = (GlipGlop) message.getPayload();
			if (glipGlop.getQuote() == ALL_DONE) {
				stopSearching();
				this.outputChannels.rick().send(buildMessage(WUBBA_LUBBA_DUB_DUB, C_137));
			}
		}));
	}

	private Message<?> buildMessage(RickAndMortyQuote quote, String instanceId) {
		return MessageBuilder.withPayload(new GlipGlop(quote, instanceId)).build();
	}

	public void findThatSauce() throws InterruptedException {
		if (!SEARCHING) {
			SEARCHING = true;
			int requestIntervalInMillis = 5000;

			while (SEARCHING) {
				this.outputChannels.rick().send(buildMessage(I_WANT_MY_SZECHUAN_SAUCE, C_137));

				Thread.sleep(requestIntervalInMillis);

				requestIntervalInMillis = Math.max(minimumRequestIntervalInMillis, requestIntervalInMillis - 100);
			}

			SEARCHING = false;
		}
	}

	public void stopSearching() {
		SEARCHING = false;
	}
}
