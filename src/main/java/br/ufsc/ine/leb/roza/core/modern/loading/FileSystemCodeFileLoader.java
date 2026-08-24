package br.ufsc.ine.leb.roza.core.modern.loading;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class FileSystemCodeFileLoader implements CodeFileLoader {

	private final List<Path> folders;
	private final boolean recursive;
	private final Set<String> extensions;

	public FileSystemCodeFileLoader(Path folder, boolean recursive, List<String> extensions) {
		this(List.of(folder), recursive, extensions);
	}

	public FileSystemCodeFileLoader(List<Path> folders, boolean recursive, List<String> extensions) {
		this.folders = List.copyOf(Objects.requireNonNull(folders));
		this.recursive = recursive;
		this.extensions = normalize(Objects.requireNonNull(extensions));
		this.folders.forEach(Objects::requireNonNull);
	}

	@Override
	public LoadedCodeFiles load() {
		List<CodeFile> files = new ArrayList<>();
		for (Path folder : folders) {
			files.addAll(loadFolder(folder));
		}
		files.sort(Comparator.comparing(CodeFile::source));
		return new LoadedCodeFiles(files);
	}

	private List<CodeFile> loadFolder(Path folder) {
		try (Stream<Path> files = files(folder)) {
			return files.filter(Files::isRegularFile)
					.filter(this::hasAcceptedExtension)
					.sorted()
					.map(file -> read(folder, file))
					.collect(Collectors.toList());
		} catch (IOException exception) {
			throw new UncheckedIOException(exception);
		}
	}

	private Stream<Path> files(Path folder) throws IOException {
		if (recursive) {
			return Files.walk(folder);
		}
		return Files.list(folder);
	}

	private boolean hasAcceptedExtension(Path file) {
		return extensions.isEmpty() || extensions.contains(extensionOf(file));
	}

	private String extensionOf(Path file) {
		String name = file.getFileName().toString();
		int separator = name.lastIndexOf('.');
		if (separator == -1) {
			return "";
		}
		return name.substring(separator + 1);
	}

	private CodeFile read(Path folder, Path file) {
		try {
			String relative = folder.relativize(file).toString();
			String source = folders.size() == 1 ? relative : folder.toAbsolutePath().normalize() + "/" + relative;
			return new CodeFile(source, Files.readString(file));
		} catch (IOException exception) {
			throw new UncheckedIOException(exception);
		}
	}

	private Set<String> normalize(List<String> extensions) {
		return extensions.stream().map(this::normalize).collect(Collectors.toSet());
	}

	private String normalize(String extension) {
		if (extension.startsWith(".")) {
			return extension.substring(1);
		}
		return extension;
	}
}
