package br.ufsc.ine.leb.roza;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class TextFileTest {

	@Test
	void create() throws Exception {
		TextFile exampleDotJava = new TextFile("Example.java", "public class Example {}");
		assertEquals("Example.java", exampleDotJava.getName());
		assertEquals("public class Example {}", exampleDotJava.getContent());
	}

}
