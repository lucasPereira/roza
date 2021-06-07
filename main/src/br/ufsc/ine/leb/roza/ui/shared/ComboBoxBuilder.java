package br.ufsc.ine.leb.roza.ui.shared;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JComboBox;

public class ComboBoxBuilder {

	private JComboBox<String> combo;
	private List<ComboBoxListener> listeners;

	public ComboBoxBuilder(String title) {
		combo = new JComboBox<String>();
		combo.setToolTipText(title);
		listeners = new LinkedList<>();
	}

	public ComboBoxBuilder add(String option, ComboBoxListener listener) {
		combo.addItem(option);
		listeners.add(listener);
		return this;
	}

	public JComboBox<String> build() {
		combo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				Integer selected = combo.getSelectedIndex();
				listeners.get(selected).execute();
			}

		});
		combo.setMaximumSize(combo.getPreferredSize());
		return combo;
	}

}
