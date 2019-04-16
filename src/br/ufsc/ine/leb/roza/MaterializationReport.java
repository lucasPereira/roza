package br.ufsc.ine.leb.roza;

import java.util.List;

public class MaterializationReport<T extends TestBlock> {

	private String baseFolder;
	private List<TestMaterialization<T>> materializations;

	public MaterializationReport(String baseFolder, List<TestMaterialization<T>> materializations) {
		this.baseFolder = baseFolder;
		this.materializations = materializations;
	}

	public String getBaseFolder() {
		return baseFolder;
	}

	public List<TestMaterialization<T>> getMaterializations() {
		return materializations;
	}

}
