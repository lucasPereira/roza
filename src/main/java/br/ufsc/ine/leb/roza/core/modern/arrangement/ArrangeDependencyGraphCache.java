package br.ufsc.ine.leb.roza.core.modern.arrangement;

import java.util.HashMap;
import java.util.Map;

import br.ufsc.ine.leb.roza.core.modern.decomposition.TestCase;

public final class ArrangeDependencyGraphCache {

	private final Map<TestCase, ArrangeDependencyGraph> graphs = new HashMap<>();

	public ArrangeDependencyGraph graph(TestCase testCase) {
		return graphs.computeIfAbsent(testCase, ArrangeDependencyGraph::build);
	}
}
