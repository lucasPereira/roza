package br.ufsc.ine.leb.roza.expt.n;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

final class DuplicationReductionDistributionChart {

	private static final int LEFT = 76;
	private static final int RIGHT = 24;
	private static final int TOP = 64;
	private static final int BOTTOM = 140;
	private static final int HEIGHT = 560;

	private DuplicationReductionDistributionChart() {
	}

	static String svg(List<String> projects, List<String> variants, List<List<Double>> valuesByVariant) {
		List<List<Double>> finiteByVariant = new ArrayList<>();
		double minimum = 0.0;
		double maximum = 0.0;
		for (List<Double> values : valuesByVariant) {
			List<Double> finite = finite(values);
			finiteByVariant.add(finite);
			for (double value : finite) {
				minimum = Math.min(minimum, value);
				maximum = Math.max(maximum, value);
			}
		}
		double padding = Math.max(1.0, (maximum - minimum) * 0.1);
		minimum = Math.floor(minimum - padding);
		maximum = Math.ceil(maximum + padding);
		double range = maximum - minimum;
		int width = Math.max(920, LEFT + RIGHT + variants.size() * 118);
		int plotWidth = width - LEFT - RIGHT;
		int plotHeight = HEIGHT - TOP - BOTTOM;
		int categoryWidth = plotWidth / Math.max(1, variants.size());
		int zeroY = yOf(0.0, minimum, range, plotHeight);

		StringBuilder svg = new StringBuilder();
		svg.append(String.format(Locale.ROOT, "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"%d\" height=\"%d\" viewBox=\"0 0 %d %d\">%n", width, HEIGHT, width, HEIGHT));
		svg.append("\t<rect width=\"100%\" height=\"100%\" fill=\"#ffffff\"/>\n");
		svg.append(String.format(Locale.ROOT, "\t<text x=\"%d\" y=\"32\" text-anchor=\"middle\" font-family=\"Helvetica, Arial, sans-serif\" font-size=\"18\" font-weight=\"700\" fill=\"#111827\">Distribuição da redução da duplicação</text>%n", width / 2));
		svg.append(String.format(Locale.ROOT, "\t<text x=\"24\" y=\"%d\" text-anchor=\"middle\" font-family=\"Helvetica, Arial, sans-serif\" font-size=\"12\" fill=\"#374151\" transform=\"rotate(-90 24 %d)\">Redução da duplicação (%%)</text>%n", TOP + plotHeight / 2, TOP + plotHeight / 2));
		for (int tick = 0; tick <= 5; tick++) {
			double value = minimum + range * tick / 5.0;
			int y = yOf(value, minimum, range, plotHeight);
			svg.append(String.format(Locale.ROOT, "\t<line x1=\"%d\" y1=\"%d\" x2=\"%d\" y2=\"%d\" stroke=\"#e5e7eb\" stroke-width=\"1\"/>%n", LEFT, y, LEFT + plotWidth, y));
			svg.append(String.format(Locale.ROOT, "\t<text x=\"%d\" y=\"%d\" text-anchor=\"end\" font-family=\"Helvetica, Arial, sans-serif\" font-size=\"11\" fill=\"#6b7280\">%.0f</text>%n", LEFT - 8, y + 4, value));
		}
		svg.append(String.format(Locale.ROOT, "\t<line x1=\"%d\" y1=\"%d\" x2=\"%d\" y2=\"%d\" stroke=\"#111827\" stroke-width=\"1.5\"/>%n", LEFT, TOP, LEFT, TOP + plotHeight));
		svg.append(String.format(Locale.ROOT, "\t<line x1=\"%d\" y1=\"%d\" x2=\"%d\" y2=\"%d\" stroke=\"#111827\" stroke-width=\"1.5\"/>%n", LEFT, zeroY, LEFT + plotWidth, zeroY));

		for (int variantIndex = 0; variantIndex < variants.size(); variantIndex++) {
			int center = LEFT + variantIndex * categoryWidth + categoryWidth / 2;
			List<Double> finite = finiteByVariant.get(variantIndex);
			if (!finite.isEmpty()) {
				double low = finite.get(0);
				double q1 = DescriptiveStats.quantile(finite, 0.25);
				double median = DescriptiveStats.median(finite);
				double q3 = DescriptiveStats.quantile(finite, 0.75);
				double high = finite.get(finite.size() - 1);
				int boxWidth = Math.min(52, categoryWidth / 2);
				svg.append(String.format(Locale.ROOT, "\t<line x1=\"%d\" y1=\"%d\" x2=\"%d\" y2=\"%d\" stroke=\"#1d4ed8\" stroke-width=\"1.5\"/>%n", center, yOf(low, minimum, range, plotHeight), center, yOf(high, minimum, range, plotHeight)));
				svg.append(String.format(Locale.ROOT, "\t<line x1=\"%d\" y1=\"%d\" x2=\"%d\" y2=\"%d\" stroke=\"#1d4ed8\" stroke-width=\"1.5\"/>%n", center - 8, yOf(low, minimum, range, plotHeight), center + 8, yOf(low, minimum, range, plotHeight)));
				svg.append(String.format(Locale.ROOT, "\t<line x1=\"%d\" y1=\"%d\" x2=\"%d\" y2=\"%d\" stroke=\"#1d4ed8\" stroke-width=\"1.5\"/>%n", center - 8, yOf(high, minimum, range, plotHeight), center + 8, yOf(high, minimum, range, plotHeight)));
				int boxTop = yOf(q3, minimum, range, plotHeight);
				int boxBottom = yOf(q1, minimum, range, plotHeight);
				svg.append(String.format(Locale.ROOT, "\t<rect x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\" fill=\"#bfdbfe\" stroke=\"#1d4ed8\" stroke-width=\"1.5\"/>%n", center - boxWidth / 2, boxTop, boxWidth, Math.max(1, boxBottom - boxTop)));
				svg.append(String.format(Locale.ROOT, "\t<line x1=\"%d\" y1=\"%d\" x2=\"%d\" y2=\"%d\" stroke=\"#1e3a8a\" stroke-width=\"2\"/>%n", center - boxWidth / 2, yOf(median, minimum, range, plotHeight), center + boxWidth / 2, yOf(median, minimum, range, plotHeight)));
			}
			List<Double> rawValues = valuesByVariant.get(variantIndex);
			for (int projectIndex = 0; projectIndex < rawValues.size(); projectIndex++) {
				Double value = rawValues.get(projectIndex);
				if (value == null || value.isNaN()) {
					continue;
				}
				int x = center + ((projectIndex % 9) - 4) * 4;
				int y = yOf(value, minimum, range, plotHeight);
				String project = projectIndex < projects.size() ? projects.get(projectIndex) : "project " + (projectIndex + 1);
				svg.append(String.format(Locale.ROOT, "\t<circle cx=\"%d\" cy=\"%d\" r=\"3\" fill=\"#2563eb\" fill-opacity=\"0.65\"><title>%s: %s</title></circle>%n", x, y, escape(project), formatPercentage(value)));
			}
			svg.append(String.format(Locale.ROOT, "\t<text x=\"%d\" y=\"%d\" text-anchor=\"end\" font-family=\"Helvetica, Arial, sans-serif\" font-size=\"10\" fill=\"#111827\" transform=\"rotate(-32 %d %d)\">%s</text>%n", center + 4, TOP + plotHeight + 20, center + 4, TOP + plotHeight + 20, escape(variants.get(variantIndex))));
		}
		svg.append("</svg>\n");
		return svg.toString();
	}

	private static List<Double> finite(List<Double> values) {
		List<Double> finite = new ArrayList<>();
		for (Double value : values) {
			if (value != null && !value.isNaN()) {
				finite.add(value);
			}
		}
		Collections.sort(finite);
		return finite;
	}

	private static int yOf(double value, double minimum, double range, int plotHeight) {
		return TOP + plotHeight - (int) Math.round(plotHeight * (value - minimum) / range);
	}

	private static String formatPercentage(double value) {
		return value == 0.0 ? "0.0%" : String.format(Locale.ROOT, "%.1f%%", value);
	}

	private static String escape(String text) {
		return text.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
	}
}
