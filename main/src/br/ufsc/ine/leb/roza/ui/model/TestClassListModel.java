package br.ufsc.ine.leb.roza.ui.model;

import java.util.List;

import javax.swing.AbstractListModel;

import br.ufsc.ine.leb.roza.TestClass;

public class TestClassListModel extends AbstractListModel<TestClass> {

	private static final long serialVersionUID = 1L;

	private List<TestClass> classes;

	public TestClassListModel(List<TestClass> classes) {
		this.classes = classes;
	}

	@Override
	public int getSize() {
		return classes.size();
	}

	@Override
	public TestClass getElementAt(int index) {
		return classes.get(index);
	}

}
