package br.ufsc.ine.leb.roza.measurer;

import br.ufsc.ine.leb.roza.support.configuration.AbstractConfigurations;
import br.ufsc.ine.leb.roza.support.configuration.Configurations;

public class SimianConfigurations extends AbstractConfigurations implements Configurations {

	public SimianConfigurations() {
		create("threshold", 6, "Matches will contain at least the specified number of lines");
//		create("ignoreCurlyBraces", false, "Curly braces are ignored");
//		create("ignoreIdentifiers", false, "Completely ignores all identfiers");
//		create("ignoreStrings", false, "\"abc\" and \"def\" would both match");
//		create("ignoreNumbers", false, "1 and 576 would both match");
//		create("ignoreCharacters", false, "'A' and 'Z' would both match");
//		create("ignoreLiterals", false, "'A', \"one\" and 27.8 would all match");
//		create("ignoreVariableNames", false, "int foo = 1; and int bar = 1 would both match");
		create("formatter", "xml", "Specifies the format in which processing results will be produced");
		create("language", "java", "Assumes all files are in the specified language");
//		create("failOnDuplication", false, "Causes the checker to fail the current process if duplication is detected");
//		create("reportDuplicateText", true, "Prints the duplicate text in reports");
//		create("ignoreBlocks", false, "Ignores all lines between specified start/end markers");
//		create("ignoreIdentifierCase", false, "MyVariableName and myvariablename would both match");
//		create("ignoreStringCase", false, "\"Hello, World\" and \"HELLO, WORLD\" would both match");
//		create("ignoreCharacterCase", false, "'A' and 'a'would both match");
//		create("ignoreSubtypeNames", false, "BufferedReader, StringReader and Reader would all match");
//		create("ignoreModifiers", true, "public, protected, static, etc");
//		create("balanceParentheses", true, "Ensures that expressions inside parenthesis that are split across multiple physical lines are considered as one");
//		create("balanceSquareBrackets", true, "Ensures that expressions inside square brackets that are split across multiple physical lines are considered as one");
	}

	@Override
	protected String generateArgument(String name, String value) {
		return String.format("-%s=%s", name, value);
	}

	public SimianConfigurations threshold(Integer threshold) {
		throwInvalidConfigurationExceptionIfFalse(threshold != null);
		throwInvalidConfigurationExceptionIfFalse(threshold >= 2);
		set("threshold", threshold);
		return this;
	}

}
