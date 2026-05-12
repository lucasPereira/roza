package br.ufsc.ine.leb.roza.core.modern.refactoring;

public enum ImplicitSetupPackagePolicy {
	DEFAULT_PACKAGE("Default package"),
	PACKAGE_PARTITIONING("Package partitioning");

	private final String displayName;

	ImplicitSetupPackagePolicy(String displayName) {
		this.displayName = displayName;
	}

	public String displayName() {
		return displayName;
	}
}
