package br.ufsc.ine.leb.roza.expt.n;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

final class DuplicationVariationDistributionChart {

	private static final int PLOT_HEIGHT = 356;
	private static final int TICK_GAP = 6;
	private static final int Y_LABEL_GAP = 4;
	private static final int AXIS_LABEL_GAP = 8;
	private static final double CATEGORY_ANGLE = Math.toRadians(32);
	private static final String Y_LABEL = "Variação da duplicação (%)";
	private static final Font Y_LABEL_FONT = new Font("Helvetica", Font.PLAIN, 12);
	private static final Font TICK_FONT = new Font("Helvetica", Font.PLAIN, 11);
	private static final Font CATEGORY_FONT = new Font("Helvetica", Font.PLAIN, 10);
	private static final FontMetrics Y_LABEL_METRICS = metrics(Y_LABEL_FONT);
	private static final FontMetrics TICK_METRICS = metrics(TICK_FONT);
	private static final FontMetrics CATEGORY_METRICS = metrics(CATEGORY_FONT);

	private DuplicationVariationDistributionChart() {
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
		int yLabelX = Y_LABEL_METRICS.getAscent();
		int tickWidth = 0;
		for (int tick = 0; tick <= 5; tick++) {
			double value = minimum + range * tick / 5.0;
			tickWidth = Math.max(tickWidth, TICK_METRICS.stringWidth(String.format(Locale.ROOT, "%.0f", value)));
		}
		int left = yLabelX + Y_LABEL_METRICS.getDescent() + Y_LABEL_GAP + tickWidth + TICK_GAP;
		int top = Math.max(3, TICK_METRICS.getAscent() - 4);
		int plotWidth = Math.max(variants.size() * 118, 920 - left);
		int plotBottom = top + PLOT_HEIGHT;
		int labelY = plotBottom + AXIS_LABEL_GAP;
		int categoryWidth = plotWidth / Math.max(1, variants.size());
		double sin = Math.sin(CATEGORY_ANGLE);
		double cos = Math.cos(CATEGORY_ANGLE);
		int descent = CATEGORY_METRICS.getDescent();
		int width = left + plotWidth;
		int height = labelY + descent;
		for (int variantIndex = 0; variantIndex < variants.size(); variantIndex++) {
			int tx = left + variantIndex * categoryWidth + categoryWidth / 2 + 4;
			int headerWidth = CATEGORY_METRICS.stringWidth(variants.get(variantIndex));
			height = Math.max(height, (int) Math.ceil(labelY + headerWidth * sin + descent * cos));
			width = Math.max(width, (int) Math.ceil(tx + descent * sin));
		}
		int zeroY = yOf(0.0, minimum, range, top);
		int plotMidY = top + PLOT_HEIGHT / 2;

		StringBuilder svg = new StringBuilder();
		svg.append(String.format(Locale.ROOT, "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"%d\" height=\"%d\" viewBox=\"0 0 %d %d\">%n", width, height, width, height));
		svg.append("\t<rect width=\"100%\" height=\"100%\" fill=\"#ffffff\"/>\n");
		svg.append(String.format(Locale.ROOT, "\t<text x=\"%d\" y=\"%d\" text-anchor=\"middle\" font-family=\"Helvetica, Arial, sans-serif\" font-size=\"12\" fill=\"#374151\" transform=\"rotate(-90 %d %d)\">%s</text>%n", yLabelX, plotMidY, yLabelX, plotMidY, Y_LABEL));
		for (int tick = 0; tick <= 5; tick++) {
			double value = minimum + range * tick / 5.0;
			int y = yOf(value, minimum, range, top);
			svg.append(String.format(Locale.ROOT, "\t<line x1=\"%d\" y1=\"%d\" x2=\"%d\" y2=\"%d\" stroke=\"#e5e7eb\" stroke-width=\"1\"/>%n", left, y, left + plotWidth, y));
			svg.append(String.format(Locale.ROOT, "\t<text x=\"%d\" y=\"%d\" text-anchor=\"end\" font-family=\"Helvetica, Arial, sans-serif\" font-size=\"11\" fill=\"#6b7280\">%.0f</text>%n", left - TICK_GAP, y + 4, value));
		}
		svg.append(String.format(Locale.ROOT, "\t<line x1=\"%d\" y1=\"%d\" x2=\"%d\" y2=\"%d\" stroke=\"#111827\" stroke-width=\"1.5\"/>%n", left, top, left, plotBottom));
		svg.append(String.format(Locale.ROOT, "\t<line x1=\"%d\" y1=\"%d\" x2=\"%d\" y2=\"%d\" stroke=\"#111827\" stroke-width=\"1.5\"/>%n", left, zeroY, left + plotWidth, zeroY));

		for (int variantIndex = 0; variantIndex < variants.size(); variantIndex++) {
			int center = left + variantIndex * categoryWidth + categoryWidth / 2;
			List<Double> finite = finiteByVariant.get(variantIndex);
			if (!finite.isEmpty()) {
				double low = finite.get(0);
				double q1 = DescriptiveStats.quantile(finite, 0.25);
				double median = DescriptiveStats.median(finite);
				double q3 = DescriptiveStats.quantile(finite, 0.75);
				double high = finite.get(finite.size() - 1);
				int boxWidth = Math.min(52, categoryWidth / 2);
				svg.append(String.format(Locale.ROOT, "\t<line x1=\"%d\" y1=\"%d\" x2=\"%d\" y2=\"%d\" stroke=\"#1d4ed8\" stroke-width=\"1.5\"/>%n", center, yOf(low, minimum, range, top), center, yOf(high, minimum, range, top)));
				svg.append(String.format(Locale.ROOT, "\t<line x1=\"%d\" y1=\"%d\" x2=\"%d\" y2=\"%d\" stroke=\"#1d4ed8\" stroke-width=\"1.5\"/>%n", center - 8, yOf(low, minimum, range, top), center + 8, yOf(low, minimum, range, top)));
				svg.append(String.format(Locale.ROOT, "\t<line x1=\"%d\" y1=\"%d\" x2=\"%d\" y2=\"%d\" stroke=\"#1d4ed8\" stroke-width=\"1.5\"/>%n", center - 8, yOf(high, minimum, range, top), center + 8, yOf(high, minimum, range, top)));
				int boxTop = yOf(q3, minimum, range, top);
				int boxBottom = yOf(q1, minimum, range, top);
				svg.append(String.format(Locale.ROOT, "\t<rect x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\" fill=\"#bfdbfe\" stroke=\"#1d4ed8\" stroke-width=\"1.5\"/>%n", center - boxWidth / 2, boxTop, boxWidth, Math.max(1, boxBottom - boxTop)));
				svg.append(String.format(Locale.ROOT, "\t<line x1=\"%d\" y1=\"%d\" x2=\"%d\" y2=\"%d\" stroke=\"#1e3a8a\" stroke-width=\"2\"/>%n", center - boxWidth / 2, yOf(median, minimum, range, top), center + boxWidth / 2, yOf(median, minimum, range, top)));
			}
			List<Double> rawValues = valuesByVariant.get(variantIndex);
			for (int projectIndex = 0; projectIndex < rawValues.size(); projectIndex++) {
				Double value = rawValues.get(projectIndex);
				if (value == null || value.isNaN()) {
					continue;
				}
				int x = center + ((projectIndex % 9) - 4) * 4;
				int y = yOf(value, minimum, range, top);
				String project = projectIndex < projects.size() ? projects.get(projectIndex) : "project " + (projectIndex + 1);
				svg.append(String.format(Locale.ROOT, "\t<circle cx=\"%d\" cy=\"%d\" r=\"3\" fill=\"#2563eb\" fill-opacity=\"0.65\"><title>%s: %s</title></circle>%n", x, y, escape(project), formatPercentage(value)));
			}
			int tx = center + 4;
			svg.append(String.format(Locale.ROOT, "\t<text x=\"%d\" y=\"%d\" text-anchor=\"end\" font-family=\"Helvetica, Arial, sans-serif\" font-size=\"10\" fill=\"#111827\" transform=\"rotate(-32 %d %d)\">%s</text>%n", tx, labelY, tx, labelY, escape(variants.get(variantIndex))));
		}
		svg.append("</svg>\n");
		return svg.toString();
	}

	private static FontMetrics metrics(Font font) {
		Graphics2D graphics = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB).createGraphics();
		graphics.setFont(font);
		return graphics.getFontMetrics();
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

	private static int yOf(double value, double minimum, double range, int top) {
		return top + PLOT_HEIGHT - (int) Math.round(PLOT_HEIGHT * (value - minimum) / range);
	}

	private static String formatPercentage(double value) {
		return value == 0.0 ? "0.0%" : String.format(Locale.ROOT, "%+.1f%%", value);
	}

	private static String escape(String text) {
		return text.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
	}
}
