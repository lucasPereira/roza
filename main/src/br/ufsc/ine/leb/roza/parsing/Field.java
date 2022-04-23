package br.ufsc.ine.leb.roza.parsing;

import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.printer.lexicalpreservation.LexicalPreservingPrinter;

public class Field {

	private FieldDeclaration declaration;

	public Field(FieldDeclaration declaration) {
		this.declaration = declaration;
	}

	public String toCode() {
		return LexicalPreservingPrinter.print(declaration);
	}

	@Override
	public String toString() {
		return toCode();
	}

}
