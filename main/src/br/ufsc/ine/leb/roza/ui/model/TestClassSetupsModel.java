package br.ufsc.ine.leb.roza.ui.model;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import br.ufsc.ine.leb.roza.parsing.SetupMethod;
import br.ufsc.ine.leb.roza.parsing.TestClass;

public class TestClassSetupsModel extends TestClassAbstractModel<SetupMethod> {

	private static final long serialVersionUID = 1L;

	public TestClassSetupsModel(TestClass testClass) {
		super(testClass);
	}

	@Override
	protected List<Supplier<String>> getColumnTitleMappers() {
		return Arrays.asList(() -> "Name");
	}

	@Override
	protected List<Function<SetupMethod, String>> getValueMappers() {
		return Arrays.asList(method -> method.getName());
	}

	@Override
	protected List<SetupMethod> getElements(TestClass testClass) {
		return testClass.getSetupMethods();
	}

}
