package br.ufsc.ine.leb.roza;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.util.List;

class SetupMethodTest {

	@Test
	void create() {
		Statement statement = new Statement("System.out.println(0);");
		SetupMethod setupMethod = new SetupMethod("setup", List.of(statement));
		assertEquals("setup", setupMethod.getName());
		assertEquals(1, setupMethod.getStatements().size());
		assertEquals("System.out.println(0);", setupMethod.getStatements().get(0).getText());
	}

}
