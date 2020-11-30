package br.ufsc.ine.leb.roza.ui.window.toolbar.extraction;

import java.util.Arrays;
import java.util.List;

import javax.swing.JComboBox;

import br.ufsc.ine.leb.roza.extraction.Junit4TestCaseExtractor;
import br.ufsc.ine.leb.roza.extraction.Junit5TestCaseExtractor;
import br.ufsc.ine.leb.roza.extraction.JunitTestCaseExtractor;
import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.Manager;
import br.ufsc.ine.leb.roza.ui.UiComponent;
import br.ufsc.ine.leb.roza.ui.shared.ComboBoxBuilder;
import br.ufsc.ine.leb.roza.ui.window.toolbar.Toolbar;

public class ExtractorComboBox implements UiComponent {

	private Hub hub;
	private Manager manager;
	private Toolbar toolbar;

	public ExtractorComboBox(Hub hub, Manager manager, Toolbar toolbar) {
		this.hub = hub;
		this.manager = manager;
		this.toolbar = toolbar;
		init();
		createChilds();
	}

	@Override
	public void init() {
		JComboBox<String> combo = new ComboBoxBuilder("Extractor").add("JUnit 4", () -> {
			manager.setTestCaseExtractor(new Junit4TestCaseExtractor());
		}).add("JUnit 5", () -> {
			manager.setTestCaseExtractor(new Junit5TestCaseExtractor());
		}).add("JUnit 5 - OMP/CAPES Project", () -> {
			List<String> assertions = Arrays.asList("assegurarTexto", "assegurarValor", "assegurarQuantidadeDeElementos", "assegurarConteudoDeArquivoBaixado", "assegurarNaoMarcado", "assegurarMarcado", "assegurarMarcacao");
			manager.setTestCaseExtractor(new JunitTestCaseExtractor(assertions));
		}).build();
		combo.setSelectedIndex(2);
		hub.loadTestClassesSubscribe(classes -> combo.setEnabled(true));
		toolbar.addComponent(combo);
	}

	@Override
	public void createChilds() {}

}
