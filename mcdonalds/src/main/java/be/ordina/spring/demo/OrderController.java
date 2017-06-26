package be.ordina.spring.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

	private final OrderChanceProperties orderChanceProperties;

	@Autowired
	public OrderController(OrderChanceProperties orderChanceProperties) {
		this.orderChanceProperties = orderChanceProperties;
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<OrderResult> makeOrder(@RequestBody @Valid Order order) {
		int chance = RAND.nextInt(orderChanceProperties.getChance());
		log.info("Received Order [{}] - Your ticket number [{}]", order, chance);
		if (order.getItem().toLowerCase().contains("szechuan")) {
			if (chance == 1) {
				log.info("Here is your Special 1988 Mulan Szechuan Dipping Sauce, sir!");
				return ResponseEntity.ok(OrderResult.ORDERED);
			} else {
				return ResponseEntity.ok(OrderResult.ITEM_DOES_NOT_EXIST);
			}
		} else {
			return ResponseEntity.ok(OrderResult.ITEM_DOES_NOT_EXIST);
		}
	}
}
