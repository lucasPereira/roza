package br.ufsc.ine.leb.roza.selector;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.TextFile;

public class JavaExtensionTextFileSelectorTest {

	private JavaExtensionTextFileSelector selector;

	@BeforeEach
	void setup() {
		selector = new JavaExtensionTextFileSelector();
	}

	@Test
	void withoutTextFiles() throws Exception {
		List<TextFile> textFiles = selector.select(Arrays.asList());
		assertEquals(0, textFiles.size());
	}

	@Test
	void oneJavaFile() throws Exception {
		TextFile exampleDotJava = new TextFile("Example.java", "public class Example { public void example() { System.out.println(0); } }");
		List<TextFile> textFiles = selector.select(Arrays.asList(exampleDotJava));
		assertEquals(1, textFiles.size());
		assertEquals(exampleDotJava, textFiles.get(0));
	}

	@Test
	void twoJavaFiles() throws Exception {
		TextFile example1DotJava = new TextFile("Example1.java", "public class Example { public void example() { System.out.println(0); } }");
		TextFile example2DotJava = new TextFile("Example2.java", "public class Example { public void example() { System.out.println(0); } }");
		List<TextFile> textFiles = selector.select(Arrays.asList(example1DotJava, example2DotJava));
		assertEquals(2, textFiles.size());
		assertEquals(example1DotJava, textFiles.get(0));
		assertEquals(example2DotJava, textFiles.get(1));
	}

	@Test
	void oneTxtFileAndOneJavaFile() throws Exception {
		TextFile exampleDotTxt = new TextFile("Example.txt", "example");
		TextFile exampleDotJava = new TextFile("Example.java", "public class Example { public void example() { System.out.println(0); } }");
		List<TextFile> textFiles = selector.select(Arrays.asList(exampleDotTxt, exampleDotJava));
		assertEquals(1, textFiles.size());
		assertEquals(exampleDotJava, textFiles.get(0));
	}

}
