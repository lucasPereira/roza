package br.ufsc.ine.leb.roza.core.modern.loading;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class FileSystemCodeFileLoader implements CodeFileLoader {

	private final Path folder;
	private final boolean recursive;
	private final Set<String> extensions;

	public FileSystemCodeFileLoader(Path folder, boolean recursive, List<String> extensions) {
		this.folder = Objects.requireNonNull(folder);
		this.recursive = recursive;
		this.extensions = normalize(Objects.requireNonNull(extensions));
	}

	@Override
	public LoadedCodeFiles load() {
		try (Stream<Path> files = files()) {
			return new LoadedCodeFiles(files.filter(Files::isRegularFile)
					.filter(this::hasAcceptedExtension)
					.sorted()
					.map(this::read)
					.collect(Collectors.toList()));
		} catch (IOException exception) {
			throw new UncheckedIOException(exception);
		}
	}

	private Stream<Path> files() throws IOException {
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

	private CodeFile read(Path file) {
		try {
			return new CodeFile(folder.relativize(file).toString(), Files.readString(file));
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
