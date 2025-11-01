package br.ufsc.ine.leb.roza;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class FieldTest {

	@Test
	void withoutInitialization() {
		Field field = new Field("Sut", "sut");
		assertEquals("Sut", field.getType());
		assertEquals("sut", field.getName());
		assertNull(field.getInitialization());
	}

	@Test
	void withInitialization() {
		Statement initialization = new Statement("new Sut();");
		Field field = new Field("Sut", "sut", initialization);
		assertEquals("Sut", field.getType());
		assertEquals("sut", field.getName());
		assertEquals(initialization, field.getInitialization());
	}

	@Test
	void equal() {
		assertEquals(new Field("Sut", "sut"), new Field("Sut", "sut"), "Withtout initialization");
		assertEquals(new Field("Sut", "sut", new Statement("new Sut();")), new Field("Sut", "sut", new Statement("new Sut();")), "With initialization");
	}

	@Test
	void notEqual() {
		assertNotEquals(new Field("Sut", "sut"), new Field("Sut", "tus"), "Without initialization, same type, different names");
		assertNotEquals(new Field("Tus", "sut"), new Field("Sut", "sut"), "Without initialization, same name, different types");
		assertNotEquals(new Field("Integer", "value1", new Statement("10;")), new Field("Integer", "value2", new Statement("10;")), "With same initialization, same type, different names");
		assertNotEquals(new Field("Number", "value", new Statement("10;")), new Field("Integer", "value", new Statement("10;")), "With same initialization, same name, different types");
		assertNotEquals(new Field("Number", "value1", new Statement("10;")), new Field("Integer", "value2", new Statement("10;")), "With same initialization, different types, different names");
		assertNotEquals(new Field("Integer", "value1", new Statement("10;")), new Field("Integer", "value2", new Statement("20;")), "With different initializations, same type, different names");
		assertNotEquals(new Field("Number", "value", new Statement("10;")), new Field("Integer", "value", new Statement("20;")), "With different initializations, same name, different types");
		assertNotEquals(new Field("Number", "value1", new Statement("10;")), new Field("Integer", "value2", new Statement("20;")), "With different initializations, different types, different names");
		assertNotEquals(new Field("Integer", "value", new Statement("10;")), new Field("Integer", "value"), "With and without initialization, same type, same name");
		assertNotEquals(new Field("Integer", "value"), new Field("Integer", "value", new Statement("10;")), "Without and with initialization, same type, same name");
		assertNotEquals(new Field("Integer", "value", new Statement("null;")), new Field("Integer", "value"), "With null and without initialization, same type, same name");
		assertNotEquals(new Field("Integer", "value"), new Field("Integer", "value", new Statement("null;")), "Without and with null initialization, same type, same name");
	}

	@Test
	public void string() {
		assertEquals("Sut sut;", new Field("Sut", "sut").toString());
		assertEquals("Sut sut = new Sut();", new Field("Sut", "sut", new Statement("new Sut();")).toString());
	}

}
