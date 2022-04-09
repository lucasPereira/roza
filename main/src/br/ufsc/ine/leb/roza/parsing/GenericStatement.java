package br.ufsc.ine.leb.roza.parsing;

import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.printer.lexicalpreservation.LexicalPreservingPrinter;

public class GenericStatement implements RozaStatement {

	private Statement statement;

	public GenericStatement(Statement statement) {
		this.statement = statement;
	}

	@Override
	public String getCode() {
		return LexicalPreservingPrinter.print(statement);
	}

	@Override
	public String toString() {
		return getCode();
	}

	@Override
	public void visit(StatementVisitor visitor) {
		visitor.visit(this);
	}

}
