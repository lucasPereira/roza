package br.ufsc.ine.leb.roza.core.modern.arrangement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.core.modern.decomposition.TestCase;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeBlock;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeStatement;

class ExtractableArrangeRunsTest {

	@Test
	void shouldKeepARunSharedByASubsetWhenTheShortestArrangeIsEmpty() {
		TestCase first = testCase("alpha", "createUser();", "login();", "assertTrue(true);");
		TestCase second = testCase("beta", "deleteUser();", "login();", "assertFalse(false);");
		TestCase emptyArrange = testCase("gamma", "assertEquals(1, 1);");

		List<ExtractableArrangeRun> runs = ExtractableArrangeRuns.nWay(List.of(first, second, emptyArrange), 1);

		assertEquals(1, runs.size());
		assertEquals(List.of("login();"), runs.get(0).statements().stream().map(CodeStatement::normalizedText).collect(Collectors.toList()));
		assertTrue(runs.get(0).appliesTo(0));
		assertTrue(runs.get(0).appliesTo(1));
		assertEquals(-1, runs.get(0).startFor(2));
	}

	private TestCase testCase(String name, String... statements) {
		List<CodeStatement> coded = List.of(statements).stream()
				.map(text -> new CodeStatement(text, text, text.startsWith("assert")))
				.collect(Collectors.toList());
		return new TestCase(name, new CodeBlock(coded));
	}
}
