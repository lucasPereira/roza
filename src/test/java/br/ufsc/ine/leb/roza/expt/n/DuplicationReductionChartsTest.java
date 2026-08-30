package br.ufsc.ine.leb.roza.expt.n;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class DuplicationReductionChartsTest {

	private static final List<String> PROJECTS = List.of("alpha", "beta", "gamma");
	private static final List<String> VARIANTS = List.of("implicit", "delegated");
	private static final List<List<Double>> VALUES = List.of(
			List.of(10.0, -5.0, Double.NaN),
			List.of(20.0, -0.0, 15.0));

	@Test
	void shouldRenderDistributionWithProjectPointsAndSampleSizes() {
		String svg = DuplicationReductionDistributionChart.svg(PROJECTS, VARIANTS, VALUES);

		assertTrue(svg.contains("Distribuição da redução da duplicação"));
		assertTrue(svg.contains("<title>alpha: 10.0%</title>"));
		assertFalse(svg.contains("n="));
		assertFalse(svg.contains("Redução ="));
		assertFalse(svg.contains("NaN"));
	}

	@Test
	void shouldRenderHeatmapWithSignedReductionsAndMissingCells() {
		String svg = DuplicationReductionHeatmap.svg(PROJECTS, VARIANTS, VALUES);

		assertTrue(svg.contains("Redução da duplicação (%) por projeto e variante"));
		assertFalse(svg.contains(">Projeto</text>"));
		assertFalse(svg.contains("Redução ="));
		assertTrue(svg.contains(">+10.0%</text>"));
		assertTrue(svg.contains(">-5.0%</text>"));
		assertTrue(svg.contains(">0.0%</text>"));
		assertFalse(svg.contains(">-0.0%</text>"));
		assertTrue(svg.contains("ausente"));
		assertFalse(svg.contains("NaN"));
	}
}
