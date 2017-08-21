package be.ordina.spring.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GlipGlop {

	private RickAndMortyQuote quote;
	private String instanceIndex;
}
