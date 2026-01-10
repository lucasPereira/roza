package br.ufsc.ine.leb.roza.clustering;

public class BiggestClusterReferee extends SizeClusterReferee implements Referee {

	public BiggestClusterReferee() {
		super(Integer.MIN_VALUE);
	}

	@Override
	public Integer compare(Integer chosenValue, Integer current) {
		return current.compareTo(chosenValue);
	}

	@Override
	public String toString() {
		return "Biggest cluster";
	}
}
