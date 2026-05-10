package br.ufsc.ine.leb.roza.ui.model;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import br.ufsc.ine.leb.roza.TestClass;
import br.ufsc.ine.leb.roza.TestMethod;

public class TestClassTestsModel extends TestClassAbstractModel<TestMethod> {

	private static final long serialVersionUID = 1L;

	public TestClassTestsModel(TestClass testClass) {
		super(testClass);
	}

	@Override
	protected List<Supplier<String>> getColumnTitleMappers() {
		return List.of(() -> "Name");
	}

	@Override
	protected List<Function<TestMethod, String>> getValueMappers() {
		return List.of(TestMethod::getName);
	}

	@Override
	protected List<TestMethod> getElements(TestClass testClass) {
		return testClass.getTestMethods();
	}

}
