package br.ufsc.ine.leb.roza.parsing;

import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.printer.DefaultPrettyPrinter;
import com.github.javaparser.printer.configuration.DefaultPrinterConfiguration;

public class GenericStatement implements RozaStatement {

	private Statement statement;

	public GenericStatement(Statement statement) {
		this.statement = statement;
	}

	@Override
	public String toCode() {
		DefaultPrinterConfiguration configuration = new DefaultPrinterConfiguration();
		DefaultPrettyPrinter printer = new DefaultPrettyPrinter(configuration);
		return printer.print(statement);
	}

	@Override
	public String toString() {
		return toCode();
	}

	@Override
	public void visit(StatementVisitor visitor) {
		visitor.visit(this);
	}

}
