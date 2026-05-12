package br.ufsc.ine.leb.roza.core.modern.measurement;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public final class RozaProjectPaths {
	private final Path root;

	private RozaProjectPaths(Path root) {
		this.root = root;
	}

	public static RozaProjectPaths forDeckard(Path rozaRoot) {
		Path normalized = normalize(rozaRoot);
		if (!Files.isDirectory(normalized.resolve("external-tools/deckard"))) {
			throw new IllegalArgumentException("Deckard measurement must run from the Róża project root.");
		}
		return new RozaProjectPaths(normalized);
	}

	public static RozaProjectPaths forJplag(Path rozaRoot) {
		Path normalized = normalize(rozaRoot);
		if (!Files.isRegularFile(normalized.resolve("external-tools/jplag/jplag-2.11.9.jar"))) {
			throw new IllegalArgumentException("JPlag measurement must run from the Róża project root with the JPlag jar available.");
		}
		return new RozaProjectPaths(normalized);
	}

	public static RozaProjectPaths forSimian(Path rozaRoot) {
		Path normalized = normalize(rozaRoot);
		if (!Files.isRegularFile(normalized.resolve("external-tools/simian/simian-2.5.10.jar"))) {
			throw new IllegalArgumentException("Simian measurement must run from the Róża project root with the Simian jar available.");
		}
		return new RozaProjectPaths(normalized);
	}

	private static Path normalize(Path rozaRoot) {
		return Objects.requireNonNull(rozaRoot).toAbsolutePath().normalize();
	}

	public Path root() {
		return root;
	}

	public Path materializerRun(String runId) {
		return root.resolve("output").resolve("materializer").resolve(runId);
	}

	public Path measurerRun(String runId) {
		return root.resolve("output").resolve("measurer").resolve(runId);
	}

	public Path deckardToolDirectory() {
		return root.resolve("external-tools").resolve("deckard");
	}

	public Path jplagToolDirectory() {
		return root.resolve("external-tools").resolve("jplag");
	}

	public Path simianToolDirectory() {
		return root.resolve("external-tools").resolve("simian");
	}
}
