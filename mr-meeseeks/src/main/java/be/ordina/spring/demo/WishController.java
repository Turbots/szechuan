package be.ordina.spring.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/")
public class WishController {

	private final McDonaldsResource mcDonaldsResource;

	@Autowired
	public WishController(McDonaldsResource mcDonaldsResource) {
		this.mcDonaldsResource = mcDonaldsResource;
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<WishResult> transferWish(@RequestBody Wish wish) {
		log.info("YES SIRREEEE!!! {}", wish);

		ResponseEntity<OrderResult> result =
			this.mcDonaldsResource.orderSzechuanSauce(Order.builder().item(wish.getItem()).build());

		if (result.getStatusCode() == HttpStatus.OK) {
			if (result.getBody() == OrderResult.ORDERED) {
				log.info("ALL DONE! NOW I CAN FINALLY DIE!!!", wish);
				return ResponseEntity.ok(WishResult.GRANTED);
			} else {
				log.info("OH NO! MCDONALDS DOESN'T HAVE THAT ITEM! I'LL NEVER DIE!!! EXISTENCE IS PAIN!!!");
				return ResponseEntity.ok(WishResult.NOT_GRANTED);
			}
		} else {
			log.info("OH NO! I CANT FIND A MCDONALDS! I'LL NEVER DIE!!!");
			return ResponseEntity.notFound().build();
		}
	}
}
