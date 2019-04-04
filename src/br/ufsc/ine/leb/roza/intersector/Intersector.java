package br.ufsc.ine.leb.roza.intersector;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Intersector<T> {

	private List<Interval> intervals;
	private BigDecimal length;

	public Intersector(T source, T target, Integer length) {
		intervals = new LinkedList<Interval>();
		this.length = new BigDecimal(length);
	}

	public BigDecimal evaluate() {
		BigDecimal sum = BigDecimal.ZERO;
		for (Interval interval : intervals) {
			sum = sum.add(interval.getLength());
		}
		return sum.divide(length);
	}

	public List<Interval> getIntervals() {
		return intervals;
	}

	public void addSegment(Integer start, Integer end) {
		Interval newInterval = new Interval(start, end);
		Interval overlaidInterval = null;
		do {
			overlaidInterval = peekOverlaidInterval(newInterval);
			if (overlaidInterval != null) {
				newInterval = newInterval.concatenate(overlaidInterval);
			}
		} while (overlaidInterval != null);
		intervals.add(newInterval);
	}

	private Interval peekOverlaidInterval(Interval interval) {
		Iterator<Interval> iterator = intervals.iterator();
		while (iterator.hasNext()) {
			Interval other = iterator.next();
			if (interval.overlaps(other)) {
				iterator.remove();
				return other;
			}
		}
		return null;
	}

}
