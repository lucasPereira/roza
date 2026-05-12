package br.ufsc.ine.leb.roza.core.modern.measurement;

import java.util.Objects;

public final class JplagSimilarityReport {

	private final String sourceFile;
	private final String targetFile;
	private final double sourceSimilarity;
	private final double targetSimilarity;

	public JplagSimilarityReport(String sourceFile, String targetFile, double sourceSimilarity, double targetSimilarity) {
		this.sourceFile = Objects.requireNonNull(sourceFile);
		this.targetFile = Objects.requireNonNull(targetFile);
		this.sourceSimilarity = validateSimilarity(sourceSimilarity);
		this.targetSimilarity = validateSimilarity(targetSimilarity);
	}

	public String sourceFile() {
		return sourceFile;
	}

	public String targetFile() {
		return targetFile;
	}

	public double sourceSimilarity() {
		return sourceSimilarity;
	}

	public double targetSimilarity() {
		return targetSimilarity;
	}

	private static double validateSimilarity(double similarity) {
		if (similarity < 0.0 || similarity > 1.0) {
			throw new IllegalArgumentException("JPlag similarity must be between 0.0 and 1.0.");
		}
		return similarity;
	}
}
