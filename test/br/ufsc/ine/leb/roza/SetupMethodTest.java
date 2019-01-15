package br.ufsc.ine.leb.roza;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class SetupMethodTest {

	@Test
	void create() throws Exception {
		SetupMethod setupMethod = new SetupMethod("setup");
		assertEquals("setup", setupMethod.getName());
	}

}
