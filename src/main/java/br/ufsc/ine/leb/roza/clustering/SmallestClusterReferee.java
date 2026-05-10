package br.ufsc.ine.leb.roza.clustering;

public class SmallestClusterReferee extends SizeClusterReferee implements Referee {

	public SmallestClusterReferee() {
		super(Integer.MAX_VALUE);
	}

	@Override
	public Integer compare(Integer chosenValue, Integer current) {
		return -current.compareTo(chosenValue);
	}

	@Override
	public String toString() {
		return "Smallest cluster";
	}
}
