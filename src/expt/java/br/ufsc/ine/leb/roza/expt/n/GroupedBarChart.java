package br.ufsc.ine.leb.roza.expt.n;

import java.util.List;
import java.util.Locale;

final class GroupedBarChart {

	private static final List<String> COLORS = List.of(
			"#6b7280",
			"#2563eb",
			"#d97706",
			"#059669",
			"#7c3ed",
			"#dc2626",
			"#0891b2",
			"#7c3aed");

	private GroupedBarChart() {
	}

	static String svg(String title, String yLabel, List<String> projects, List<String> series, List<List<Double>> valuesBySeries, boolean percent) {
		int projectCount = projects.size();
		int seriesCount = series.size();
		int left = 72;
		int right = 24;
		int top = 56;
		int bottom = 110;
		int height = 520;
		double minValue = 0.0;
		double maxValue = percent ? 100.0 : 0.0;
		for (List<Double> seriesValues : valuesBySeries) {
			for (Double value : seriesValues) {
				if (value != null && !value.isNaN()) {
					maxValue = Math.max(maxValue, value);
					minValue = Math.min(minValue, value);
				}
			}
		}
		if (!percent) {
			if (minValue < 0.0) {
				double span = Math.max(1.0, Math.max(Math.abs(minValue), Math.abs(maxValue)));
				double pad = span * 0.1;
				maxValue = Math.ceil(maxValue + pad);
				minValue = Math.floor(minValue - pad);
			} else {
				maxValue = Math.ceil(Math.max(1.0, maxValue) * 1.1);
				minValue = 0.0;
			}
		}
		double range = maxValue - minValue;
		if (range == 0.0) {
			range = 1.0;
		}
		int width = Math.max(920, left + right + projectCount * (seriesCount * 8 + 14));
		int plotWidth = width - left - right;
		int plotHeight = height - top - bottom;
		int innerGroup = plotWidth / Math.max(1, projectCount);
		int barWidth = Math.max(3, (innerGroup - 10) / seriesCount);
		int zeroY = yOf(0.0, minValue, range, top, plotHeight);
		StringBuilder svg = new StringBuilder();
		svg.append(String.format(Locale.ROOT, "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"%d\" height=\"%d\" viewBox=\"0 0 %d %d\">%n", width, height, width, height));
		svg.append("\t<rect width=\"100%\" height=\"100%\" fill=\"#ffffff\"/>\n");
		svg.append(String.format(Locale.ROOT, "\t<text x=\"%d\" y=\"32\" text-anchor=\"middle\" font-family=\"Helvetica, Arial, sans-serif\" font-size=\"18\" font-weight=\"700\" fill=\"#111827\">%s</text>%n", width / 2, escape(title)));
		svg.append(String.format(Locale.ROOT, "\t<text x=\"24\" y=\"%d\" text-anchor=\"middle\" font-family=\"Helvetica, Arial, sans-serif\" font-size=\"12\" fill=\"#374151\" transform=\"rotate(-90 24 %d)\">%s</text>%n", top + plotHeight / 2, top + plotHeight / 2, escape(yLabel)));
		int ticks = 5;
		for (int tick = 0; tick <= ticks; tick++) {
			double value = minValue + range * tick / ticks;
			int y = yOf(value, minValue, range, top, plotHeight);
			svg.append(String.format(Locale.ROOT, "\t<line x1=\"%d\" y1=\"%d\" x2=\"%d\" y2=\"%d\" stroke=\"#e5e7eb\" stroke-width=\"1\"/>%n", left, y, left + plotWidth, y));
			svg.append(String.format(Locale.ROOT, "\t<text x=\"%d\" y=\"%d\" text-anchor=\"end\" font-family=\"Helvetica, Arial, sans-serif\" font-size=\"11\" fill=\"#6b7280\">%s</text>%n", left - 8, y + 4, String.format(Locale.ROOT, "%.0f", value)));
		}
		svg.append(String.format(Locale.ROOT, "\t<line x1=\"%d\" y1=\"%d\" x2=\"%d\" y2=\"%d\" stroke=\"#111827\" stroke-width=\"1.5\"/>%n", left, top, left, top + plotHeight));
		svg.append(String.format(Locale.ROOT, "\t<line x1=\"%d\" y1=\"%d\" x2=\"%d\" y2=\"%d\" stroke=\"#111827\" stroke-width=\"1.5\"/>%n", left, top + plotHeight, left + plotWidth, top + plotHeight));
		if (minValue < 0.0 && maxValue > 0.0) {
			svg.append(String.format(Locale.ROOT, "\t<line x1=\"%d\" y1=\"%d\" x2=\"%d\" y2=\"%d\" stroke=\"#111827\" stroke-width=\"1\"/>%n", left, zeroY, left + plotWidth, zeroY));
		}
		for (int project = 0; project < projectCount; project++) {
			int groupX = left + project * innerGroup + 6;
			for (int seriesIndex = 0; seriesIndex < seriesCount; seriesIndex++) {
				Double raw = valuesBySeries.get(seriesIndex).get(project);
				if (raw == null || raw.isNaN()) {
					continue;
				}
				int barHeight = Math.max(0, (int) Math.round(plotHeight * (Math.abs(raw) / range)));
				int x = groupX + seriesIndex * barWidth;
				int y = raw >= 0.0 ? zeroY - barHeight : zeroY;
				svg.append(String.format(Locale.ROOT, "\t<rect x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\" fill=\"%s\"/>%n", x, y, Math.max(1, barWidth - 1), barHeight, COLORS.get(seriesIndex % COLORS.size())));
			}
			int labelX = groupX + (seriesCount * barWidth) / 2;
			svg.append(String.format(Locale.ROOT, "\t<text x=\"%d\" y=\"%d\" text-anchor=\"end\" font-family=\"Helvetica, Arial, sans-serif\" font-size=\"10\" fill=\"#111827\" transform=\"rotate(-40 %d %d)\">%s</text>%n", labelX, top + plotHeight + 16, labelX, top + plotHeight + 16, escape(projects.get(project))));
		}
		int legendY = height - 22;
		int legendX = left;
		for (int seriesIndex = 0; seriesIndex < seriesCount; seriesIndex++) {
			svg.append(String.format(Locale.ROOT, "\t<rect x=\"%d\" y=\"%d\" width=\"10\" height=\"10\" fill=\"%s\"/>%n", legendX, legendY, COLORS.get(seriesIndex % COLORS.size())));
			svg.append(String.format(Locale.ROOT, "\t<text x=\"%d\" y=\"%d\" font-family=\"Helvetica, Arial, sans-serif\" font-size=\"10\" fill=\"#111827\">%s</text>%n", legendX + 14, legendY + 9, escape(series.get(seriesIndex))));
			legendX += 18 + series.get(seriesIndex).length() * 6;
		}
		svg.append("</svg>\n");
		return svg.toString();
	}

	private static int yOf(double value, double minValue, double range, int top, int plotHeight) {
		return top + plotHeight - (int) Math.round(plotHeight * (value - minValue) / range);
	}

	private static String escape(String text) {
		return text.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
	}
}
