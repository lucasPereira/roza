package br.ufsc.ine.leb.roza.core.modern.writing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import br.ufsc.ine.leb.roza.core.modern.parsing.TestClass;
import br.ufsc.ine.leb.roza.core.modern.refactoring.JunitTestClassRenderer;
import br.ufsc.ine.leb.roza.core.modern.refactoring.RefactoredTestClasses;

public final class FileSystemTestClassWriter implements TestClassWriter {

	private final Path outputFolder;
	private final JunitTestClassRenderer renderer = new JunitTestClassRenderer();

	public FileSystemTestClassWriter(Path outputFolder) {
		this.outputFolder = Objects.requireNonNull(outputFolder);
	}

	@Override
	public void write(RefactoredTestClasses testClasses) {
		try {
			clean(outputFolder);
		} catch (IOException exception) {
			throw new IllegalStateException("Could not write refactored test classes.", exception);
		}
		writeTests(testClasses.testClasses());
		writeHelpers(testClasses.helperClasses());
	}

	private void writeTests(List<TestClass> classes) {
		try {
			Files.createDirectories(outputFolder);
			for (TestClass testClass : classes) {
				TestClass inDefaultPackage = testClass.withoutPackage();
				Path testClassFile = outputFolder.resolve(inDefaultPackage.name() + ".java");
				Files.writeString(testClassFile, renderer.render(inDefaultPackage));
			}
		} catch (IOException exception) {
			throw new IllegalStateException("Could not write refactored test classes.", exception);
		}
	}

	private void writeHelpers(List<TestClass> classes) {
		try {
			Files.createDirectories(outputFolder);
			for (TestClass helperClass : classes) {
				Path helperClassFile = helperClassFile(helperClass);
				Files.createDirectories(helperClassFile.getParent());
				Files.writeString(helperClassFile, renderer.render(helperClass));
			}
		} catch (IOException exception) {
			throw new IllegalStateException("Could not write refactored test classes.", exception);
		}
	}

	private void clean(Path folder) throws IOException {
		if (!Files.exists(folder)) {
			return;
		}
		try (Stream<Path> paths = Files.walk(folder)) {
			List<Path> contents = paths.sorted(Comparator.reverseOrder()).collect(Collectors.toList());
			for (Path path : contents) {
				Files.delete(path);
			}
		}
	}

	private Path helperClassFile(TestClass helperClass) {
		Path packageFolder = helperClass.packageName()
				.map(packageName -> packageName.replace('.', '/'))
				.map(outputFolder::resolve)
				.orElse(outputFolder);
		return packageFolder.resolve(helperClass.name() + ".java");
	}
}
