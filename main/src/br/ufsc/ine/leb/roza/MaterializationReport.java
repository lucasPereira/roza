package br.ufsc.ine.leb.roza;

import java.util.List;

public class MaterializationReport {

	private String baseFolder;
	private List<TestCaseMaterialization> materializations;

	public MaterializationReport(String baseFolder, List<TestCaseMaterialization> materializations) {
		this.baseFolder = baseFolder;
		this.materializations = materializations;
	}

	public String getBaseFolder() {
		return baseFolder;
	}

	public List<TestCaseMaterialization> getMaterializations() {
		return materializations;
	}

	@Override
	public String toString() {
		StringBuilder text = new StringBuilder();
		materializations.forEach(materialization -> {
			text.append(materialization.toString());
			text.append(System.lineSeparator());
		});
		return text.toString();
	}

}
