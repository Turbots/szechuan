package be.ordina.spring.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/wish")
public class WishController {

	private final McDonaldsResource mcDonaldsResource;
	private final OutputChannels outputChannels;

	@Value("${INSTANCE_INDEX}")
	private String instanceId;

	@Autowired
	public WishController(McDonaldsResource mcDonaldsResource, OutputChannels outputChannels) {
		this.mcDonaldsResource = mcDonaldsResource;
		this.outputChannels = outputChannels;
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<WishResult> transferWish(@RequestBody Wish wish) {
		log.info("YES SIRREEEE!!!");

		ResponseEntity<OrderResult> result =
			this.mcDonaldsResource.orderSzechuanSauce(Order.builder().item(wish.getItem()).build());

		if (result.getStatusCode() == HttpStatus.OK) {
			if (result.getBody() == OrderResult.ORDERED) {
				this.outputChannels.meeseeks()
					.send(MessageBuilder.withPayload(instanceId + ": I FOUND IT! I CAN FINALLY DIE!!!").build());
				return ResponseEntity.ok(WishResult.GRANTED);
			} else {
				this.outputChannels.meeseeks()
					.send(MessageBuilder.withPayload(instanceId + ": AH NUTS, NO LUCK!!!").build());
				return ResponseEntity.ok(WishResult.NOT_GRANTED);
			}
		} else {
			this.outputChannels.meeseeks()
				.send(MessageBuilder.withPayload(instanceId + ": I CAN'T FIND A MCDONALDS!!!").build());
			return ResponseEntity.ok(WishResult.NOT_GRANTED);
		}
	}
}
