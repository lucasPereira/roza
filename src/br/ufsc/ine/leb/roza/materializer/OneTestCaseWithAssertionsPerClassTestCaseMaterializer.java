package br.ufsc.ine.leb.roza.materializer;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.stmt.BlockStmt;

import br.ufsc.ine.leb.roza.TestCase;

public class OneTestCaseWithAssertionsPerClassTestCaseMaterializer extends OneTestCasePerClassTestCaseMaterializer implements TestCaseMaterializer {

	public OneTestCaseWithAssertionsPerClassTestCaseMaterializer(String baseFolder) {
		super(baseFolder);
	}

	public void addAssertions(TestCase testCase, BlockStmt javaMethodBody) {
		testCase.getAsserts().forEach((assertion) -> {
			javaMethodBody.addStatement(JavaParser.parseStatement(assertion.getText()));
		});
	}

}
