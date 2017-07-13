package be.ordina.spring.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Random;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

	private static final Random RAND = new Random();

	private final OutputChannels outputChannels;

	@Value("${mcdonalds.order.chance:10}")
	private int orderChance;

	@Autowired
	public OrderController(OutputChannels outputChannels) {
		this.outputChannels = outputChannels;
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<OrderResult> makeOrder(@RequestBody @Valid Order order) {
		int chance = RAND.nextInt(this.orderChance);
		log.info("Received Order [{}] - Your ticket number [{}]", order, chance);
		if (order.getItem().toLowerCase().contains("szechuan")) {
			if (chance == 1) {
				sendMessage("Here is your Special 1988 Mulan Szechuan Dipping Sauce, sir!");
				return ResponseEntity.ok(OrderResult.ORDERED);
			} else {
				sendMessage("I don't know what you're talking about sir, what Szechuan sauce?!");
				return ResponseEntity.ok(OrderResult.ITEM_DOES_NOT_EXIST);
			}
		} else {
			sendMessage("We don't have that item sir.");
			return ResponseEntity.ok(OrderResult.ITEM_DOES_NOT_EXIST);
		}
	}

	private void sendMessage(String message) {
		log.info(message);
		this.outputChannels.mcdonalds().send(MessageBuilder.withPayload(message).build());
	}
}
