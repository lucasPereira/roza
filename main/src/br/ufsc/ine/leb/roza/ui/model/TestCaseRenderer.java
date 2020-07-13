package br.ufsc.ine.leb.roza.ui.model;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import br.ufsc.ine.leb.roza.TestCase;

public class TestCaseRenderer implements ListCellRenderer<TestCase> {

	private DefaultListCellRenderer defaultRederer;

	public TestCaseRenderer() {
		defaultRederer = new DefaultListCellRenderer();
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends TestCase> list, TestCase value, int index, boolean isSelected, boolean cellHasFocus) {
		return defaultRederer.getListCellRendererComponent(list, value.getName(), index, isSelected, cellHasFocus);
	}

}
