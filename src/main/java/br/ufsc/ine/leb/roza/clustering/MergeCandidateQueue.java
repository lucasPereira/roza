package br.ufsc.ine.leb.roza.clustering;

import br.ufsc.ine.leb.roza.Cluster;
import br.ufsc.ine.leb.roza.exceptions.NoCombinationToChooseException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

class MergeCandidateQueue {

	private final Linkage linkage;
	private final Referee referee;
	private final ClusteringStatistics statistics;
	private final Set<Cluster> activeClusters;
	private final PriorityQueue<MergeCandidate> candidates;

	MergeCandidateQueue(Set<Cluster> clusters, Linkage linkage, Referee referee, ClusteringStatistics statistics) {
		this.linkage = linkage;
		this.referee = referee;
		this.statistics = statistics;
		this.activeClusters = new HashSet<>(clusters);
		this.candidates = new PriorityQueue<>();
		addInitialCandidates(clusters);
	}

	WinnerCombination next() {
		Set<Combination> chosen = new HashSet<>();
		BigDecimal nearestDistance = null;
		while (!candidates.isEmpty()) {
			MergeCandidate candidate = candidates.poll();
			if (!candidate.isActive(activeClusters)) {
				continue;
			}
			if (nearestDistance == null) {
				nearestDistance = candidate.evaluation;
				chosen.add(candidate.combination);
			} else if (candidate.evaluation.compareTo(nearestDistance) == 0) {
				chosen.add(candidate.combination);
			} else {
				candidates.add(candidate);
				break;
			}
		}
		if (chosen.isEmpty()) {
			throw new NoCombinationToChooseException();
		}
		Combination winner = referee.untie(chosen);
		for (Combination combination : chosen) {
			if (!combination.equals(winner)) {
				candidates.add(new MergeCandidate(combination, nearestDistance));
			}
		}
		statistics.recordSelectedMerge();
		return new WinnerCombination(winner, nearestDistance);
	}

	void merge(Cluster first, Cluster second, Cluster merged) {
		activeClusters.remove(first);
		activeClusters.remove(second);
		for (Cluster cluster : activeClusters) {
			addCandidate(merged, cluster);
		}
		activeClusters.add(merged);
	}

	private void addInitialCandidates(Set<Cluster> clusters) {
		List<Cluster> ordered = new ArrayList<>(clusters);
		for (int first = 0; first < ordered.size(); first++) {
			for (int second = first + 1; second < ordered.size(); second++) {
				addCandidate(ordered.get(first), ordered.get(second));
			}
		}
	}

	private void addCandidate(Cluster first, Cluster second) {
		BigDecimal evaluation = linkage.evaluate(first, second);
		statistics.recordGeneratedCandidate();
		statistics.recordLinkageEvaluation();
		candidates.add(new MergeCandidate(new Combination(first, second), evaluation));
	}

	private static class MergeCandidate implements Comparable<MergeCandidate> {

		private final Combination combination;
		private final BigDecimal evaluation;

		private MergeCandidate(Combination combination, BigDecimal evaluation) {
			this.combination = combination;
			this.evaluation = evaluation;
		}

		private boolean isActive(Set<Cluster> activeClusters) {
			return activeClusters.contains(combination.getFirst()) && activeClusters.contains(combination.getSecond());
		}

		@Override
		public int compareTo(MergeCandidate other) {
			return -evaluation.compareTo(other.evaluation);
		}
	}
}
