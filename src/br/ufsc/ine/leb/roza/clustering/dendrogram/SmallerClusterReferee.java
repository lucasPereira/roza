package br.ufsc.ine.leb.roza.clustering.dendrogram;

import br.ufsc.ine.leb.roza.Cluster;

class SmallerClusterReferee implements Referee {

	@Override
	public Combination untie(Combination first, Combination second) {
		if (first == null) {
			return second;
		}
		if (second == null) {
			return first;
		}
		Cluster firstMerged = first.getFirst().merge(first.getSecond());
		Cluster secondMerged = second.getFirst().merge(second.getSecond());
		Integer sizeFirst = firstMerged.getTestCases().size();
		Integer sizeSecond = secondMerged.getTestCases().size();
		return sizeFirst == sizeSecond ? null : (sizeFirst < sizeSecond ? first : second);
	}

}
