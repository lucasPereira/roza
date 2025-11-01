package br.ufsc.ine.leb.roza.ui.model;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import br.ufsc.ine.leb.roza.Field;
import br.ufsc.ine.leb.roza.TestClass;

public class TestClassFieldsModel extends TestClassAbstractModel<Field> {

	private static final long serialVersionUID = 1L;

	public TestClassFieldsModel(TestClass testClass) {
		super(testClass);
	}

	@Override
	protected List<Field> getElements(TestClass testClass) {
		return testClass.getFields();
	}

	@Override
	protected List<Supplier<String>> getColumnTitleMappers() {
		return List.of(() -> "Type", () -> "Name");
	}

	@Override
	protected List<Function<Field, String>> getValueMappers() {
		return List.of(Field::getType, Field::getName);
	}

}
