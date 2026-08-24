package br.ufsc.ine.leb.roza.expt.n;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.expt.n.Subjects.Subject;

class SubjectsTest {

	@Test
	void shouldUseTheJavaFolderOfEveryCoberturaModuleIncludingFlushWar() {
		assertEquals(
				List.of(
						"cobertura-flush-war/src/test/java",
						"cobertura/src/test/java",
						"conversion/conversion-api/src/test/java",
						"metrics/metrics-api/src/test/java",
						"metrics/metrics-model/src/test/java"),
				relativeFolders("cobertura", Path.of("external-projects/cobertura")));
	}

	@Test
	void shouldLoadEveryTestSourceFolderOfEachSubject() {
		List<String> names = Subjects.all().stream().map(Subject::name).collect(Collectors.toList());
		assertEquals(
				List.of(
						"roza",
						"commons-csv",
						"commons-lang",
						"commons-math",
						"commons-text",
						"java-string-similarity",
						"javaparser",
						"jfreechart",
						"joda-money",
						"gson",
						"java-hamcrest",
						"ektorp",
						"rest-assured",
						"junit4",
						"cobertura",
						"couchdb-lucene",
						"picon",
						"selenium",
						"saas+teste",
						"saas+teste+moodle",
						"saas+teste+selenium",
						"saas+teste+service"),
				names);

		Map<String, List<String>> expected = Map.ofEntries(
				Map.entry("roza", List.of("src/test/java")),
				Map.entry("commons-csv", List.of("src/test/java")),
				Map.entry("commons-lang", List.of("src/test/java")),
				Map.entry("commons-math", List.of(
						"commons-math-core/src/test/java",
						"commons-math-legacy-core/src/test/java",
						"commons-math-legacy-exception/src/test/java",
						"commons-math-legacy/src/test/java",
						"commons-math-neuralnet/src/test/java",
						"commons-math-transform/src/test/java")),
				Map.entry("commons-text", List.of("src/test/java")),
				Map.entry("java-string-similarity", List.of("src/test/java")),
				Map.entry("javaparser", List.of(
						"javaparser-core-generators/src/test/java",
						"javaparser-core-serialization/src/test/java",
						"javaparser-core-testing-bdd/src/test/java",
						"javaparser-core-testing/src/test/java",
						"javaparser-symbol-solver-testing/src/test/java")),
				Map.entry("jfreechart", List.of("src/test/java")),
				Map.entry("joda-money", List.of("src/test/java")),
				Map.entry("gson", List.of(
						"extras/src/test/java",
						"gson/src/test/java",
						"proto/src/test/java",
						"test-graal-native-image/src/test/java",
						"test-jpms/src/test/java",
						"test-shrinker/src/test/java")),
				Map.entry("java-hamcrest", List.of("hamcrest/src/test/java")),
				Map.entry("ektorp", List.of(
						"org.ektorp.android/src/test/java",
						"org.ektorp.spring/src/test/java",
						"org.ektorp/src/test/java")),
				Map.entry("rest-assured", List.of(
						"examples/jackson3-example/src/test/java",
						"examples/rest-assured-itest-java-osgi/src/test/java",
						"examples/rest-assured-itest-java/src/test/java",
						"examples/spring-mvc-webapp/src/test/java",
						"examples/spring7-mvc-webapp/src/test/java",
						"json-path/src/test/java",
						"modules/json-schema-validator/src/test/java",
						"modules/spring-mock-mvc/src/test/java",
						"modules/spring-web-test-client/src/test/java",
						"rest-assured-common/src/test/java",
						"rest-assured/src/test/java",
						"xml-path/src/test/java")),
				Map.entry("junit4", List.of("src/test/java")),
				Map.entry("couchdb-lucene", List.of("src/test/java")),
				Map.entry("picon", List.of("test")),
				Map.entry("selenium", List.of("java/test")),
				Map.entry("saas+teste", List.of(".")),
				Map.entry("saas+teste+moodle", List.of(".")),
				Map.entry("saas+teste+selenium", List.of(".")),
				Map.entry("saas+teste+service", List.of(".")));

		expected.forEach((name, folders) -> assertEquals(folders, relativeFolders(name, rootOf(name)), name));
	}

	private Path rootOf(String name) {
		if ("roza".equals(name)) {
			return Path.of(".");
		}
		if (name.startsWith("saas+")) {
			return Path.of("external-projects/saas-unificado").resolve(name);
		}
		return Path.of("external-projects").resolve(name);
	}

	private List<String> relativeFolders(String name, Path root) {
		Path absoluteRoot = root.toAbsolutePath().normalize();
		return subject(name).existingFolders()
				.stream()
				.map(folder -> normalize(absoluteRoot.relativize(folder.toAbsolutePath().normalize())))
				.sorted()
				.collect(Collectors.toList());
	}

	private String normalize(Path relative) {
		String text = relative.toString().replace('\\', '/');
		if (text.isEmpty()) {
			return ".";
		}
		return text;
	}

	private Subject subject(String name) {
		return Subjects.all().stream().filter(candidate -> candidate.name().equals(name)).findFirst().orElseThrow();
	}
}
