package br.ufsc.ine.leb.roza.ui.window.toolbar.measuring;

import java.awt.Component;
import java.util.List;

import javax.swing.JPanel;

import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.Manager;
import br.ufsc.ine.leb.roza.ui.UiComponent;
import br.ufsc.ine.leb.roza.ui.window.toolbar.Toolbar;

public class MeasuringTab implements UiComponent {

	private Toolbar toolbar;
	private JPanel panel;

	public MeasuringTab(Toolbar toolbar) {
		this.toolbar = toolbar;
	}

	@Override
	public void init(Hub hub, Manager manager) {
		panel = new JPanel();
		toolbar.addComponent("Measuring", panel);
	}

	@Override
	public void addChilds(List<UiComponent> childs) {
		childs.add(new MeasurerComboBox(this));
		childs.add(new DeckardConfigurationInputs(this));
		childs.add(new JplagConfigurationInputs(this));
		childs.add(new SimianConfigurationInputs(this));
		childs.add(new MeasureTestCasesButton(this));
	}

	@Override
	public void start() {}

	public void addComponent(Component component) {
		panel.add(component);
	}

}
