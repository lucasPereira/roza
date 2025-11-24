package br.ufsc.ine.leb.roza.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Performs Wilcoxon signed-rank test for paired samples.
 * This is a non-parametric statistical test to determine if there is a significant
 * difference between two related samples.
 */
public class WilcoxonSignedRankTest {

	/**
	 * Performs a Wilcoxon signed-rank test for paired samples.
	 *
	 * @param sample1 First sample (e.g., original values)
	 * @param sample2 Second sample (e.g., refactored values)
	 * @return WilcoxonTestResult containing test statistic, p-value, and significance level
	 */
	public WilcoxonTestResult test(List<Integer> sample1, List<Integer> sample2) {
		return test(sample1, sample2, 0.05);
	}

	/**
	 * Performs a Wilcoxon signed-rank test for paired samples with custom significance level.
	 *
	 * @param sample1 First sample (e.g., original values)
	 * @param sample2 Second sample (e.g., refactored values)
	 * @param alpha Significance level (e.g., 0.05 for 5%)
	 * @return WilcoxonTestResult containing test statistic, p-value, and significance level
	 */
	public WilcoxonTestResult test(List<Integer> sample1, List<Integer> sample2, double alpha) {
		if (sample1.size() != sample2.size()) {
			throw new IllegalArgumentException("Samples must have the same size");
		}

		int sampleSize = sample1.size();
		List<RankedDifference> differences = new ArrayList<>();

		// Calculate differences and their absolute values
		for (int index = 0; index < sampleSize; index++) {
			int difference = sample1.get(index) - sample2.get(index);
			if (difference != 0) { // Exclude zero differences
				differences.add(new RankedDifference(difference, Math.abs(difference)));
			}
		}

		if (differences.isEmpty()) {
			return new WilcoxonTestResult(0, 1.0, alpha, false);
		}

		// Sort by absolute difference for ranking
		differences.sort((first, second) -> Integer.compare(first.absoluteDifference, second.absoluteDifference));

		// Assign ranks (handle ties by averaging)
		for (int startIndex = 0; startIndex < differences.size(); startIndex++) {
			int endIndex = startIndex;
			while (endIndex < differences.size() - 1 && differences.get(endIndex).absoluteDifference == differences.get(endIndex + 1).absoluteDifference) {
				endIndex++;
			}
			double averageRank = (startIndex + 1 + endIndex + 1) / 2.0;
			for (int currentIndex = startIndex; currentIndex <= endIndex; currentIndex++) {
				differences.get(currentIndex).rank = averageRank;
			}
			startIndex = endIndex;
		}

		// Calculate W+ (sum of ranks for positive differences) and W- (sum for negative)
		double positiveRankSum = 0;
		double negativeRankSum = 0;
		for (RankedDifference rankedDifference : differences) {
			if (rankedDifference.difference > 0) {
				positiveRankSum += rankedDifference.rank;
			} else {
				negativeRankSum += rankedDifference.rank;
			}
		}

		// Test statistic is the smaller of W+ and W-
		double testStatistic = Math.min(positiveRankSum, negativeRankSum);
		int nonZeroDifferencesCount = differences.size();

		// Calculate z-score for large samples (n > 10)
		double expectedMean = nonZeroDifferencesCount * (nonZeroDifferencesCount + 1) / 4.0;
		double variance = nonZeroDifferencesCount * (nonZeroDifferencesCount + 1) * (2 * nonZeroDifferencesCount + 1) / 24.0;
		double zScore = (testStatistic - expectedMean) / Math.sqrt(variance);

		// Two-tailed p-value (approximate using normal distribution)
		double pValue = 2 * (1 - normalCDF(Math.abs(zScore)));

		// Check significance
		boolean isSignificant = pValue < alpha;

		return new WilcoxonTestResult(testStatistic, pValue, alpha, isSignificant);
	}

	/**
	 * Approximates the cumulative distribution function of the standard normal distribution.
	 */
	private double normalCDF(double zScore) {
		if (zScore < -8.0) return 0.0;
		if (zScore > 8.0) return 1.0;
		double sum = 0.0;
		double term = zScore;
		for (int exponent = 3; exponent < 100; exponent += 2) {
			sum += term;
			term *= zScore * zScore / exponent;
		}
		return 0.5 + sum * Math.exp(-zScore * zScore / 2) / Math.sqrt(2 * Math.PI);
	}

	private static class RankedDifference {
		int difference;
		int absoluteDifference;
		double rank;

		RankedDifference(int difference, int absoluteDifference) {
			this.difference = difference;
			this.absoluteDifference = absoluteDifference;
		}
	}

	public static class WilcoxonTestResult {
		private final double testStatistic;
		private final double pValue;
		private final double significanceLevel;
		private final boolean isSignificant;

		WilcoxonTestResult(double testStatistic, double pValue, double significanceLevel, boolean isSignificant) {
			this.testStatistic = testStatistic;
			this.pValue = pValue;
			this.significanceLevel = significanceLevel;
			this.isSignificant = isSignificant;
		}

		public double getTestStatistic() {
			return testStatistic;
		}

		public double getPValue() {
			return pValue;
		}

		public double getSignificanceLevel() {
			return significanceLevel;
		}

		public boolean isSignificant() {
			return isSignificant;
		}

		@Override
		public String toString() {
			return String.format("W=%.2f, p-value=%.4f, α=%.2f, significant=%s",
				testStatistic, pValue, significanceLevel, isSignificant ? "Yes" : "No");
		}
	}
}
