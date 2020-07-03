package br.ufsc.ine.leb.roza;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class StatementTest {

	@Test
	void create() throws Exception {
		Statement statement = new Statement("assertEquals(0, 0);");
		assertEquals("assertEquals(0, 0);", statement.getText());
		assertEquals("assertEquals(0, 0);", statement.toString());
	}

	@Test
	void equals() throws Exception {
		assertEquals(new Statement("sut(0);"), new Statement("sut(0);"));
		assertNotEquals(new Statement("sut(0);"), new Statement("sut(1);"));
	}

}
