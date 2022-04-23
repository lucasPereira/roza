package br.ufsc.ine.leb.roza.parsing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.fixture.ParsingFixtures;

class FieldTest {

	@Test
	void notInitialized() throws Exception {
		Field field = ParsingFixtures.fieldInteger("fixture");
		assertEquals("private Integer fixture;", field.toCode());
	}

	@Test
	void initialized() throws Exception {
		Field field = ParsingFixtures.fieldInteger("fixture", 0);
		assertNotEquals(field, ParsingFixtures.fieldInteger("fixture"));
		assertEquals("private Integer fixture = 0;", field.toCode());
	}

}
