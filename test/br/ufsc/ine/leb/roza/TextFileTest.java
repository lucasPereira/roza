package br.ufsc.ine.leb.roza;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TextFileTest {

	private TextFile exampleDotJava;

	@BeforeEach
	void setup() {
		exampleDotJava = new TextFile("Example.java", "public class Example {}");
	}

	@Test
	void create() throws Exception {
		assertEquals("Example.java", exampleDotJava.getName());
		assertEquals("public class Example {}", exampleDotJava.getContent());
	}

}
