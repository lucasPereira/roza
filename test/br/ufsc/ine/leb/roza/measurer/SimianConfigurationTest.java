package br.ufsc.ine.leb.roza.measurer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.exceptions.InvalidConfigurationException;

public class SimianConfigurationTest {

	private SimianConfigurations configurations;

	@BeforeEach
	void setup() {
		configurations = new SimianConfigurations();
	}

	@Test
	void configurations() throws Exception {
		assertEquals(20, configurations.getAll().size());

		assertEquals("threshold", configurations.getAll().get(0).getName());
		assertEquals("Matches will contain at least the specified number of lines", configurations.getAll().get(0).getDescription());
		assertEquals("6", configurations.getAll().get(0).getValue());

		assertEquals("ignoreCurlyBraces", configurations.getAll().get(1).getName());
		assertEquals("false", configurations.getAll().get(1).getValue());
		assertEquals("Curly braces are ignored", configurations.getAll().get(1).getDescription());

		assertEquals("ignoreIdentifiers", configurations.getAll().get(2).getName());
		assertEquals("false", configurations.getAll().get(2).getValue());
		assertEquals("Completely ignores all identfiers", configurations.getAll().get(2).getDescription());

		assertEquals("ignoreStrings", configurations.getAll().get(3).getName());
		assertEquals("false", configurations.getAll().get(3).getValue());
		assertEquals("\"abc\" and \"def\" would both match", configurations.getAll().get(3).getDescription());

		assertEquals("ignoreNumbers", configurations.getAll().get(4).getName());
		assertEquals("false", configurations.getAll().get(4).getValue());
		assertEquals("1 and 576 would both match", configurations.getAll().get(4).getDescription());

		assertEquals("ignoreCharacters", configurations.getAll().get(5).getName());
		assertEquals("false", configurations.getAll().get(5).getValue());
		assertEquals("'A' and 'Z' would both match", configurations.getAll().get(5).getDescription());

		assertEquals("ignoreLiterals", configurations.getAll().get(6).getName());
		assertEquals("false", configurations.getAll().get(6).getValue());
		assertEquals("'A', \"one\" and 27.8 would all match", configurations.getAll().get(6).getDescription());

		assertEquals("ignoreVariableNames", configurations.getAll().get(7).getName());
		assertEquals("false", configurations.getAll().get(7).getValue());
		assertEquals("int foo = 1; and int bar = 1 would both match", configurations.getAll().get(7).getDescription());

		assertEquals("formatter", configurations.getAll().get(8).getName());
		assertEquals("xml", configurations.getAll().get(8).getValue());
		assertEquals("Specifies the format in which processing results will be produced", configurations.getAll().get(8).getDescription());

		assertEquals("language", configurations.getAll().get(9).getName());
		assertEquals("java", configurations.getAll().get(9).getValue());
		assertEquals("Assumes all files are in the specified language", configurations.getAll().get(9).getDescription());

		assertEquals("failOnDuplication", configurations.getAll().get(10).getName());
		assertEquals("false", configurations.getAll().get(10).getValue());
		assertEquals("Causes the checker to fail the current process if duplication is detected", configurations.getAll().get(10).getDescription());

		assertEquals("reportDuplicateText", configurations.getAll().get(11).getName());
		assertEquals("true", configurations.getAll().get(11).getValue());
		assertEquals("Prints the duplicate text in reports", configurations.getAll().get(11).getDescription());

		assertEquals("ignoreBlocks", configurations.getAll().get(12).getName());
		assertEquals("false", configurations.getAll().get(12).getValue());
		assertEquals("Ignores all lines between specified start/end markers", configurations.getAll().get(12).getDescription());

		assertEquals("ignoreIdentifierCase", configurations.getAll().get(13).getName());
		assertEquals("false", configurations.getAll().get(13).getValue());
		assertEquals("MyVariableName and myvariablename would both match", configurations.getAll().get(13).getDescription());

		assertEquals("ignoreStringCase", configurations.getAll().get(14).getName());
		assertEquals("false", configurations.getAll().get(14).getValue());
		assertEquals("\"Hello, World\" and \"HELLO, WORLD\" would both match", configurations.getAll().get(14).getDescription());

		assertEquals("ignoreCharacterCase", configurations.getAll().get(15).getName());
		assertEquals("false", configurations.getAll().get(15).getValue());
		assertEquals("'A' and 'a'would both match", configurations.getAll().get(15).getDescription());

		assertEquals("ignoreSubtypeNames", configurations.getAll().get(16).getName());
		assertEquals("false", configurations.getAll().get(16).getValue());
		assertEquals("BufferedReader, StringReader and Reader would all match", configurations.getAll().get(16).getDescription());

		assertEquals("ignoreModifiers", configurations.getAll().get(17).getName());
		assertEquals("true", configurations.getAll().get(17).getValue());
		assertEquals("public, protected, static, etc", configurations.getAll().get(17).getDescription());

		assertEquals("balanceParentheses", configurations.getAll().get(18).getName());
		assertEquals("Ensures that expressions inside parenthesis that are split across multiple physical lines are considered as one", configurations.getAll().get(18).getDescription());
		assertEquals("true", configurations.getAll().get(18).getValue());

		assertEquals("balanceSquareBrackets", configurations.getAll().get(19).getName());
		assertEquals("true", configurations.getAll().get(19).getValue());
		assertEquals("Ensures that expressions inside square brackets that are split across multiple physical lines are considered as one", configurations.getAll().get(19).getDescription());
	}

	@Test
	void arguments() throws Exception {
		assertEquals(20, configurations.getAllAsArguments().size());
		assertEquals("-threshold=6", configurations.getAllAsArguments().get(0));
		assertEquals("-ignoreCurlyBraces=false", configurations.getAllAsArguments().get(1));
		assertEquals("-ignoreIdentifiers=false", configurations.getAllAsArguments().get(2));
		assertEquals("-ignoreStrings=false", configurations.getAllAsArguments().get(3));
		assertEquals("-ignoreNumbers=false", configurations.getAllAsArguments().get(4));
		assertEquals("-ignoreCharacters=false", configurations.getAllAsArguments().get(5));
		assertEquals("-ignoreLiterals=false", configurations.getAllAsArguments().get(6));
		assertEquals("-ignoreVariableNames=false", configurations.getAllAsArguments().get(7));
		assertEquals("-formatter=xml", configurations.getAllAsArguments().get(8));
		assertEquals("-language=java", configurations.getAllAsArguments().get(9));
		assertEquals("-failOnDuplication=false", configurations.getAllAsArguments().get(10));
		assertEquals("-reportDuplicateText=true", configurations.getAllAsArguments().get(11));
		assertEquals("-ignoreBlocks=false", configurations.getAllAsArguments().get(12));
		assertEquals("-ignoreIdentifierCase=false", configurations.getAllAsArguments().get(13));
		assertEquals("-ignoreStringCase=false", configurations.getAllAsArguments().get(14));
		assertEquals("-ignoreCharacterCase=false", configurations.getAllAsArguments().get(15));
		assertEquals("-ignoreSubtypeNames=false", configurations.getAllAsArguments().get(16));
		assertEquals("-ignoreModifiers=true", configurations.getAllAsArguments().get(17));
		assertEquals("-balanceParentheses=true", configurations.getAllAsArguments().get(18));
		assertEquals("-balanceSquareBrackets=true", configurations.getAllAsArguments().get(19));
	}

	@Test
	void changeValues() throws Exception {
		configurations.threshold(2);
		assertEquals(20, configurations.getAllAsArguments().size());
		assertEquals("-threshold=2", configurations.getAllAsArguments().get(0));
		assertEquals("-ignoreCurlyBraces=false", configurations.getAllAsArguments().get(1));
		assertEquals("-ignoreIdentifiers=false", configurations.getAllAsArguments().get(2));
		assertEquals("-ignoreStrings=false", configurations.getAllAsArguments().get(3));
		assertEquals("-ignoreNumbers=false", configurations.getAllAsArguments().get(4));
		assertEquals("-ignoreCharacters=false", configurations.getAllAsArguments().get(5));
		assertEquals("-ignoreLiterals=false", configurations.getAllAsArguments().get(6));
		assertEquals("-ignoreVariableNames=false", configurations.getAllAsArguments().get(7));
		assertEquals("-formatter=xml", configurations.getAllAsArguments().get(8));
		assertEquals("-language=java", configurations.getAllAsArguments().get(9));
		assertEquals("-failOnDuplication=false", configurations.getAllAsArguments().get(10));
		assertEquals("-reportDuplicateText=true", configurations.getAllAsArguments().get(11));
		assertEquals("-ignoreBlocks=false", configurations.getAllAsArguments().get(12));
		assertEquals("-ignoreIdentifierCase=false", configurations.getAllAsArguments().get(13));
		assertEquals("-ignoreStringCase=false", configurations.getAllAsArguments().get(14));
		assertEquals("-ignoreCharacterCase=false", configurations.getAllAsArguments().get(15));
		assertEquals("-ignoreSubtypeNames=false", configurations.getAllAsArguments().get(16));
		assertEquals("-ignoreModifiers=true", configurations.getAllAsArguments().get(17));
		assertEquals("-balanceParentheses=true", configurations.getAllAsArguments().get(18));
		assertEquals("-balanceSquareBrackets=true", configurations.getAllAsArguments().get(19));
	}

	@Test
	void threasholdShoudBeLargerThanTow() throws Exception {
		assertThrows(InvalidConfigurationException.class, () -> {
			new SimianConfigurations().threshold(-1);
		});
		assertThrows(InvalidConfigurationException.class, () -> {
			new SimianConfigurations().threshold(0);
		});
		assertThrows(InvalidConfigurationException.class, () -> {
			new SimianConfigurations().threshold(1);
		});
		assertThrows(InvalidConfigurationException.class, () -> {
			new SimianConfigurations().threshold(null);
		});
	}

}
