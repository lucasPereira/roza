package br.ufsc.ine.leb.roza.materializer;

import com.github.javaparser.ast.stmt.BlockStmt;

import br.ufsc.ine.leb.roza.TestCase;

public class OneTestCaseWithoutAssertionsPerClassTestCaseMaterializer extends OneTestCasePerClassTestCaseMaterializer implements TestCaseMaterializer {

	public OneTestCaseWithoutAssertionsPerClassTestCaseMaterializer(String baseFolder) {
		super(baseFolder);
	}

	@Override
	protected void addAssertions(TestCase testCase, BlockStmt javaMethodBody) {}

}
