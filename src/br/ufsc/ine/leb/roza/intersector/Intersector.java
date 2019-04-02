package br.ufsc.ine.leb.roza.intersector;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public class Intersector<T> {

	public BigDecimal evaluate() {
		return BigDecimal.ZERO;
	}

	public List<Interval> getIntervals() {
		return new LinkedList<>();
	}

	public void addSegment(T object, Integer start, Integer end) {}

}
