package be.ordina.spring.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static be.ordina.spring.demo.RickAndMortyQuote.I_WANT_MY_SZECHUAN_SAUCE;
import static be.ordina.spring.demo.RickAndMortyQuote.YOU_ARE_A_WINNER;

@Component
public class MrMeeseeksRoutine {

	private final MeeseeksChannels meeseeksChannels;

	private static final List<RickAndMortyQuote> GREETINGS = new ArrayList<>();
	private static final List<RickAndMortyQuote> WHINES = new ArrayList<>();

	private static final Random RAND = new Random();

	@Value("${INSTANCE_INDEX:${CF_INSTANCE_INDEX:0}}")
	private String instanceId;

	@Autowired
	public MrMeeseeksRoutine(MeeseeksChannels meeseeksChannels) {
		this.meeseeksChannels = meeseeksChannels;

		GREETINGS.add(RickAndMortyQuote.IM_MR_MEESEEKS_LOOK_AT_ME);
		GREETINGS.add(RickAndMortyQuote.HEY_THERE_IM_MR_MEESEEKS);
		GREETINGS.add(RickAndMortyQuote.LOOK_AT_ME_IM_MR_MEESEEKS);

		WHINES.add(RickAndMortyQuote.EXISTENCE_IS_PAIN);
		WHINES.add(RickAndMortyQuote.NOW_I_LL_NEVER_DIE);
	}

	@StreamListener("meeseeks")
	public void handleMessage(GlipGlop glipGlop) {
		if (glipGlop.getQuote() == I_WANT_MY_SZECHUAN_SAUCE) {
			this.meeseeksChannels.microverse().send(MessageBuilder
				.withPayload(new GlipGlop(RickAndMortyQuote.OOOH_YEAH_CAN_DO, instanceId))
				.build());
			this.meeseeksChannels.mcdonalds().send(MessageBuilder
				.withPayload(new GlipGlop(RickAndMortyQuote.PLEASE_GIVE_ME_SOME_SZECHUAN_SAUCE, instanceId))
				.build());
		} else if (glipGlop.getQuote() == YOU_ARE_A_WINNER) {
			this.meeseeksChannels.rick().send(
				MessageBuilder.withPayload(new GlipGlop(RickAndMortyQuote.ALL_DONE, instanceId)).build());
		}
	}

	@Scheduled(fixedDelay = 5000)
	public void run() {
		if (RAND.nextBoolean()) {
			RickAndMortyQuote quote = GREETINGS.get(RAND.nextInt(GREETINGS.size()));
			this.meeseeksChannels.microverse().send(
				MessageBuilder.withPayload(new GlipGlop(quote, instanceId)).build());
		} else {
			RickAndMortyQuote quote = WHINES.get(RAND.nextInt(WHINES.size()));
			this.meeseeksChannels.microverse().send(
				MessageBuilder.withPayload(new GlipGlop(quote, instanceId)).build());
		}
	}
}
