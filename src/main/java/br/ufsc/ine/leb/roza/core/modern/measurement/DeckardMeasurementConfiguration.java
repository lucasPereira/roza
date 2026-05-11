package br.ufsc.ine.leb.roza.core.modern.measurement;

public final class DeckardMeasurementConfiguration {

	public static final int DEFAULT_MIN_TOKENS = 1;
	public static final int DEFAULT_STRIDE = 0;
	public static final double DEFAULT_SIMILARITY = 1.0;

	private final int minTokens;
	private final int stride;
	private final double similarity;

	public DeckardMeasurementConfiguration() {
		this(DEFAULT_MIN_TOKENS, DEFAULT_STRIDE, DEFAULT_SIMILARITY);
	}

	public DeckardMeasurementConfiguration(int minTokens, int stride, double similarity) {
		if (minTokens <= 0) {
			throw new IllegalArgumentException("Deckard min tokens must be greater than zero.");
		}
		if (stride < 0) {
			throw new IllegalArgumentException("Deckard stride must be zero or greater.");
		}
		if (similarity < 0.0 || similarity > 1.0) {
			throw new IllegalArgumentException("Deckard similarity must be between 0.0 and 1.0.");
		}
		this.minTokens = minTokens;
		this.stride = stride;
		this.similarity = similarity;
	}

	public int minTokens() {
		return minTokens;
	}

	public int stride() {
		return stride;
	}

	public double similarity() {
		return similarity;
	}
}
