package br.ufsc.ine.leb.roza.expt.n;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Locale;

final class DuplicationVariationHeatmap {

	private static final int CELL_WIDTH = 112;
	private static final int ROW_HEIGHT = 23;
	private static final int LABEL_GAP = 6;
	private static final double HEADER_ANGLE = Math.toRadians(34);
	private static final Font LABEL_FONT = new Font("Helvetica", Font.PLAIN, 10);
	private static final FontMetrics LABEL_METRICS = metrics(LABEL_FONT);
	private static final String MISSING = "#d1d5db";

	private DuplicationVariationHeatmap() {
	}

	static String svg(List<String> projects, List<String> variants, List<List<Double>> valuesByVariant) {
		int labelWidth = maxWidth(projects, LABEL_METRICS);
		int left = labelWidth + LABEL_GAP;
		double sin = Math.sin(HEADER_ANGLE);
		double cos = Math.cos(HEADER_ANGLE);
		int headerWidth = maxWidth(variants, LABEL_METRICS);
		int ascent = LABEL_METRICS.getAscent();
		int descent = LABEL_METRICS.getDescent();
		int headerY = (int) Math.ceil(headerWidth * sin + ascent * cos);
		int top = headerY + 17;
		int lastHeaderX = left + (variants.size() - 1) * CELL_WIDTH + CELL_WIDTH / 2 - 8;
		int headerRight = (int) Math.ceil(lastHeaderX + headerWidth * cos + descent * sin);
		int gridRight = left + variants.size() * CELL_WIDTH;
		int width = Math.max(gridRight, headerRight);
		int height = top + projects.size() * ROW_HEIGHT;
		double scale = maximumAbsolute(valuesByVariant);
		StringBuilder svg = new StringBuilder();
		svg.append(String.format(Locale.ROOT, "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"%d\" height=\"%d\" viewBox=\"0 0 %d %d\">%n", width, height, width, height));
		svg.append("\t<rect width=\"100%\" height=\"100%\" fill=\"#ffffff\"/>\n");
		for (int variantIndex = 0; variantIndex < variants.size(); variantIndex++) {
			int x = left + variantIndex * CELL_WIDTH + CELL_WIDTH / 2;
			svg.append(String.format(Locale.ROOT, "\t<text x=\"%d\" y=\"%d\" text-anchor=\"start\" font-family=\"Helvetica, Arial, sans-serif\" font-size=\"10\" fill=\"#111827\" transform=\"rotate(-34 %d %d)\">%s</text>%n", x - 8, headerY, x - 8, headerY, escape(variants.get(variantIndex))));
		}
		for (int projectIndex = 0; projectIndex < projects.size(); projectIndex++) {
			int y = top + projectIndex * ROW_HEIGHT;
			svg.append(String.format(Locale.ROOT, "\t<text x=\"%d\" y=\"%d\" text-anchor=\"end\" font-family=\"Helvetica, Arial, sans-serif\" font-size=\"10\" fill=\"#111827\">%s</text>%n", labelWidth, y + 15, escape(projects.get(projectIndex))));
			for (int variantIndex = 0; variantIndex < variants.size(); variantIndex++) {
				Double value = value(valuesByVariant, variantIndex, projectIndex);
				int x = left + variantIndex * CELL_WIDTH;
				String fill = value == null || value.isNaN() ? MISSING : color(value, scale);
				svg.append(String.format(Locale.ROOT, "\t<rect x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\" fill=\"%s\" stroke=\"#ffffff\" stroke-width=\"1\"/>%n", x, y, CELL_WIDTH, ROW_HEIGHT, fill));
				if (value != null && !value.isNaN()) {
					svg.append(String.format(Locale.ROOT, "\t<text x=\"%d\" y=\"%d\" text-anchor=\"middle\" font-family=\"Helvetica, Arial, sans-serif\" font-size=\"10\" fill=\"%s\">%s</text>%n", x + CELL_WIDTH / 2, y + 15, textColor(value, scale), formatPercentage(value)));
				}
			}
		}
		svg.append("</svg>\n");
		return svg.toString();
	}

	private static FontMetrics metrics(Font font) {
		Graphics2D graphics = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB).createGraphics();
		graphics.setFont(font);
		return graphics.getFontMetrics();
	}

	private static int maxWidth(List<String> labels, FontMetrics metrics) {
		int maximum = 0;
		for (String label : labels) {
			maximum = Math.max(maximum, metrics.stringWidth(label));
		}
		return maximum;
	}

	private static Double value(List<List<Double>> valuesByVariant, int variantIndex, int projectIndex) {
		if (variantIndex >= valuesByVariant.size() || projectIndex >= valuesByVariant.get(variantIndex).size()) {
			return Double.NaN;
		}
		return valuesByVariant.get(variantIndex).get(projectIndex);
	}

	private static double maximumAbsolute(List<List<Double>> valuesByVariant) {
		double maximum = 1.0;
		for (List<Double> values : valuesByVariant) {
			for (Double value : values) {
				if (value != null && !value.isNaN()) {
					maximum = Math.max(maximum, Math.abs(value));
				}
			}
		}
		return maximum;
	}

	private static String color(double value, double scale) {
		double strength = Math.min(1.0, Math.abs(value) / scale);
		int[] neutral = { 243, 244, 246 };
		int[] endpoint = value < 0.0 ? new int[] { 21, 128, 61 } : new int[] { 185, 28, 28 };
		int red = interpolate(neutral[0], endpoint[0], strength);
		int green = interpolate(neutral[1], endpoint[1], strength);
		int blue = interpolate(neutral[2], endpoint[2], strength);
		return String.format(Locale.ROOT, "#%02x%02x%02x", red, green, blue);
	}

	private static int interpolate(int from, int to, double strength) {
		return (int) Math.round(from + (to - from) * strength);
	}

	private static String textColor(double value, double scale) {
		return Math.abs(value) / scale > 0.58 ? "#ffffff" : "#111827";
	}

	private static String formatPercentage(double value) {
		return value == 0.0 ? "0.0%" : String.format(Locale.ROOT, "%+.1f%%", value);
	}

	private static String escape(String text) {
		return text.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
	}
}
