package br.ufsc.ine.leb.roza.selection;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.TextFile;

class JavaExtensionTextFileSelectorTest {

	private JavaExtensionTextFileSelector selector;

	@BeforeEach
	void setup() {
		selector = new JavaExtensionTextFileSelector();
	}

	@Test
	void withoutTextFiles() {
		List<TextFile> textFiles = selector.select(List.of());
		assertEquals(0, textFiles.size());
	}

	@Test
	void oneJavaFile() {
		TextFile exampleDotJava = new TextFile("Example.java", "public class Example { public void example() { System.out.println(0); } }");
		List<TextFile> textFiles = selector.select(List.of(exampleDotJava));
		assertEquals(1, textFiles.size());
		assertEquals(exampleDotJava, textFiles.get(0));
	}

	@Test
	void twoJavaFiles() {
		TextFile example1DotJava = new TextFile("Example1.java", "public class Example { public void example() { System.out.println(0); } }");
		TextFile example2DotJava = new TextFile("Example2.java", "public class Example { public void example() { System.out.println(0); } }");
		List<TextFile> textFiles = selector.select(List.of(example1DotJava, example2DotJava));
		assertEquals(2, textFiles.size());
		assertEquals(example1DotJava, textFiles.get(0));
		assertEquals(example2DotJava, textFiles.get(1));
	}

	@Test
	void oneTxtFileAndOneJavaFile() {
		TextFile exampleDotTxt = new TextFile("Example.txt", "example");
		TextFile exampleDotJava = new TextFile("Example.java", "public class Example { public void example() { System.out.println(0); } }");
		List<TextFile> textFiles = selector.select(List.of(exampleDotTxt, exampleDotJava));
		assertEquals(1, textFiles.size());
		assertEquals(exampleDotJava, textFiles.get(0));
	}

}
