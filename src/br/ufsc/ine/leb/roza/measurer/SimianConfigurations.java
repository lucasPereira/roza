package br.ufsc.ine.leb.roza.measurer;

import java.util.LinkedList;
import java.util.List;

import br.ufsc.ine.leb.roza.support.configuration.AbstractConfigurations;
import br.ufsc.ine.leb.roza.support.configuration.Configuration;
import br.ufsc.ine.leb.roza.support.configuration.Configurations;

public class SimianConfigurations extends AbstractConfigurations implements Configurations {

	private SimianIntegerConfiguration threshold;

	public SimianConfigurations() {
		threshold = new SimianIntegerConfiguration("threshold", 6, "Matches will contain at least the specified number of lines");
	}

	@Override
	public List<Configuration> getAll() {
		List<Configuration> configurations = new LinkedList<>();
		configurations.add(threshold);
		configurations.add(new SimianBooleanConfiguration("ignoreCurlyBraces", true, "Curly braces are ignored"));
		configurations.add(new SimianBooleanConfiguration("ignoreIdentifiers", false, "Completely ignores all identfiers"));
		configurations.add(new SimianBooleanConfiguration("ignoreStrings", false, "\"abc\" and \"def\" would both match"));
		configurations.add(new SimianBooleanConfiguration("ignoreNumbers", false, "1 and 576 would both match"));
		configurations.add(new SimianBooleanConfiguration("ignoreCharacters", false, "'A' and 'Z' would both match"));
		configurations.add(new SimianBooleanConfiguration("ignoreLiterals", false, "'A', \"one\" and 27.8 would all match"));
		configurations.add(new SimianBooleanConfiguration("ignoreVariableNames", false, "int foo = 1; and int bar = 1 would both match"));
		configurations.add(new SimianStringConfiguration("formatter", "xml", "Specifies the format in which processing results will be produced"));
		configurations.add(new SimianStringConfiguration("language", "java", "Assumes all files are in the specified language"));
		configurations.add(new SimianBooleanConfiguration("failOnDuplication", false, "Causes the checker to fail the current process if duplication is detected"));
		configurations.add(new SimianBooleanConfiguration("reportDuplicateText", true, "Prints the duplicate text in reports"));
		configurations.add(new SimianStringConfiguration("ignoreBlocks", "none", "Ignores all lines between specified start/end markers"));
		configurations.add(new SimianBooleanConfiguration("ignoreIdentifierCase", false, "MyVariableName and myvariablename would both match"));
		configurations.add(new SimianBooleanConfiguration("ignoreStringCase", false, "\"Hello, World\" and \"HELLO, WORLD\" would both match"));
		configurations.add(new SimianBooleanConfiguration("ignoreCharacterCase", false, "'A' and 'a'would both match"));
		configurations.add(new SimianBooleanConfiguration("ignoreSubtypeNames", false, "BufferedReader, StringReader and Reader would all match"));
		configurations.add(new SimianBooleanConfiguration("ignoreModifiers", true, "public, protected, static, etc"));
		configurations.add(new SimianBooleanConfiguration("balanceParentheses", true, "Ensures that expressions inside parenthesis that are split across multiple physical lines are considered as one"));
		configurations.add(new SimianBooleanConfiguration("balanceSquareBrackets", true, "Ensures that expressions inside square brackets that are split across multiple physical lines are considered as one"));
		return configurations;
	}

	public SimianConfigurations threshold(Integer value) {
		ensureThat(value != null);
		ensureThat(value >= 2);
		threshold.setValue(value);
		return this;
	}

}
