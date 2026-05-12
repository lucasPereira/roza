package br.ufsc.ine.leb.roza.core.modern.measurement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import br.ufsc.ine.leb.roza.core.modern.decomposition.TestCase;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeBlock;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeStatement;

class TestCaseProjectionMaterializerTest {

	@TempDir
	Path folder;

	@Test
	void shouldMaterializePreAssertionProjectionAsJavaFile() throws IOException {
		TestCase testCase = testCase(
				"source",
				statement("setup();"),
				statement("shared();"),
				assertion("assertEquals(1, value());"),
				statement("afterAssertion();"));

		List<MaterializedTestCase> materialized = TestCaseProjectionMaterializer.materialize(List.of(testCase), folder, "Example");

		MaterializedTestCase materializedTestCase = materialized.get(0);
		assertEquals("Example00000.java", materializedTestCase.fileName());
		assertEquals(3, materializedTestCase.firstProjectedLine());
		assertEquals(4, materializedTestCase.lastProjectedLine());
		assertEquals(2, materializedTestCase.projectedLineCount());
		assertEquals("public class Example00000 {\n"
				+ "\tpublic void test() {\n"
				+ "\t\tsetup();\n"
				+ "\t\tshared();\n"
				+ "\t}\n"
				+ "}\n", Files.readString(folder.resolve("Example00000.java"), StandardCharsets.UTF_8));
	}

	private TestCase testCase(String name, CodeStatement... statements) {
		return new TestCase(name, new CodeBlock(List.of(statements)));
	}

	private CodeStatement statement(String text) {
		return new CodeStatement(text, text);
	}

	private CodeStatement assertion(String text) {
		return new CodeStatement(text, text, true);
	}
}
