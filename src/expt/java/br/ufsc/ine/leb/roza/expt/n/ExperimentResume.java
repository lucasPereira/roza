package br.ufsc.ine.leb.roza.expt.n;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.ufsc.ine.leb.roza.expt.n.StatisticalTables.ResultRow;

final class ExperimentResume {

	private static final Map<String, String> PREREQUISITE = Map.of(
			"implicit+delegated", "implicit",
			"delegated+implicit", "delegated",
			"residual-implicit+delegated", "residual-implicit",
			"delegated+residual-implicit", "delegated");

	private ExperimentResume() {
	}

	static Set<String> variants(List<ResultRow> rows, String project) {
		Set<String> present = new LinkedHashSet<>();
		for (ResultRow row : rows) {
			if (project.equals(row.project)) {
				present.add(row.variant);
			}
		}
		return present;
	}

	static boolean subjectComplete(List<ResultRow> rows, String project) {
		return variants(rows, project).containsAll(StatisticalTables.TREATMENTS);
	}

	static Set<String> neededVariants(Set<String> present) {
		Set<String> missing = new LinkedHashSet<>(StatisticalTables.VARIANTS);
		missing.removeAll(present);
		Set<String> needed = new LinkedHashSet<>();
		for (String variant : missing) {
			addWithPrerequisites(variant, needed);
		}
		return needed;
	}

	static void upsert(List<ResultRow> rows, ResultRow row) {
		rows.removeIf(existing -> existing.project.equals(row.project) && existing.variant.equals(row.variant));
		rows.add(row);
	}

	static List<ResultRow> ordered(List<ResultRow> rows, List<String> projects) {
		Map<String, Map<String, ResultRow>> index = new LinkedHashMap<>();
		for (ResultRow row : rows) {
			index.computeIfAbsent(row.project, key -> new LinkedHashMap<>()).put(row.variant, row);
		}
		List<ResultRow> ordered = new ArrayList<>();
		Set<String> seen = new LinkedHashSet<>();
		for (String project : projects) {
			appendProject(index.get(project), ordered);
			seen.add(project);
		}
		for (String project : index.keySet()) {
			if (!seen.contains(project)) {
				appendProject(index.get(project), ordered);
			}
		}
		return ordered;
	}

	static List<ResultRow> parseComparison(String content) {
		List<ResultRow> rows = new ArrayList<>();
		if (content == null || content.isBlank()) {
			return rows;
		}
		String[] lines = content.split("\\R");
		for (int index = 1; index < lines.length; index++) {
			String line = lines[index].trim();
			if (line.isEmpty()) {
				continue;
			}
			rows.add(parseRow(line));
		}
		return rows;
	}

	private static void addWithPrerequisites(String variant, Set<String> needed) {
		String prerequisite = PREREQUISITE.get(variant);
		if (prerequisite != null) {
			addWithPrerequisites(prerequisite, needed);
		}
		needed.add(variant);
	}

	private static void appendProject(Map<String, ResultRow> byVariant, List<ResultRow> ordered) {
		if (byVariant == null) {
			return;
		}
		for (String treatment : StatisticalTables.TREATMENTS) {
			ResultRow row = byVariant.get(treatment);
			if (row != null) {
				ordered.add(row);
			}
		}
	}

	private static ResultRow parseRow(String line) {
		String[] columns = line.split(";", -1);
		if (columns.length != 8) {
			throw new IllegalArgumentException("Expected 8 columns in comparison.csv, got " + columns.length + ": " + line);
		}
		return new ResultRow(
				columns[0],
				columns[1],
				Integer.parseInt(columns[2]),
				Integer.parseInt(columns[3]),
				Integer.parseInt(columns[4]),
				Integer.parseInt(columns[5]),
				Integer.parseInt(columns[6]),
				Integer.parseInt(columns[7]));
	}
}
