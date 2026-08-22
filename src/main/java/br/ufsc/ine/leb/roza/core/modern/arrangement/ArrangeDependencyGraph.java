package br.ufsc.ine.leb.roza.core.modern.arrangement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.ufsc.ine.leb.roza.core.modern.decomposition.TestCase;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeStatement;

public final class ArrangeDependencyGraph {

	private final List<CodeStatement> statements;
	private final List<Set<Integer>> predecessors;
	private final boolean valid;

	private ArrangeDependencyGraph(List<CodeStatement> statements, List<Set<Integer>> predecessors, boolean valid) {
		this.statements = List.copyOf(statements);
		this.predecessors = copy(predecessors);
		this.valid = valid;
	}

	public static ArrangeDependencyGraph build(TestCase testCase) {
		List<CodeStatement> statements = ArrangeProjection.arrangeStatements(testCase);
		StatementDependencyAnalyzer analyzer = new StatementDependencyAnalyzer();
		List<Set<Integer>> predecessors = emptyPredecessors(statements.size());
		Map<String, Integer> lastDefinition = new HashMap<>();
		Map<String, Set<Integer>> readsSinceDefinition = new HashMap<>();
		for (int statementIndex = 0; statementIndex < statements.size(); statementIndex++) {
			StatementDependencyAnalyzer.Analysis analysis = analyzer.analyze(statements.get(statementIndex)).orElse(null);
			if (analysis == null) {
				return new ArrangeDependencyGraph(statements, emptyPredecessors(statements.size()), false);
			}
			for (String usedName : analysis.uses()) {
				Integer definitionIndex = lastDefinition.get(usedName);
				if (definitionIndex != null) {
					predecessors.get(statementIndex).add(definitionIndex);
				}
				readsSinceDefinition.computeIfAbsent(usedName, ignored -> new LinkedHashSet<>()).add(statementIndex);
			}
			for (String definedName : analysis.definitions()) {
				Integer definitionIndex = lastDefinition.get(definedName);
				if (definitionIndex != null) {
					predecessors.get(statementIndex).add(definitionIndex);
				}
				for (int readerIndex : readsSinceDefinition.getOrDefault(definedName, Set.of())) {
					if (readerIndex < statementIndex) {
						predecessors.get(statementIndex).add(readerIndex);
					}
				}
				readsSinceDefinition.remove(definedName);
				lastDefinition.put(definedName, statementIndex);
			}
		}
		return new ArrangeDependencyGraph(statements, predecessors, true);
	}

	public int size() {
		return statements.size();
	}

	public CodeStatement statement(int index) {
		return statements.get(index);
	}

	public Set<Integer> predecessors(int index) {
		return predecessors.get(index);
	}

	public boolean valid() {
		return valid;
	}

	private static List<Set<Integer>> emptyPredecessors(int size) {
		List<Set<Integer>> predecessors = new ArrayList<>();
		for (int index = 0; index < size; index++) {
			predecessors.add(new LinkedHashSet<>());
		}
		return predecessors;
	}

	private static List<Set<Integer>> copy(List<Set<Integer>> predecessors) {
		List<Set<Integer>> copy = new ArrayList<>();
		for (Set<Integer> statementPredecessors : predecessors) {
			copy.add(Set.copyOf(statementPredecessors));
		}
		return List.copyOf(copy);
	}
}
