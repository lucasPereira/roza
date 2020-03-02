package br.ufsc.ine.leb.roza.loading;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.TextFile;
import br.ufsc.ine.leb.roza.loading.RecursiveTextFileLoader;
import br.ufsc.ine.leb.roza.loading.TextFileLoader;

public class RecursiveTextFileLoaderTest {

	private TextFileLoader loader;

	@BeforeEach
	void setup() {
		loader = new RecursiveTextFileLoader("test-resources/loader");
	}

	@Test
	void names() throws Exception {
		List<TextFile> files = loader.load();
		assertEquals(6, files.size());
		assertEquals("Example.bin", files.get(0).getName());
		assertEquals("Example.java", files.get(1).getName());
		assertEquals("Example.txt", files.get(2).getName());
		assertEquals("ExampleChild.bin", files.get(3).getName());
		assertEquals("ExampleChild.java", files.get(4).getName());
		assertEquals("ExampleChild.txt", files.get(5).getName());
	}

	@Test
	void content() throws Exception {
		List<TextFile> files = loader.load();
		assertEquals(6, files.size());
		assertEquals("Example.bin\n", files.get(0).getContent());
		assertEquals("public class Example {}\n", files.get(1).getContent());
		assertEquals("Example\n", files.get(2).getContent());
		assertEquals("ExampleChild.bin\n", files.get(3).getContent());
		assertEquals("package childs;\n\npublic class ExampleChild {}\n", files.get(4).getContent());
		assertEquals("ExampleChild\n", files.get(5).getContent());
	}

}
