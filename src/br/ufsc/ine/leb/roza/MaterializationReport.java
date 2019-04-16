package br.ufsc.ine.leb.roza;

import java.util.List;

public class MaterializationReport<T extends TestBlock> {

	private String baseFolder;
	private List<TestCaseMaterialization<T>> materializations;

	public MaterializationReport(String baseFolder, List<TestCaseMaterialization<T>> materializations) {
		this.baseFolder = baseFolder;
		this.materializations = materializations;
	}

	public String getBaseFolder() {
		return baseFolder;
	}

	public List<TestCaseMaterialization<T>> getMaterializations() {
		return materializations;
	}

}
