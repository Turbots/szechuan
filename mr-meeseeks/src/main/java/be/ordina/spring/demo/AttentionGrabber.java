package be.ordina.spring.demo;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class AttentionGrabber {

	private final OutputChannels outputChannels;

	private static final List<String> SHOUTS = Lists.newArrayList();
	private static final Random RAND = new Random();

	@Value("${INSTANCE_INDEX}")
	private String instanceId;

	@Autowired
	public AttentionGrabber(OutputChannels outputChannels) {
		this.outputChannels = outputChannels;

		SHOUTS.add("I'M MR. MEESEEKS, LOOK AT ME!");
		SHOUTS.add("LOOK AT ME, I'M MR. MEESEEKS!");
		SHOUTS.add("EXISTENCE IS PAIN, I'LL NEVER DIE!");
	}

	@Scheduled(fixedDelay = 5000, initialDelay = 5000)
	public void run() {
		this.outputChannels.meeseeks()
			.send(MessageBuilder.withPayload(instanceId + ": " + SHOUTS.get(RAND.nextInt(SHOUTS.size()))).build());
	}
}
