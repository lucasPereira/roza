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
		int groupWidth = seriesCount * 8 + 14;
		int left = 72;
		int right = 24;
		int top = 56;
		int bottom = 110;
		int width = Math.max(920, left + right + projectCount * groupWidth);
		int height = 520;
		int plotWidth = width - left - right;
		int plotHeight = height - top - bottom;
		double maxValue = percent ? 100.0 : 1.0;
		for (List<Double> seriesValues : valuesBySeries) {
			for (Double value : seriesValues) {
				if (value != null && !value.isNaN()) {
					maxValue = Math.max(maxValue, value);
				}
			}
		}
		if (!percent) {
			maxValue = Math.ceil(maxValue * 1.1);
			maxValue = Math.max(1.0, maxValue);
		}
		int innerGroup = plotWidth / Math.max(1, projectCount);
		int barWidth = Math.max(3, (innerGroup - 10) / seriesCount);
		StringBuilder svg = new StringBuilder();
		svg.append(String.format(Locale.ROOT, "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"%d\" height=\"%d\" viewBox=\"0 0 %d %d\">%n", width, height, width, height));
		svg.append("\t<rect width=\"100%\" height=\"100%\" fill=\"#ffffff\"/>\n");
		svg.append(String.format(Locale.ROOT, "\t<text x=\"%d\" y=\"32\" text-anchor=\"middle\" font-family=\"Helvetica, Arial, sans-serif\" font-size=\"18\" font-weight=\"700\" fill=\"#111827\">%s</text>%n", width / 2, escape(title)));
		svg.append(String.format(Locale.ROOT, "\t<text x=\"24\" y=\"%d\" text-anchor=\"middle\" font-family=\"Helvetica, Arial, sans-serif\" font-size=\"12\" fill=\"#374151\" transform=\"rotate(-90 24 %d)\">%s</text>%n", top + plotHeight / 2, top + plotHeight / 2, escape(yLabel)));
		int ticks = 5;
		for (int tick = 0; tick <= ticks; tick++) {
			double value = maxValue * tick / ticks;
			int y = top + plotHeight - (int) Math.round(plotHeight * tick / (double) ticks);
			svg.append(String.format(Locale.ROOT, "\t<line x1=\"%d\" y1=\"%d\" x2=\"%d\" y2=\"%d\" stroke=\"#e5e7eb\" stroke-width=\"1\"/>%n", left, y, left + plotWidth, y));
			svg.append(String.format(Locale.ROOT, "\t<text x=\"%d\" y=\"%d\" text-anchor=\"end\" font-family=\"Helvetica, Arial, sans-serif\" font-size=\"11\" fill=\"#6b7280\">%s</text>%n", left - 8, y + 4, percent ? String.format(Locale.ROOT, "%.0f", value) : String.format(Locale.ROOT, "%.0f", value)));
		}
		svg.append(String.format(Locale.ROOT, "\t<line x1=\"%d\" y1=\"%d\" x2=\"%d\" y2=\"%d\" stroke=\"#111827\" stroke-width=\"1.5\"/>%n", left, top, left, top + plotHeight));
		svg.append(String.format(Locale.ROOT, "\t<line x1=\"%d\" y1=\"%d\" x2=\"%d\" y2=\"%d\" stroke=\"#111827\" stroke-width=\"1.5\"/>%n", left, top + plotHeight, left + plotWidth, top + plotHeight));
		for (int project = 0; project < projectCount; project++) {
			int groupX = left + project * innerGroup + 6;
			for (int seriesIndex = 0; seriesIndex < seriesCount; seriesIndex++) {
				Double raw = valuesBySeries.get(seriesIndex).get(project);
				double value = raw == null || raw.isNaN() ? 0.0 : raw;
				int barHeight = (int) Math.round(plotHeight * (value / maxValue));
				int x = groupX + seriesIndex * barWidth;
				int y = top + plotHeight - barHeight;
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

	private static String escape(String text) {
		return text.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
	}
}
