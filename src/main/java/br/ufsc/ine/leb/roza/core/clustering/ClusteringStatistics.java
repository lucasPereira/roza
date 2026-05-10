package br.ufsc.ine.leb.roza.core.clustering;

public class ClusteringStatistics {

	private long generatedCandidates;
	private long linkageEvaluations;
	private long selectedMerges;

	void recordGeneratedCandidate() {
		generatedCandidates++;
	}

	void recordLinkageEvaluation() {
		linkageEvaluations++;
	}

	void recordSelectedMerge() {
		selectedMerges++;
	}

	public long getGeneratedCandidates() {
		return generatedCandidates;
	}

	public long getLinkageEvaluations() {
		return linkageEvaluations;
	}

	public long getSelectedMerges() {
		return selectedMerges;
	}
}
