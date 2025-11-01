package br.ufsc.ine.leb.roza.ui.model;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import br.ufsc.ine.leb.roza.SetupMethod;
import br.ufsc.ine.leb.roza.TestClass;

public class TestClassSetupsModel extends TestClassAbstractModel<SetupMethod> {

	private static final long serialVersionUID = 1L;

	public TestClassSetupsModel(TestClass testClass) {
		super(testClass);
	}

	@Override
	protected List<Supplier<String>> getColumnTitleMappers() {
		return List.of(() -> "Name");
	}

	@Override
	protected List<Function<SetupMethod, String>> getValueMappers() {
		return List.of(SetupMethod::getName);
	}

	@Override
	protected List<SetupMethod> getElements(TestClass testClass) {
		return testClass.getSetupMethods();
	}

}
