package br.ufsc.ine.leb.roza.core.modern.measurement;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.ufsc.ine.leb.roza.core.modern.decomposition.TestCase;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeStatement;

final class TestCaseProjectionMaterializer {

	private static final int FIRST_PROJECTED_BODY_LINE = 3;

	private TestCaseProjectionMaterializer() {
	}

	static List<MaterializedTestCase> materialize(List<TestCase> testCases, Path materializerDirectory, String classNamePrefix) throws IOException {
		List<MaterializedTestCase> materializedTestCases = new ArrayList<>();
		for (int index = 0; index < testCases.size(); index++) {
			TestCase testCase = testCases.get(index);
			String className = String.format("%s%05d", classNamePrefix, index);
			String fileName = className + ".java";
			List<String> projection = projection(testCase);
			Files.writeString(materializerDirectory.resolve(fileName), javaSource(className, projection), StandardCharsets.UTF_8);
			materializedTestCases.add(new MaterializedTestCase(testCase, fileName, FIRST_PROJECTED_BODY_LINE, projection.size()));
		}
		return materializedTestCases;
	}

	private static List<String> projection(TestCase testCase) {
		return testCase.body()
				.statements()
				.stream()
				.takeWhile(statement -> !statement.isAssertion())
				.map(CodeStatement::originalText)
				.collect(Collectors.toList());
	}

	private static String javaSource(String className, List<String> statements) {
		StringBuilder builder = new StringBuilder();
		builder.append("public class ").append(className).append(" {\n");
		builder.append("\tpublic void test() {\n");
		for (String statement : statements) {
			builder.append("\t\t").append(statement).append("\n");
		}
		builder.append("\t}\n");
		builder.append("}\n");
		return builder.toString();
	}
}
