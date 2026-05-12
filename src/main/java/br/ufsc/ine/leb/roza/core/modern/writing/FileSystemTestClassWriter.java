package br.ufsc.ine.leb.roza.core.modern.writing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

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
			Files.createDirectories(outputFolder);
			for (TestClass testClass : testClasses.testClasses()) {
				Path testClassFile = testClassFile(testClass);
				Files.createDirectories(testClassFile.getParent());
				Files.writeString(testClassFile, renderer.render(testClass));
			}
		} catch (IOException exception) {
			throw new IllegalStateException("Could not write refactored test classes.", exception);
		}
	}

	private Path testClassFile(TestClass testClass) {
		Path packageFolder = testClass.packageName()
				.map(packageName -> packageName.replace('.', '/'))
				.map(outputFolder::resolve)
				.orElse(outputFolder);
		return packageFolder.resolve(testClass.name() + ".java");
	}
}
