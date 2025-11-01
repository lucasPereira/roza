package br.ufsc.ine.leb.roza.ui.window.toolbar.decomposition;

import br.ufsc.ine.leb.roza.extraction.Junit4TestCaseExtractor;
import br.ufsc.ine.leb.roza.extraction.Junit5TestCaseExtractor;
import br.ufsc.ine.leb.roza.extraction.JunitTestCaseExtractor;
import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.Manager;
import br.ufsc.ine.leb.roza.ui.UiComponent;
import br.ufsc.ine.leb.roza.ui.shared.ComboBoxBuilder;

import javax.swing.JComboBox;
import java.util.List;

public class ExtractorComboBox implements UiComponent {

	private final DecompositionTab toolbar;
	private JComboBox<String> combo;

	public ExtractorComboBox(DecompositionTab toolbar) {
		this.toolbar = toolbar;
	}

	@Override
	public void init(Hub hub, Manager manager) {
		combo = new ComboBoxBuilder("Extractor").add("JUnit 4", () -> manager.setTestCaseExtractor(new Junit4TestCaseExtractor())).add("JUnit 5", () -> manager.setTestCaseExtractor(new Junit5TestCaseExtractor())).add("JUnit 5 - OMP/CAPES Project", () -> {
			List<String> assertions = List.of("assegurarTexto", "assegurarValor", "assegurarQuantidadeDeElementos", "assegurarConteudoDeArquivoBaixado", "assegurarNaoMarcado", "assegurarMarcado", "assegurarMarcacao");
			manager.setTestCaseExtractor(new JunitTestCaseExtractor(assertions));
		}).build();
		toolbar.addComponent(combo);
	}

	@Override
	public void addChildren(List<UiComponent> children) {
	}

	@Override
	public void start() {
		combo.setSelectedIndex(2);
	}

}
