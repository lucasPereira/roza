package br.ufsc.ine.leb.roza;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

class SetupMethodTest {

	@Test
	void create() throws Exception {
		Statement statement = new Statement("System.out.println(0);");
		SetupMethod setupMethod = new SetupMethod("setup", Arrays.asList(statement));
		assertEquals("setup", setupMethod.getName());
		assertEquals(1, setupMethod.getStatements().size());
		assertEquals("System.out.println(0);", setupMethod.getStatements().get(0).getText());
	}

}
