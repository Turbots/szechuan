package be.ordina.spring.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SzechuanFinder implements CommandLineRunner {

	private final MeeseeksResource meeseeksResource;
	private final OutputChannels outputChannels;

	private static final int minimumRequestIntervalInMillis = 50;

	@Autowired
	public SzechuanFinder(MeeseeksResource meeseeksResource, OutputChannels outputChannels) {
		this.meeseeksResource = meeseeksResource;
		this.outputChannels = outputChannels;
	}

	@Override
	public void run(String... strings) throws Exception {
		WishResult result = WishResult.NOT_GRANTED;
		Wish wish = new Wish("1989 MCNUGGET SZECHUAN DIPPING SAUCE");
		int requestIntervalInMillis = 5000;

		while (result == WishResult.NOT_GRANTED) {
			outputChannels.rick()
				.send(MessageBuilder.withPayload("Mr Meeseeks, get me that McNugget Szechuan Dipping Sauce").build());
			ResponseEntity<WishResult> response = this.meeseeksResource.makeWish(wish);

			if (response.getStatusCode() == HttpStatus.OK) {
				result = response.getBody();
				log.info("Mr Meeseeks came back with [{}]", result);
			} else {
				log.error("Mr Meeseeks seems to be malfunctioning! [HTTP " + response.getStatusCode() + "]");
			}

			Thread.sleep(requestIntervalInMillis);
			requestIntervalInMillis = Math.max(minimumRequestIntervalInMillis, requestIntervalInMillis - 100);
		}

		log.info("Found it!");
		outputChannels.rick()
			.send(MessageBuilder.withPayload("WUB-A-DUB-A-DUB-DUB, I FINALLY GOT MY SAUCE!").build());
	}
}
