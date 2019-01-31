package br.ufsc.ine.leb.roza;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class StatementTest {

	@Test
	void create() throws Exception {
		Statement statement = new Statement("assertEquals(0, 0);");
		assertEquals("assertEquals(0, 0);", statement.getText());
		assertEquals("assertEquals(0, 0);", statement.toString());
	}

}
