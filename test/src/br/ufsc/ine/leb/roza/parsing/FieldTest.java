package br.ufsc.ine.leb.roza.parsing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class FieldTest {

	@Test
	void create() throws Exception {
		Field field = new Field("Sut", "sut");
		assertEquals("Sut", field.getType());
		assertEquals("sut", field.getName());
	}

	@Test
	void equals() throws Exception {
		assertEquals(new Field("Sut", "sut"), new Field("Sut", "sut"));
	}

	@Test
	void notEquals() throws Exception {
		assertNotEquals(new Field("Sut", "sut"), new Field("Sut", "tus"), "Same type, different names");
		assertNotEquals(new Field("Tus", "sut"), new Field("Sut", "sut"), "Same name, different types");
	}

	@Test
	public void string() {
		assertEquals("private Sut sut;", new Field("Sut", "sut").toString());
	}

}
