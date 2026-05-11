package br.ufsc.ine.leb.roza.core.modern.loading;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FileSystemCodeFileLoaderTest {

	@TempDir
	Path folder;

	@Test
	void shouldIncludeFilesFromTheProvidedFolder() throws IOException {
		createFile(folder.resolve("test.java"), "root");

		Set<String> contents = load(false, List.of());

		assertEquals(Set.of("root"), contents);
	}

	@Test
	void shouldNotIncludeFilesFromChildFoldersOfTheProvidedFolder() throws IOException {
		createFile(folder.resolve("root.java"), "root");
		createFile(folder.resolve("child").resolve("child.java"), "child");

		Set<String> contents = load(false, List.of());

		assertEquals(Set.of("root"), contents);
	}

	@Test
	void shouldIncludeFileFromChildFoldersOfTheProvidedFolderIfRecursiveIsTrue() throws IOException {
		createFile(folder.resolve("root.java"), "root");
		createFile(folder.resolve("child").resolve("child.java"), "child");

		Set<String> contents = load(true, List.of());

		assertEquals(Set.of("root", "child"), contents);
	}

	@Test
	void shouldNotIncludeFilesIfExtensionListIsNotEmptyAndTheFileExtensionIsNotInTheList() throws IOException {
		createFile(folder.resolve("test.txt"), "txt");

		Set<String> contents = load(false, List.of("java"));

		assertEquals(Set.of(), contents);
	}

	@Test
	void shouldIncludeOnlyFilesWhoseExtensionBelongsToTheExtensionList() throws IOException {
		createFile(folder.resolve("test.java"), "java");
		createFile(folder.resolve("test.txt"), "txt");
		createFile(folder.resolve("test.kt"), "kt");

		Set<String> contents = load(false, List.of("java", "kt"));

		assertEquals(Set.of("java", "kt"), contents);
	}

	@Test
	void shouldIncludeFilesIfExtensionListIsEmpty() throws IOException {
		createFile(folder.resolve("test.java"), "java");
		createFile(folder.resolve("test.txt"), "txt");

		Set<String> contents = load(false, List.of());

		assertEquals(Set.of("java", "txt"), contents);
	}

	private Set<String> load(boolean recursive, List<String> extensions) {
		return new FileSystemCodeFileLoader(folder, recursive, extensions)
				.load()
				.codeFiles()
				.stream()
				.map(CodeFile::content)
				.collect(Collectors.toSet());
	}

	private void createFile(Path file, String content) throws IOException {
		Files.createDirectories(file.getParent());
		Files.writeString(file, content);
	}
}
