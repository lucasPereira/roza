package br.ufsc.ine.leb.roza.ui.model;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.swing.table.AbstractTableModel;

import br.ufsc.ine.leb.roza.parsing.TestClass;

public abstract class TestClassAbstractModel<T> extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private TestClass testClass;

	protected abstract List<Supplier<String>> getColumnTitleMappers();

	protected abstract List<Function<T, String>> getValueMappers();

	protected abstract List<T> getElements(TestClass testClass);

	public TestClassAbstractModel(TestClass testClass) {
		this.testClass = testClass;
	}

	@Override
	public final int getRowCount() {
		return getElements(testClass).size();
	}

	@Override
	public final int getColumnCount() {
		return getColumnTitleMappers().size();
	}

	@Override
	public final String getColumnName(int column) {
		String text = getColumnTitleMappers().get(column).get();
		return text;
	}

	@Override
	public final Object getValueAt(int rowIndex, int columnIndex) {
		T element = getElements(testClass).get(rowIndex);
		String text = getValueMappers().get(columnIndex).apply(element);
		return text;
	}

}
