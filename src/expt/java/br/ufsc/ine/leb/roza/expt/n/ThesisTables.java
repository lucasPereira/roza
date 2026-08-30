package br.ufsc.ine.leb.roza.expt.n;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.ToDoubleFunction;

import br.ufsc.ine.leb.roza.core.legacy.utils.CommaSeparatedValues;

final class ThesisTables {

	static final List<String> VARIANTS = List.of(
			"implicit",
			"residual-implicit",
			"delegated",
			"implicit+delegated",
			"delegated+implicit",
			"residual-implicit+delegated",
			"delegated+residual-implicit");

	static final List<String> TREATMENTS;

	static {
		List<String> treatments = new ArrayList<>();
		treatments.add("original");
		treatments.addAll(VARIANTS);
		TREATMENTS = List.copyOf(treatments);
	}

	static final List<String[]> COMPOSITION_PAIRS = List.of(
			new String[] { "implicit+delegated", "implicit" },
			new String[] { "delegated+implicit", "delegated" },
			new String[] { "residual-implicit+delegated", "residual-implicit" },
			new String[] { "delegated+residual-implicit", "delegated" });

	private ThesisTables() {
	}

	static String vsOriginal(List<ResultRow> rows, String metricName, ToDoubleFunction<ResultRow> metric, boolean includeRelativePercent) {
		Map<String, Map<String, ResultRow>> byProject = index(rows);
		CommaSeparatedValues csv = new CommaSeparatedValues();
		csv.addLine(
				"variant",
				"w",
				"p",
				"median_difference",
				"iqr_difference",
				"median_percentage",
				"iqr_percentage",
				"improved",
				"worsened",
				"tied");
		for (String variant : VARIANTS) {
			List<Double> baseline = new ArrayList<>();
			List<Double> treatment = new ArrayList<>();
			List<Double> percents = new ArrayList<>();
			for (Map<String, ResultRow> project : byProject.values()) {
				ResultRow original = project.get("original");
				ResultRow current = project.get(variant);
				if (original == null || current == null) {
					continue;
				}
				double left = metric.applyAsDouble(original);
				double right = metric.applyAsDouble(current);
				baseline.add(left);
				treatment.add(right);
				if (includeRelativePercent && left != 0.0) {
					percents.add((right - left) / left * 100.0);
				} else if (includeRelativePercent) {
					percents.add(Double.NaN);
				} else if (left != 0.0) {
					percents.add((right - left) / left * 100.0);
				} else {
					percents.add(Double.NaN);
				}
			}
			csv.addLine(variantRow(variant, baseline, treatment, percents));
		}
		return csv.getContent();
	}

	static String friedman(List<ResultRow> rows) {
		double[][] matrix = treatmentMatrix(rows, row -> row.duplicatedStatements, TREATMENTS);
		NonparametricTests.FriedmanResult result = NonparametricTests.friedman(matrix);
		CommaSeparatedValues csv = new CommaSeparatedValues();
		csv.addLine("chi_squared", "df", "p");
		csv.addLine(DescriptiveStats.formatNumber(result.chiSquared), result.degreesOfFreedom, DescriptiveStats.formatP(result.p));
		return csv.getContent();
	}

	static String pairwise(List<ResultRow> rows) {
		Map<String, Map<String, ResultRow>> byProject = index(rows);
		List<String[]> pairs = new ArrayList<>();
		for (int first = 0; first < VARIANTS.size(); first++) {
			for (int second = first + 1; second < VARIANTS.size(); second++) {
				pairs.add(new String[] { VARIANTS.get(first), VARIANTS.get(second) });
			}
		}
		double[] rawP = new double[pairs.size()];
		List<Object[]> prepared = new ArrayList<>();
		for (int pairIndex = 0; pairIndex < pairs.size(); pairIndex++) {
			String a = pairs.get(pairIndex)[0];
			String b = pairs.get(pairIndex)[1];
			PairSummary summary = pairSummary(byProject, a, b);
			rawP[pairIndex] = summary.wilcoxon.p;
			prepared.add(new Object[] { a + " vs " + b, summary });
		}
		double[] adjusted = NonparametricTests.holm(rawP);
		CommaSeparatedValues csv = new CommaSeparatedValues();
		csv.addLine(
				"pair",
				"w",
				"p_holm",
				"median_difference",
				"iqr_difference",
				"median_percentage",
				"iqr_percentage",
				"improved",
				"worsened",
				"tied");
		for (int index = 0; index < prepared.size(); index++) {
			String label = (String) prepared.get(index)[0];
			PairSummary summary = (PairSummary) prepared.get(index)[1];
			csv.addLine(pairRow(label, summary, adjusted[index]));
		}
		return csv.getContent();
	}

	static String medians(List<ResultRow> rows) {
		CommaSeparatedValues csv = new CommaSeparatedValues();
		csv.addLine("treatment", "median_duplicated_statements", "iqr");
		for (String treatment : TREATMENTS) {
			List<Double> values = new ArrayList<>();
			for (ResultRow row : rows) {
				if (treatment.equals(row.variant)) {
					values.add((double) row.duplicatedStatements);
				}
			}
			csv.addLine(treatment, DescriptiveStats.formatNumber(DescriptiveStats.median(values)), DescriptiveStats.iqrRange(values));
		}
		return csv.getContent();
	}

	static String composition(List<ResultRow> rows) {
		Map<String, Map<String, ResultRow>> byProject = index(rows);
		CommaSeparatedValues csv = new CommaSeparatedValues();
		csv.addLine(
				"pair",
				"w",
				"p",
				"median_difference",
				"iqr_difference",
				"median_percentage",
				"iqr_percentage",
				"improved",
				"worsened",
				"tied");
		for (String[] pair : COMPOSITION_PAIRS) {
			PairSummary summary = pairSummary(byProject, pair[1], pair[0]);
			csv.addLine(pairRow(pair[0] + " vs " + pair[1], summary, summary.wilcoxon.p));
		}
		return csv.getContent();
	}

	private static Object[] variantRow(String variant, List<Double> baseline, List<Double> treatment, List<Double> percents) {
		List<Double> differences = new ArrayList<>();
		for (int index = 0; index < baseline.size(); index++) {
			differences.add(treatment.get(index) - baseline.get(index));
		}
		NonparametricTests.WilcoxonResult wilcoxon = NonparametricTests.wilcoxon(baseline, treatment);
		DescriptiveStats.Counts counts = DescriptiveStats.counts(differences);
		List<Double> finitePercents = finite(percents);
		return new Object[] {
				variant,
				DescriptiveStats.formatNumber(wilcoxon.w),
				DescriptiveStats.formatP(wilcoxon.p),
				DescriptiveStats.formatNumber(DescriptiveStats.median(differences)),
				DescriptiveStats.iqrRange(differences),
				finitePercents.isEmpty() ? "" : DescriptiveStats.formatNumber(DescriptiveStats.median(finitePercents)),
				DescriptiveStats.iqrRange(finitePercents),
				counts.improved,
				counts.worsened,
				counts.tied
		};
	}

	private static Object[] pairRow(String label, PairSummary summary, double p) {
		return new Object[] {
				label,
				DescriptiveStats.formatNumber(summary.wilcoxon.w),
				DescriptiveStats.formatP(p),
				DescriptiveStats.formatNumber(DescriptiveStats.median(summary.differences)),
				DescriptiveStats.iqrRange(summary.differences),
				summary.percents.isEmpty() ? "" : DescriptiveStats.formatNumber(DescriptiveStats.median(summary.percents)),
				DescriptiveStats.iqrRange(summary.percents),
				summary.counts.improved,
				summary.counts.worsened,
				summary.counts.tied
		};
	}

	private static PairSummary pairSummary(Map<String, Map<String, ResultRow>> byProject, String a, String b) {
		List<Double> left = new ArrayList<>();
		List<Double> right = new ArrayList<>();
		List<Double> differences = new ArrayList<>();
		List<Double> percents = new ArrayList<>();
		for (Map<String, ResultRow> project : byProject.values()) {
			ResultRow first = project.get(a);
			ResultRow second = project.get(b);
			if (first == null || second == null) {
				continue;
			}
			double from = first.duplicatedStatements;
			double to = second.duplicatedStatements;
			left.add(from);
			right.add(to);
			differences.add(to - from);
			if (from != 0.0) {
				percents.add((to - from) / from * 100.0);
			}
		}
		return new PairSummary(NonparametricTests.wilcoxon(left, right), differences, percents, DescriptiveStats.counts(differences));
	}

	private static double[][] treatmentMatrix(List<ResultRow> rows, ToDoubleFunction<ResultRow> metric, List<String> treatments) {
		Map<String, Map<String, ResultRow>> byProject = index(rows);
		List<String> projects = new ArrayList<>();
		for (String project : byProject.keySet()) {
			boolean complete = true;
			for (String treatment : treatments) {
				if (!byProject.get(project).containsKey(treatment)) {
					complete = false;
					break;
				}
			}
			if (complete) {
				projects.add(project);
			}
		}
		double[][] matrix = new double[projects.size()][treatments.size()];
		for (int subject = 0; subject < projects.size(); subject++) {
			Map<String, ResultRow> project = byProject.get(projects.get(subject));
			for (int treatment = 0; treatment < treatments.size(); treatment++) {
				ResultRow row = project.get(treatments.get(treatment));
				matrix[subject][treatment] = row == null ? Double.NaN : metric.applyAsDouble(row);
			}
		}
		return matrix;
	}

	private static Map<String, Map<String, ResultRow>> index(List<ResultRow> rows) {
		Map<String, Map<String, ResultRow>> byProject = new LinkedHashMap<>();
		for (ResultRow row : rows) {
			byProject.computeIfAbsent(row.project, key -> new LinkedHashMap<>()).put(row.variant, row);
		}
		return byProject;
	}

	private static List<Double> finite(List<Double> values) {
		List<Double> finite = new ArrayList<>();
		for (Double value : values) {
			if (value != null && !value.isNaN()) {
				finite.add(value);
			}
		}
		return finite;
	}

	private static final class PairSummary {

		private final NonparametricTests.WilcoxonResult wilcoxon;
		private final List<Double> differences;
		private final List<Double> percents;
		private final DescriptiveStats.Counts counts;

		private PairSummary(
				NonparametricTests.WilcoxonResult wilcoxon,
				List<Double> differences,
				List<Double> percents,
				DescriptiveStats.Counts counts) {
			this.wilcoxon = wilcoxon;
			this.differences = differences;
			this.percents = percents;
			this.counts = counts;
		}
	}

	static final class ResultRow {

		final String project;
		final String variant;
		final int testClasses;
		final int setups;
		final int attributes;
		final int helperMethods;
		final int totalStatements;
		final int duplicatedStatements;
		final double duplicationRate;
		final Double duplicationDifferencePercentage;

		ResultRow(
				String project,
				String variant,
				int testClasses,
				int setups,
				int attributes,
				int helperMethods,
				int totalStatements,
				int duplicatedStatements,
				double duplicationRate,
				Double duplicationDifferencePercentage) {
			this.project = project;
			this.variant = variant;
			this.testClasses = testClasses;
			this.setups = setups;
			this.attributes = attributes;
			this.helperMethods = helperMethods;
			this.totalStatements = totalStatements;
			this.duplicatedStatements = duplicatedStatements;
			this.duplicationRate = duplicationRate;
			this.duplicationDifferencePercentage = duplicationDifferencePercentage;
		}
	}
}
