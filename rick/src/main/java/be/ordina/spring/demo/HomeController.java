package be.ordina.spring.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomeController {

	private final SzechuanFinder szechuanFinder;

	@Autowired
	public HomeController(SzechuanFinder szechuanFinder) {
		this.szechuanFinder = szechuanFinder;
	}

	@GetMapping
	public String homepage() {
		return "WUB-A-DUB-A-DUB-DUB! LETS GET THAT SAUCE!";
	}

	@PostMapping
	public String findTheSauce() throws InterruptedException {
		this.szechuanFinder.findThatSauce();

		return "YOU GOT IT!";
	}
}
