package br.ufsc.ine.leb.roza.core.modern.writing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import br.ufsc.ine.leb.roza.core.modern.parsing.CodeAnnotation;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeBlock;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeStatement;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestClass;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestMethod;
import br.ufsc.ine.leb.roza.core.modern.refactoring.RefactoredTestClasses;

class FileSystemTestClassWriterTest {

	@TempDir
	Path temporaryFolder;

	@Test
	void shouldWriteRefactoredTestClassesToOutputFolder() throws IOException {
		TestClass testClass = new TestClass(
				"GeneratedTest",
				List.of("import org.junit.Test;"),
				null,
				List.of(),
				List.of(),
				List.of(),
				List.of(new TestMethod("alpha", List.of(annotation("Test")), block("assertTrue(true);"))));
		Path outputFolder = temporaryFolder.resolve("generated");

		new FileSystemTestClassWriter(outputFolder).write(new RefactoredTestClasses(List.of(testClass)));

		Path writtenFile = outputFolder.resolve("GeneratedTest.java");
		assertTrue(Files.exists(writtenFile));
		assertEquals(String.join(
				"\n",
				"import org.junit.Test;",
				"",
				"public class GeneratedTest {",
				"\t@Test",
				"\tpublic void alpha() {",
				"\t\tassertTrue(true);",
				"\t}",
				"",
				"}"),
				Files.readString(writtenFile));
	}

	private CodeAnnotation annotation(String name) {
		return new CodeAnnotation(name, "@" + name);
	}

	private CodeBlock block(String... statements) {
		return new CodeBlock(List.of(statements).stream().map(statement -> new CodeStatement(statement, statement)).collect(Collectors.toList()));
	}
}
