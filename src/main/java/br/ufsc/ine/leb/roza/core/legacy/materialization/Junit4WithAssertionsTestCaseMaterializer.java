package br.ufsc.ine.leb.roza.core.legacy.materialization;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.stmt.BlockStmt;

import br.ufsc.ine.leb.roza.core.legacy.TestCase;

public class Junit4WithAssertionsTestCaseMaterializer extends Junit4TestCaseMaterializer implements TestCaseMaterializer {

	public Junit4WithAssertionsTestCaseMaterializer(String baseFolder) {
		super(baseFolder);
	}

	public void addAssertions(TestCase testCase, BlockStmt javaMethodBody) {
		testCase.getAsserts().forEach((assertion) -> javaMethodBody.addStatement(StaticJavaParser.parseStatement(assertion.getText())));
	}

}
