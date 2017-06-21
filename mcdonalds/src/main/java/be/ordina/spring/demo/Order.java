package be.ordina.spring.demo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;

@Getter
@Setter
@ToString
public class Order {

	@NotEmpty
	private String item;
}
