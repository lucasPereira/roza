package br.ufsc.ine.leb.roza;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class FieldTest {

	@Test
	void create() throws Exception {
		Field field = new Field("Sut", "sut");
		assertEquals("Sut", field.getType());
		assertEquals("sut", field.getName());
	}

}
