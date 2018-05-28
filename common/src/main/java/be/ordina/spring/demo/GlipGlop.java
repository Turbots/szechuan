package be.ordina.spring.demo;

public class GlipGlop {

	private RickAndMortyQuote quote;
	private String instanceIndex;

	public GlipGlop(RickAndMortyQuote quote, String instanceIndex) {
		this.quote = quote;
		this.instanceIndex = instanceIndex;
	}

	public RickAndMortyQuote getQuote() {
		return quote;
	}

	public String getInstanceIndex() {
		return instanceIndex;
	}
}
