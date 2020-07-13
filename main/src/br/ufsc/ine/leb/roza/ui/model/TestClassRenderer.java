package br.ufsc.ine.leb.roza.ui.model;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import br.ufsc.ine.leb.roza.TestClass;

public class TestClassRenderer implements ListCellRenderer<TestClass> {

	private DefaultListCellRenderer defaultRederer;

	public TestClassRenderer() {
		defaultRederer = new DefaultListCellRenderer();
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends TestClass> list, TestClass value, int index, boolean isSelected, boolean cellHasFocus) {
		return defaultRederer.getListCellRendererComponent(list, value.getName(), index, isSelected, cellHasFocus);
	}

}
