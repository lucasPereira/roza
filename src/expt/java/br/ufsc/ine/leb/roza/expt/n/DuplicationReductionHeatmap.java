package br.ufsc.ine.leb.roza.expt.n;

import java.util.List;
import java.util.Locale;

final class DuplicationReductionHeatmap {

	private static final int LEFT = 150;
	private static final int RIGHT = 170;
	private static final int TOP = 116;
	private static final int BOTTOM = 82;
	private static final int CELL_WIDTH = 112;
	private static final int ROW_HEIGHT = 23;
	private static final String MISSING = "#d1d5db";

	private DuplicationReductionHeatmap() {
	}

	static String svg(List<String> projects, List<String> variants, List<List<Double>> valuesByVariant) {
		int width = LEFT + RIGHT + variants.size() * CELL_WIDTH;
		int height = TOP + BOTTOM + projects.size() * ROW_HEIGHT;
		double scale = maximumAbsolute(valuesByVariant);
		StringBuilder svg = new StringBuilder();
		svg.append(String.format(Locale.ROOT, "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"%d\" height=\"%d\" viewBox=\"0 0 %d %d\">%n", width, height, width, height));
		svg.append("\t<rect width=\"100%\" height=\"100%\" fill=\"#ffffff\"/>\n");
		svg.append(String.format(Locale.ROOT, "\t<text x=\"%d\" y=\"32\" text-anchor=\"middle\" font-family=\"Helvetica, Arial, sans-serif\" font-size=\"18\" font-weight=\"700\" fill=\"#111827\">Redução da duplicação (%%) por projeto e variante</text>%n", width / 2));
		for (int variantIndex = 0; variantIndex < variants.size(); variantIndex++) {
			int x = LEFT + variantIndex * CELL_WIDTH + CELL_WIDTH / 2;
			svg.append(String.format(Locale.ROOT, "\t<text x=\"%d\" y=\"99\" text-anchor=\"start\" font-family=\"Helvetica, Arial, sans-serif\" font-size=\"10\" fill=\"#111827\" transform=\"rotate(-34 %d 99)\">%s</text>%n", x - 8, x - 8, escape(variants.get(variantIndex))));
		}
		for (int projectIndex = 0; projectIndex < projects.size(); projectIndex++) {
			int y = TOP + projectIndex * ROW_HEIGHT;
			svg.append(String.format(Locale.ROOT, "\t<text x=\"%d\" y=\"%d\" text-anchor=\"end\" font-family=\"Helvetica, Arial, sans-serif\" font-size=\"10\" fill=\"#111827\">%s</text>%n", LEFT - 9, y + 15, escape(projects.get(projectIndex))));
			for (int variantIndex = 0; variantIndex < variants.size(); variantIndex++) {
				Double value = value(valuesByVariant, variantIndex, projectIndex);
				int x = LEFT + variantIndex * CELL_WIDTH;
				String fill = value == null || value.isNaN() ? MISSING : color(value, scale);
				svg.append(String.format(Locale.ROOT, "\t<rect x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\" fill=\"%s\" stroke=\"#ffffff\" stroke-width=\"1\"/>%n", x, y, CELL_WIDTH, ROW_HEIGHT, fill));
				if (value != null && !value.isNaN()) {
					svg.append(String.format(Locale.ROOT, "\t<text x=\"%d\" y=\"%d\" text-anchor=\"middle\" font-family=\"Helvetica, Arial, sans-serif\" font-size=\"10\" fill=\"%s\">%s</text>%n", x + CELL_WIDTH / 2, y + 15, textColor(value, scale), formatPercentage(value)));
				}
			}
		}
		int legendY = TOP + projects.size() * ROW_HEIGHT + 25;
		double[] legendValues = { -scale, -scale / 2.0, 0.0, scale / 2.0, scale };
		String[] legendLabels = { "piora", "", "0%", "", "melhora" };
		int legendStart = LEFT;
		for (int index = 0; index < legendValues.length; index++) {
			int x = legendStart + index * 56;
			svg.append(String.format(Locale.ROOT, "\t<rect x=\"%d\" y=\"%d\" width=\"56\" height=\"12\" fill=\"%s\"/>%n", x, legendY, color(legendValues[index], scale)));
			if (!legendLabels[index].isEmpty()) {
				svg.append(String.format(Locale.ROOT, "\t<text x=\"%d\" y=\"%d\" text-anchor=\"middle\" font-family=\"Helvetica, Arial, sans-serif\" font-size=\"9\" fill=\"#4b5563\">%s</text>%n", x + 28, legendY + 25, legendLabels[index]));
			}
		}
		int missingX = legendStart + 320;
		svg.append(String.format(Locale.ROOT, "\t<rect x=\"%d\" y=\"%d\" width=\"18\" height=\"12\" fill=\"%s\"/>%n", missingX, legendY, MISSING));
		svg.append(String.format(Locale.ROOT, "\t<text x=\"%d\" y=\"%d\" font-family=\"Helvetica, Arial, sans-serif\" font-size=\"9\" fill=\"#4b5563\">ausente</text>%n", missingX + 24, legendY + 10));
		svg.append("</svg>\n");
		return svg.toString();
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
		int[] endpoint = value < 0.0 ? new int[] { 185, 28, 28 } : new int[] { 21, 128, 61 };
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
