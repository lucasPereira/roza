package br.ufsc.ine.leb.roza.ui.window.toolbar;

import java.awt.Component;

import javax.swing.JToolBar;

import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.Manager;
import br.ufsc.ine.leb.roza.ui.UiComponent;
import br.ufsc.ine.leb.roza.ui.window.Window;
import br.ufsc.ine.leb.roza.ui.window.toolbar.clustering.LinkageMethodComboBox;
import br.ufsc.ine.leb.roza.ui.window.toolbar.clustering.RefereeComboBox;
import br.ufsc.ine.leb.roza.ui.window.toolbar.clustering.StartTestsDistributionButton;
import br.ufsc.ine.leb.roza.ui.window.toolbar.clustering.ThresholdCriteriaComboBox;
import br.ufsc.ine.leb.roza.ui.window.toolbar.clustering.ThresholdCriteriaInput;
import br.ufsc.ine.leb.roza.ui.window.toolbar.extraction.ExtractTestCasesButton;
import br.ufsc.ine.leb.roza.ui.window.toolbar.extraction.ExtractorComboBox;
import br.ufsc.ine.leb.roza.ui.window.toolbar.measuring.DeckardConfigurationInputs;
import br.ufsc.ine.leb.roza.ui.window.toolbar.measuring.JplagConfigurationInputs;
import br.ufsc.ine.leb.roza.ui.window.toolbar.measuring.MeasureTestCasesButton;
import br.ufsc.ine.leb.roza.ui.window.toolbar.measuring.MeasurerComboBox;
import br.ufsc.ine.leb.roza.ui.window.toolbar.measuring.SimianConfigurationInputs;
import br.ufsc.ine.leb.roza.ui.window.toolbar.parsing.LoadTestClassesButton;
import br.ufsc.ine.leb.roza.ui.window.toolbar.parsing.ParserComboBox;

public class Toolbar implements UiComponent {

	private Hub hub;
	private Manager manager;
	private Window window;
	private JToolBar bar;

	public Toolbar(Hub hub, Manager manager, Window window) {
		this.hub = hub;
		this.manager = manager;
		this.window = window;
		init();
		createChilds();
	}

	@Override
	public void init() {
		bar = new JToolBar();
		window.addComponent(bar);
	}

	@Override
	public void createChilds() {
		new ParserComboBox(manager, this);
		new LoadTestClassesButton(hub, manager, this);
		addComponent(new JToolBar.Separator());
		new ExtractorComboBox(hub, manager, this);
		new ExtractTestCasesButton(hub, manager, this);
		addComponent(new JToolBar.Separator());
		new MeasurerComboBox(hub, manager, this);
		new DeckardConfigurationInputs(hub, this);
		new JplagConfigurationInputs(hub, this);
		new SimianConfigurationInputs(hub, this);
		new MeasureTestCasesButton(hub, manager, this);
		addComponent(new JToolBar.Separator());
		new LinkageMethodComboBox(hub, this);
		new RefereeComboBox(hub, this);
		new ThresholdCriteriaComboBox(hub, this);
		new ThresholdCriteriaInput(this);
		new StartTestsDistributionButton(hub, manager, this);
	}

	public void addComponent(Component component) {
		bar.add(component);
	}

}
