package br.ufsc.ine.leb.roza.core.modern.writing;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import br.ufsc.ine.leb.roza.core.modern.parsing.CodeAnnotation;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeBlock;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeStatement;
import br.ufsc.ine.leb.roza.core.modern.parsing.HelperMethod;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestClass;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestMethod;
import br.ufsc.ine.leb.roza.core.modern.refactoring.RefactoredTestClasses;

class FileSystemTestClassWriterTest {

	@Test
	void shouldWriteTestsInTheDefaultPackageAndKeepExistingHelperPackages(@TempDir Path tempDirectory) throws IOException {
		Path outputFolder = tempDirectory.resolve("output");

		new FileSystemTestClassWriter(outputFolder).write(new RefactoredTestClasses(
				List.of(testClass("Example", "example.tests", "alpha")),
				List.of(
						helperClass("ExistingHelper", "example.helpers"),
						helperClass("HelperClass1", null))));

		Path testFile = outputFolder.resolve("Example.java");
		assertTrue(Files.exists(testFile));
		assertFalse(Files.readString(testFile).contains("package "));
		assertFalse(Files.exists(outputFolder.resolve("example/tests/Example.java")));
		assertTrue(Files.exists(outputFolder.resolve("example/helpers/ExistingHelper.java")));
		assertTrue(Files.readString(outputFolder.resolve("example/helpers/ExistingHelper.java")).contains("package example.helpers;"));
		assertTrue(Files.exists(outputFolder.resolve("HelperClass1.java")));
		assertFalse(Files.readString(outputFolder.resolve("HelperClass1.java")).contains("package "));
	}

	@Test
	void shouldClearPreviousContentsBeforeWriting(@TempDir Path tempDirectory) throws IOException {
		Path outputFolder = tempDirectory.resolve("output");
		Files.createDirectories(outputFolder.resolve("stale"));
		Files.writeString(outputFolder.resolve("stale/OldTest.java"), "class OldTest {}");
		Files.writeString(outputFolder.resolve("OldHelper.java"), "class OldHelper {}");

		new FileSystemTestClassWriter(outputFolder).write(new RefactoredTestClasses(
				List.of(testClass("Example", "example.tests", "alpha")),
				List.of(helperClass("HelperClass1", null))));

		assertFalse(Files.exists(outputFolder.resolve("stale/OldTest.java")));
		assertFalse(Files.exists(outputFolder.resolve("OldHelper.java")));
		assertTrue(Files.exists(outputFolder.resolve("Example.java")));
		assertTrue(Files.exists(outputFolder.resolve("HelperClass1.java")));
	}

	private TestClass testClass(String name, String packageName, String methodName) {
		return new TestClass(
				name,
				packageName,
				List.of(),
				null,
				List.of(),
				List.of(),
				List.of(),
				List.of(new TestMethod(
						methodName,
						List.of(new CodeAnnotation("Test", "@Test")),
						new CodeBlock(List.of(new CodeStatement("HelperClass1.setup1();", "HelperClass1.setup1();"))))));
	}

	private TestClass helperClass(String name, String packageName) {
		return new TestClass(
				name,
				packageName,
				List.of(),
				null,
				List.of(),
				List.of(),
				List.of(new HelperMethod(
						List.of("public", "static"),
						"void",
						"setup1",
						List.of(),
						List.of(),
						new CodeBlock(List.of(new CodeStatement("login();", "login();"))))),
				List.of());
	}
}
