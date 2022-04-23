package br.ufsc.ine.leb.roza.parsing;

import java.util.List;

import com.github.javaparser.ast.body.MethodDeclaration;

public class SetupMethod {

	private MethodDeclaration declaration;
	private List<RozaStatement> statements;


	public SetupMethod(MethodDeclaration declaration, List<RozaStatement> statements) {
		this.declaration = declaration;
		this.statements = statements;
	}

	public String getName() {
		return declaration.getName().asString();
	}

	public List<RozaStatement> getStatements() {
		return statements;
	}

}
