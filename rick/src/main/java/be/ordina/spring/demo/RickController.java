package be.ordina.spring.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class RickController {

	private final SzechuanSauceFinder szechuanSauceFinder;

	@Autowired
	public RickController(SzechuanSauceFinder szechuanSauceFinder) {
		this.szechuanSauceFinder = szechuanSauceFinder;
	}

	@GetMapping
	public void startSearching() throws InterruptedException {
		this.szechuanSauceFinder.findThatSauce();
	}

	@PostMapping
	public void stop() {
		this.szechuanSauceFinder.stopSearching();
	}
}
