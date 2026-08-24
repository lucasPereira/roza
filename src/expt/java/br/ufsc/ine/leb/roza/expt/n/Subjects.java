package br.ufsc.ine.leb.roza.expt.n;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

final class Subjects {

	private Subjects() {
	}

	static List<Subject> all() {
		Path external = Path.of("external-projects");
		return List.of(
				new Subject("roza", List.of(Path.of("src/test/java"))),
				folders("commons-csv", external.resolve("commons-csv"), "src/test/java"),
				folders("commons-lang", external.resolve("commons-lang"), "src/test/java"),
				folders("commons-math", external.resolve("commons-math"),
						"commons-math-core/src/test/java",
						"commons-math-legacy/src/test/java",
						"commons-math-legacy-core/src/test/java",
						"commons-math-legacy-exception/src/test/java",
						"commons-math-neuralnet/src/test/java",
						"commons-math-transform/src/test/java"),
				folders("commons-text", external.resolve("commons-text"), "src/test/java"),
				folders("java-string-similarity", external.resolve("java-string-similarity"), "src/test/java"),
				folders("javaparser", external.resolve("javaparser"),
						"javaparser-core-generators/src/test/java",
						"javaparser-core-serialization/src/test/java",
						"javaparser-core-testing/src/test/java",
						"javaparser-core-testing-bdd/src/test/java",
						"javaparser-symbol-solver-testing/src/test/java"),
				folders("jfreechart", external.resolve("jfreechart"), "src/test/java"),
				folders("joda-money", external.resolve("joda-money"), "src/test/java"),
				folders("gson", external.resolve("gson"),
						"extras/src/test/java",
						"gson/src/test/java",
						"proto/src/test/java",
						"test-graal-native-image/src/test/java",
						"test-jpms/src/test/java",
						"test-shrinker/src/test/java"),
				folders("java-hamcrest", external.resolve("java-hamcrest"), "hamcrest/src/test/java"),
				folders("ektorp", external.resolve("ektorp"),
						"org.ektorp/src/test/java",
						"org.ektorp.android/src/test/java",
						"org.ektorp.spring/src/test/java"),
				folders("rest-assured", external.resolve("rest-assured"),
						"examples/jackson3-example/src/test/java",
						"examples/rest-assured-itest-java/src/test/java",
						"examples/rest-assured-itest-java-osgi/src/test/java",
						"examples/spring-mvc-webapp/src/test/java",
						"examples/spring7-mvc-webapp/src/test/java",
						"json-path/src/test/java",
						"modules/json-schema-validator/src/test/java",
						"modules/spring-mock-mvc/src/test/java",
						"modules/spring-web-test-client/src/test/java",
						"rest-assured/src/test/java",
						"rest-assured-common/src/test/java",
						"xml-path/src/test/java"),
				folders("junit4", external.resolve("junit4"), "src/test/java"),
				folders("cobertura", external.resolve("cobertura"),
						"cobertura/src/test/java",
						"cobertura-flush-war/src/test/java",
						"conversion/conversion-api/src/test/java",
						"metrics/metrics-api/src/test/java",
						"metrics/metrics-model/src/test/java"),
				folders("couchdb-lucene", external.resolve("couchdb-lucene"), "src/test/java"),
				folders("picon", external.resolve("picon"), "test"),
				folders("selenium", external.resolve("selenium"), "java/test"),
				saas("saas+teste"),
				saas("saas+teste+moodle"),
				saas("saas+teste+selenium"),
				saas("saas+teste+service"));
	}

	private static Subject folders(String name, Path project, String... relative) {
		List<Path> folders = Stream.of(relative).map(project::resolve).collect(Collectors.toList());
		return new Subject(name, folders);
	}

	private static Subject saas(String module) {
		return new Subject(module, List.of(Path.of("external-projects/saas-unificado").resolve(module)));
	}

	static final class Subject {

		private final String name;
		private final List<Path> folders;

		private Subject(String name, List<Path> folders) {
			this.name = name;
			this.folders = List.copyOf(folders);
		}

		String name() {
			return name;
		}

		List<Path> folders() {
			return folders;
		}

		List<Path> existingFolders() {
			return folders.stream().filter(Files::isDirectory).collect(Collectors.toList());
		}
	}
}
