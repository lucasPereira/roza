package br.ufsc.ine.leb.roza.expt.n;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

final class NonparametricTests {

	private NonparametricTests() {
	}

	static WilcoxonResult wilcoxon(List<Double> baseline, List<Double> treatment) {
		if (baseline.size() != treatment.size()) {
			throw new IllegalArgumentException("Wilcoxon requires paired samples of the same length.");
		}
		List<Double> differences = new ArrayList<>();
		for (int index = 0; index < baseline.size(); index++) {
			differences.add(treatment.get(index) - baseline.get(index));
		}
		return wilcoxonDifferences(differences);
	}

	static WilcoxonResult wilcoxonDifferences(List<Double> differences) {
		List<SignedDifference> nonzero = new ArrayList<>();
		for (double difference : differences) {
			if (difference != 0.0) {
				nonzero.add(new SignedDifference(difference));
			}
		}
		if (nonzero.isEmpty()) {
			return new WilcoxonResult(0.0, 1.0);
		}
		nonzero.sort(Comparator.comparingDouble(item -> Math.abs(item.value)));
		double[] ranks = averageRanks(nonzero);
		double wPositive = 0.0;
		double wNegative = 0.0;
		for (int index = 0; index < nonzero.size(); index++) {
			if (nonzero.get(index).value > 0) {
				wPositive += ranks[index];
			} else {
				wNegative += ranks[index];
			}
		}
		double w = Math.min(wPositive, wNegative);
		int n = nonzero.size();
		double mean = n * (n + 1) / 4.0;
		double tieCorrection = 0.0;
		int start = 0;
		while (start < n) {
			int end = start;
			while (end + 1 < n && Double.compare(Math.abs(nonzero.get(end + 1).value), Math.abs(nonzero.get(start).value)) == 0) {
				end++;
			}
			int size = end - start + 1;
			if (size > 1) {
				tieCorrection += size * (size * size - 1.0);
			}
			start = end + 1;
		}
		double variance = n * (n + 1) * (2.0 * n + 1) / 24.0 - tieCorrection / 48.0;
		if (variance <= 0) {
			return new WilcoxonResult(w, 1.0);
		}
		double z = (wPositive - mean) / Math.sqrt(variance);
		double p = Math.min(1.0, 2.0 * (1.0 - standardNormalCdf(Math.abs(z))));
		return new WilcoxonResult(w, p);
	}

	static FriedmanResult friedman(double[][] subjectsByTreatment) {
		int n = subjectsByTreatment.length;
		int k = subjectsByTreatment[0].length;
		double[] rankSums = new double[k];
		for (int subject = 0; subject < n; subject++) {
			double[] ranks = midranks(subjectsByTreatment[subject]);
			for (int treatment = 0; treatment < k; treatment++) {
				rankSums[treatment] += ranks[treatment];
			}
		}
		double sumSquares = 0.0;
		for (double rankSum : rankSums) {
			sumSquares += rankSum * rankSum;
		}
		double chiSquared = 12.0 / (n * k * (k + 1)) * sumSquares - 3.0 * n * (k + 1);
		int degreesOfFreedom = k - 1;
		double p = chiSquaredCdfUpper(chiSquared, degreesOfFreedom);
		return new FriedmanResult(chiSquared, degreesOfFreedom, p);
	}

	static double[] holm(double[] pValues) {
		int m = pValues.length;
		Integer[] order = new Integer[m];
		for (int index = 0; index < m; index++) {
			order[index] = index;
		}
		Arrays.sort(order, Comparator.comparingDouble(index -> pValues[index]));
		double[] adjusted = new double[m];
		double running = 0.0;
		for (int rank = 0; rank < m; rank++) {
			double candidate = (m - rank) * pValues[order[rank]];
			running = Math.max(running, candidate);
			adjusted[order[rank]] = Math.min(1.0, running);
		}
		return adjusted;
	}

	private static double[] averageRanks(List<SignedDifference> sortedByAbs) {
		double[] ranks = new double[sortedByAbs.size()];
		int start = 0;
		while (start < sortedByAbs.size()) {
			int end = start;
			while (end + 1 < sortedByAbs.size()
					&& Double.compare(Math.abs(sortedByAbs.get(end + 1).value), Math.abs(sortedByAbs.get(start).value)) == 0) {
				end++;
			}
			double rank = (start + 1 + end + 1) / 2.0;
			for (int index = start; index <= end; index++) {
				ranks[index] = rank;
			}
			start = end + 1;
		}
		return ranks;
	}

	private static double[] midranks(double[] values) {
		Integer[] order = new Integer[values.length];
		for (int index = 0; index < values.length; index++) {
			order[index] = index;
		}
		Arrays.sort(order, Comparator.comparingDouble(index -> values[index]));
		double[] ranks = new double[values.length];
		int start = 0;
		while (start < values.length) {
			int end = start;
			while (end + 1 < values.length && Double.compare(values[order[end + 1]], values[order[start]]) == 0) {
				end++;
			}
			double rank = (start + 1 + end + 1) / 2.0;
			for (int index = start; index <= end; index++) {
				ranks[order[index]] = rank;
			}
			start = end + 1;
		}
		return ranks;
	}

	private static double standardNormalCdf(double z) {
		return 0.5 * (1.0 + erf(z / Math.sqrt(2.0)));
	}

	private static double erf(double x) {
		double t = 1.0 / (1.0 + 0.5 * Math.abs(x));
		double tau = t * Math.exp(-x * x - 1.26551223
				+ t * (1.00002368
				+ t * (0.37409196
				+ t * (0.09678418
				+ t * (-0.18628806
				+ t * (0.27886807
				+ t * (-1.13520398
				+ t * (1.48851587
				+ t * (-0.82215223
				+ t * 0.17087277)))))))));
		return x >= 0 ? 1.0 - tau : tau - 1.0;
	}

	private static double chiSquaredCdfUpper(double chiSquared, int degreesOfFreedom) {
		if (chiSquared <= 0) {
			return 1.0;
		}
		return 1.0 - regularizedGammaP(degreesOfFreedom / 2.0, chiSquared / 2.0);
	}

	private static double regularizedGammaP(double a, double x) {
		if (x <= 0) {
			return 0.0;
		}
		double sum = 1.0 / a;
		double term = sum;
		for (int n = 1; n < 200; n++) {
			term *= x / (a + n);
			sum += term;
			if (Math.abs(term) < 1e-12 * Math.abs(sum)) {
				break;
			}
		}
		return Math.min(1.0, Math.exp(-x + a * Math.log(x) - logGamma(a)) * sum);
	}

	private static double logGamma(double z) {
		double[] coefficients = {
				76.18009172947146,
				-86.50532032941677,
				24.01409824083091,
				-1.231739572450155,
				0.1208650973866179e-2,
				-0.5395239384953e-5
		};
		double x = z;
		double y = z;
		double tmp = x + 5.5;
		tmp -= (x + 0.5) * Math.log(tmp);
		double ser = 1.000000000190015;
		for (double coefficient : coefficients) {
			ser += coefficient / ++y;
		}
		return -tmp + Math.log(2.5066282746310005 * ser / x);
	}

	private static final class SignedDifference {

		private final double value;

		private SignedDifference(double value) {
			this.value = value;
		}
	}

	static final class WilcoxonResult {

		final double w;
		final double p;

		WilcoxonResult(double w, double p) {
			this.w = w;
			this.p = p;
		}
	}

	static final class FriedmanResult {

		final double chiSquared;
		final int degreesOfFreedom;
		final double p;

		FriedmanResult(double chiSquared, int degreesOfFreedom, double p) {
			this.chiSquared = chiSquared;
			this.degreesOfFreedom = degreesOfFreedom;
			this.p = p;
		}
	}
}
