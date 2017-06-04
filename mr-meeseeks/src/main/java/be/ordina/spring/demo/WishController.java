package be.ordina.spring.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/")
public class WishController {

    private final McDonaldsResource mcDonaldsResource;

    public WishController(McDonaldsResource mcDonaldsResource) {
        this.mcDonaldsResource = mcDonaldsResource;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity transferOrder(Wish wish) {
        log.info("Oooooooooh, can do!");

        return ResponseEntity.ok().build();
    }
}
