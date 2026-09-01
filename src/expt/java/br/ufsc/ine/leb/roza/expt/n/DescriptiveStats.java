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

	static String q1(List<Double> values) {
		return values.size() < 2 ? "" : formatNumber(quantile(values, 0.25));
	}

	static String q3(List<Double> values) {
		return values.size() < 2 ? "" : formatNumber(quantile(values, 0.75));
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
		int increased = 0;
		int decreased = 0;
		int unchanged = 0;
		for (double difference : differences) {
			if (difference > 0) {
				increased++;
			} else if (difference < 0) {
				decreased++;
			} else {
				unchanged++;
			}
		}
		return new Counts(increased, decreased, unchanged);
	}

	static final class Counts {

		final int increased;
		final int decreased;
		final int unchanged;

		Counts(int increased, int decreased, int unchanged) {
			this.increased = increased;
			this.decreased = decreased;
			this.unchanged = unchanged;
		}
	}

	static List<Double> requireNonNull(List<Double> values) {
		return Objects.requireNonNull(values);
	}
}
