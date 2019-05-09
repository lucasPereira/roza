package br.ufsc.ine.leb.roza.measurer.intersector;

import java.math.BigDecimal;

import br.ufsc.ine.leb.roza.exceptions.IntervalWithoutOverlapForConcatenationException;
import br.ufsc.ine.leb.roza.exceptions.InvalidIntervalException;

public class Interval {

	private Integer start;
	private Integer end;

	public Interval(Integer start, Integer end) {
		if (start <= 0 || end <= 0 || end < start) {
			throw new InvalidIntervalException();
		}
		this.start = start;
		this.end = end;
	}

	public Integer getStart() {
		return start;
	}

	public Integer getEnd() {
		return end;
	}

	public BigDecimal getLength() {
		return new BigDecimal(end - start + 1);
	}

	public Boolean overlaps(Interval other) {
		return end >= other.start && start <= other.end;
	}

	public Interval concatenate(Interval other) {
		if (!overlaps(other)) {
			throw new IntervalWithoutOverlapForConcatenationException();
		}
		Integer concatenateStart = Math.min(start, other.start);
		Integer concatenateEnd = Math.max(end, other.end);
		return new Interval(concatenateStart, concatenateEnd);
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof Interval) {
			Interval other = (Interval) object;
			return start.equals(other.start) && end.equals(other.end);
		}
		return super.equals(object);
	}

	@Override
	public String toString() {
		return String.format("(%d, %d)", start, end);
	}

}
