package br.ufsc.ine.leb.roza.ui.shared;

import java.util.LinkedList;
import java.util.List;

import javax.swing.JComboBox;

public class ComboBoxBuilder {

	private final JComboBox<String> combo;
	private final List<ComboBoxListener> listeners;

	public ComboBoxBuilder(String title) {
		combo = new JComboBox<>();
		combo.setToolTipText(title);
		listeners = new LinkedList<>();
	}

	public ComboBoxBuilder add(String option, ComboBoxListener listener) {
		combo.addItem(option);
		listeners.add(listener);
		return this;
	}

	public JComboBox<String> build() {
		combo.addActionListener(event -> {
			int selected = combo.getSelectedIndex();
			listeners.get(selected).execute();
		});
		combo.setMaximumSize(combo.getPreferredSize());
		return combo;
	}

}
