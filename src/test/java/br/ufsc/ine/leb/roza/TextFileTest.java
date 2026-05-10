package br.ufsc.ine.leb.roza;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class TextFileTest {

	@Test
	void create() {
		TextFile exampleDotJava = new TextFile("Example.java", "public class Example {}");
		assertEquals("Example.java", exampleDotJava.getName());
		assertEquals("public class Example {}", exampleDotJava.getContent());
	}

}
