package br.ufsc.ine.leb.roza.core.modern.parsing;

import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.printer.DefaultPrettyPrinter;
import com.github.javaparser.printer.configuration.DefaultConfigurationOption;
import com.github.javaparser.printer.configuration.DefaultPrinterConfiguration;
import com.github.javaparser.printer.configuration.DefaultPrinterConfiguration.ConfigOption;
import com.github.javaparser.printer.configuration.Indentation;
import com.github.javaparser.printer.configuration.Indentation.IndentType;

public final class ConfiguredJavaParser {

	static {
		ParserConfiguration configuration = new ParserConfiguration();
		configuration.setLanguageLevel(ParserConfiguration.LanguageLevel.JAVA_17);
		StaticJavaParser.setConfiguration(configuration);
	}

	private ConfiguredJavaParser() {
	}

	public static CompilationUnit parseCompilationUnit(String content) {
		return StaticJavaParser.parse(content);
	}

	public static Statement parseStatement(String content) {
		return StaticJavaParser.parseStatement(content);
	}

	public static String printCompact(Node node) {
		DefaultPrinterConfiguration configuration = new DefaultPrinterConfiguration();
		configuration.addOption(new DefaultConfigurationOption(ConfigOption.END_OF_LINE_CHARACTER, " "));
		configuration.addOption(new DefaultConfigurationOption(ConfigOption.INDENTATION, new Indentation(IndentType.SPACES, 0)));
		configuration.addOption(new DefaultConfigurationOption(ConfigOption.PRINT_COMMENTS, false));
		return new DefaultPrettyPrinter(configuration).print(node).trim();
	}
}
