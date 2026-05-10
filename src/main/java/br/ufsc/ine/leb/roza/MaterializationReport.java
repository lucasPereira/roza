package br.ufsc.ine.leb.roza;

import java.util.List;

public class MaterializationReport {

	private final String baseFolder;
	private final List<TestCaseMaterialization> materialization;

	public MaterializationReport(String baseFolder, List<TestCaseMaterialization> materialization) {
		this.baseFolder = baseFolder;
		this.materialization = materialization;
	}

	public String getBaseFolder() {
		return baseFolder;
	}

	public List<TestCaseMaterialization> getMaterialization() {
		return materialization;
	}

	@Override
	public String toString() {
		StringBuilder text = new StringBuilder();
		materialization.forEach(materialization -> {
			text.append(materialization.toString());
			text.append(System.lineSeparator());
		});
		return text.toString();
	}

}
