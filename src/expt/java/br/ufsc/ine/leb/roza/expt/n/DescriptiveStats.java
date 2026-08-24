package br.ufsc.ine.leb.roza.expt.n;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

final class DescriptiveStats {

	private DescriptiveStats() {
	}

	static double median(List<Double> values) {
		return quantile(values, 0.5);
	}

	static double quantile(List<Double> values, double probability) {
		if (values.isEmpty()) {
			return Double.NaN;
		}
		List<Double> sorted = new ArrayList<>(values);
		Collections.sort(sorted);
		if (sorted.size() == 1) {
			return sorted.get(0);
		}
		double index = probability * (sorted.size() - 1);
		int lower = (int) Math.floor(index);
		int upper = (int) Math.ceil(index);
		if (lower == upper) {
			return sorted.get(lower);
		}
		double weight = index - lower;
		return sorted.get(lower) * (1.0 - weight) + sorted.get(upper) * weight;
	}

	static String iqrRange(List<Double> values) {
		if (values.size() < 2) {
			return "";
		}
		return formatNumber(quantile(values, 0.25)) + " a " + formatNumber(quantile(values, 0.75));
	}

	static String formatNumber(double value) {
		if (Double.isNaN(value)) {
			return "";
		}
		return String.format(Locale.ROOT, "%.4g", value);
	}

	static String formatP(double value) {
		if (Double.isNaN(value)) {
			return "";
		}
		return String.format(Locale.ROOT, "%.4g", value);
	}

	static Counts counts(List<Double> differences) {
		int improved = 0;
		int worsened = 0;
		int tied = 0;
		for (double difference : differences) {
			if (difference < 0) {
				improved++;
			} else if (difference > 0) {
				worsened++;
			} else {
				tied++;
			}
		}
		return new Counts(improved, worsened, tied);
	}

	static final class Counts {

		final int improved;
		final int worsened;
		final int tied;

		Counts(int improved, int worsened, int tied) {
			this.improved = improved;
			this.worsened = worsened;
			this.tied = tied;
		}
	}

	static List<Double> requireNonNull(List<Double> values) {
		return Objects.requireNonNull(values);
	}
}
