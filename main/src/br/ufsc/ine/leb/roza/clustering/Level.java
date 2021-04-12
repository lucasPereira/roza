package br.ufsc.ine.leb.roza.clustering;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import br.ufsc.ine.leb.roza.Cluster;
import br.ufsc.ine.leb.roza.exceptions.NoNextLevelException;
import br.ufsc.ine.leb.roza.exceptions.TiebreakException;

public class Level {

	private Linkage linkage;
	private Referee referee;
	private Set<Cluster> clusters;
	private Integer step;

	public Level(Linkage linkage, Referee referee, Set<Cluster> clusters) {
		this.linkage = linkage;
		this.referee = referee;
		this.clusters = clusters;
		this.step = 0;
	}

	private Level(Integer step, Linkage linkage, Referee referee, Set<Cluster> next) {
		this(linkage, referee, next);
		this.step = step;
	}

	public Boolean hasNextLevel() {
		return clusters.size() > 1;
	}

	public Level generateNextLevel() {
		if (!hasNextLevel()) {
			throw new NoNextLevelException();
		}
		Combination combination = getCombinationToNextLevel();
		Set<Cluster> next = new HashSet<>(clusters.size() - 1);
		Cluster first = combination.getFirst();
		Cluster second = combination.getSecond();
		Cluster merged = first.merge(second);
		next.add(merged);
		clusters.stream().filter((cluster) -> !cluster.equals(first) && !cluster.equals(second)).forEach((cluster) -> next.add(cluster));
		return new Level(step + 1, linkage, referee, next);
	}

	public Set<Cluster> getClusters() {
		return Collections.unmodifiableSet(clusters);
	}

	public Combination getCombinationToNextLevel() {
		if (!hasNextLevel()) {
			return null;
		}
		return new ClusterJoiner(linkage, referee).join(new ClustersToMerge(clusters));
	}

	public BigDecimal getEvaluationToNextLevel() {
		if (!hasNextLevel()) {
			return null;
		}
		try {
			Combination combination = getCombinationToNextLevel();
			return linkage.evaluate(combination.getFirst(), combination.getSecond());
		} catch (TiebreakException exception) {
			return null;
		}
	}

	public Integer getStep() {
		return step;
	}

}
