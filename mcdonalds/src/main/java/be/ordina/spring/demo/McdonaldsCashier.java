package be.ordina.spring.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class McdonaldsCashier {

	private final InputChannels inputChannels;
	private final OutputChannels outputChannels;

	private static final int ODDS_AT_FINDING_SZECHUAN_SAUCE = 500;
	private static final Random RAND = new Random();

	@Value("${INSTANCE_INDEX:${CF_INSTANCE_INDEX:0}}")
	private String instanceId;

	@Autowired
	public McdonaldsCashier(InputChannels inputChannels, OutputChannels outputChannels) {
		this.inputChannels = inputChannels;
		this.outputChannels = outputChannels;

		this.inputChannels.meeseeks().subscribe(message -> {
			GlipGlop glipGlop = (GlipGlop) message.getPayload();
			if (glipGlop.getQuote() == RickAndMortyQuote.PLEASE_GIVE_ME_SOME_SZECHUAN_SAUCE) {
				if (RAND.nextInt(ODDS_AT_FINDING_SZECHUAN_SAUCE) == 1) {
					this.outputChannels.mcdonalds()
						.send(MessageBuilder.withPayload(new GlipGlop(RickAndMortyQuote.YOU_ARE_A_WINNER, instanceId))
							.build());
				} else {
					this.outputChannels.mcdonalds()
						.send(MessageBuilder.withPayload(new GlipGlop(RickAndMortyQuote.SORRY_NO_LUCK, instanceId))
							.build());
				}
			}
		});
	}

}
