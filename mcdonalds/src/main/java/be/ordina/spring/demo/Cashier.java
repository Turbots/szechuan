package be.ordina.spring.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class Cashier {

	private static final int ODDS_AT_FINDING_SZECHUAN_SAUCE = 500;
	private static final Random RAND = new Random();

	private final McDonaldsChannels mcDonaldsChannels;

	@Value("${INSTANCE_INDEX:${CF_INSTANCE_INDEX:0}}")
	private String instanceId;

	private int luckyNumber;

	public Cashier(McDonaldsChannels mcDonaldsChannels) {
		this.luckyNumber = RAND.nextInt(ODDS_AT_FINDING_SZECHUAN_SAUCE);

		this.mcDonaldsChannels = mcDonaldsChannels;
	}

	@StreamListener("mcdonalds")
	public void handleMessage(GlipGlop glipGlop) {
		if (glipGlop.getQuote() == RickAndMortyQuote.PLEASE_GIVE_ME_SOME_SZECHUAN_SAUCE) {
			int randomInt = RAND.nextInt(ODDS_AT_FINDING_SZECHUAN_SAUCE);
			if (randomInt == luckyNumber) {
				mcDonaldsChannels.meeseeks().send(
					MessageBuilder.withPayload(new GlipGlop(RickAndMortyQuote.YOU_ARE_A_WINNER, instanceId))
						.build());
			} else {
				mcDonaldsChannels.meeseeks().send(
					MessageBuilder.withPayload(new GlipGlop(RickAndMortyQuote.SORRY_NO_LUCK, instanceId)).build());
			}
		}
	}
}
