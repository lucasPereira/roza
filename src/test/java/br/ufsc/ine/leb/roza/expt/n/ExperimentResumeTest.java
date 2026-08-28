package br.ufsc.ine.leb.roza.expt.n;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.expt.n.ThesisTables.ResultRow;

class ExperimentResumeTest {

	@Test
	void shouldNeedPrerequisitesOfMissingCompositions() {
		Set<String> needed = ExperimentResume.neededVariants(Set.of(
				"original",
				"implicit",
				"residual-implicit",
				"delegated",
				"implicit+delegated",
				"delegated+implicit"));
		assertEquals(Set.of("residual-implicit", "residual-implicit+delegated", "delegated", "delegated+residual-implicit"), needed);
	}

	@Test
	void shouldRecomputeImplicitAndResidualWhenDelegatedCompositionsAreMissing() {
		Set<String> needed = ExperimentResume.neededVariants(Set.of("original", "implicit", "residual-implicit"));
		assertEquals(Set.of(
				"implicit",
				"residual-implicit",
				"delegated",
				"implicit+delegated",
				"delegated+implicit",
				"residual-implicit+delegated",
				"delegated+residual-implicit"), needed);
	}

	@Test
	void shouldNeedNothingWhenEveryVariantIsPresent() {
		assertTrue(ExperimentResume.neededVariants(Set.copyOf(ThesisTables.TREATMENTS)).isEmpty());
		assertTrue(ExperimentResume.subjectComplete(List.of(
				row("roza", "original"),
				row("roza", "implicit"),
				row("roza", "residual-implicit"),
				row("roza", "delegated"),
				row("roza", "implicit+delegated"),
				row("roza", "delegated+implicit"),
				row("roza", "residual-implicit+delegated"),
				row("roza", "delegated+residual-implicit")), "roza"));
		assertFalse(ExperimentResume.subjectComplete(List.of(row("roza", "original")), "roza"));
	}

	@Test
	void shouldParseAndOrderComparisonRows() {
		List<ResultRow> rows = ExperimentResume.parseComparison(String.join("\n",
				"project;variant;test_classes;setups;attributes;helper_methods;total_statements;duplicated_statements;duplication_rate;duplication_difference_percentage",
				"javaparser;delegated;518;80;424;1796;15612;6764;43.3;-10.1",
				"roza;original;131;72;350;156;2710;1501;55.4;0.0",
				"roza;implicit;342;49;271;156;2869;1638;57.1;"));
		assertEquals("javaparser", rows.get(0).project);
		assertEquals(-10.1, rows.get(0).duplicationDifferencePercentage);
		assertNull(rows.get(2).duplicationDifferencePercentage);
		List<ResultRow> ordered = ExperimentResume.ordered(rows, List.of("roza", "javaparser"));
		assertEquals("roza", ordered.get(0).project);
		assertEquals("original", ordered.get(0).variant);
		assertEquals("implicit", ordered.get(1).variant);
		assertEquals("javaparser", ordered.get(2).project);
	}

	@Test
	void shouldReplaceAnExistingRowOnUpsert() {
		List<ResultRow> rows = new ArrayList<>();
		rows.add(row("roza", "implicit"));
		ExperimentResume.upsert(rows, new ResultRow("roza", "implicit", 2, 0, 0, 0, 0, 0, 0.0, 0.0));
		assertEquals(1, rows.size());
		assertEquals(2, rows.get(0).testClasses);
	}

	private static ResultRow row(String project, String variant) {
		return new ResultRow(project, variant, 1, 0, 0, 0, 0, 0, 0.0, 0.0);
	}
}
