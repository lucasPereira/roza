package br.ufsc.ine.leb.roza.measurement.intersector;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.exceptions.IntervalWithoutOverlapForConcatenationException;
import br.ufsc.ine.leb.roza.exceptions.InvalidIntervalException;

import static org.junit.jupiter.api.Assertions.*;

class IntervalTest {

	@Test
	void oneToTen() {
		Interval interval = new Interval(1, 10);
		assertEquals(1, interval.getStart().intValue());
		assertEquals(10, interval.getEnd().intValue());
		assertEquals(new BigDecimal(10), interval.getLength());
	}

	@Test
	void twoToTen() {
		Interval interval = new Interval(2, 10);
		assertEquals(2, interval.getStart().intValue());
		assertEquals(10, interval.getEnd().intValue());
		assertEquals(new BigDecimal(9), interval.getLength());
	}

	@Test
	void oneToOne() {
		Interval interval = new Interval(1, 1);
		assertEquals(1, interval.getStart().intValue());
		assertEquals(1, interval.getEnd().intValue());
		assertEquals(new BigDecimal(1), interval.getLength());
	}

	@Test
	void equals() {
		Interval oneToTen = new Interval(1, 10);
		assertEquals(new Interval(1, 10), oneToTen);
	}

	@Test
	void notEquals() {
		Interval twoToEleven = new Interval(2, 11);
		assertNotEquals(new Interval(1, 10), twoToEleven);
	}

	@Test
	void overlaps() {
		Interval fourToFive = new Interval(4, 5);
		Interval fiveToSix = new Interval(5, 6);
		assertTrue(fourToFive.overlaps(fiveToSix));
		assertTrue(fiveToSix.overlaps(fourToFive));
	}

	@Test
	void notOverlaps() {
		Interval fourToFive = new Interval(4, 5);
		Interval sixToSeven = new Interval(6, 7);
		assertFalse(fourToFive.overlaps(sixToSeven));
		assertFalse(sixToSeven.overlaps(fourToFive));
	}

	@Test
	void concatenate() {
		Interval fourToFive = new Interval(4, 5);
		Interval fiveToSix = new Interval(5, 6);
		Interval fourToSix = new Interval(4, 6);
		assertEquals(fourToSix, fourToFive.concatenate(fiveToSix));
		assertEquals(fourToSix, fiveToSix.concatenate(fourToFive));
	}

	@Test
	void notConcatenate() {
		Interval fourToFive = new Interval(4, 5);
		Interval sixToSeven = new Interval(6, 7);
		assertThrows(IntervalWithoutOverlapForConcatenationException.class, () -> fourToFive.concatenate(sixToSeven));
		assertThrows(IntervalWithoutOverlapForConcatenationException.class, () -> sixToSeven.concatenate(fourToFive));
	}

	@Test
	void startGreaterThanEnd() {
		assertThrows(InvalidIntervalException.class, () -> new Interval(10, 1));
	}

	@Test
	void zeroStart() {
		assertThrows(InvalidIntervalException.class, () -> new Interval(0, 10));
	}

	@Test
	void negativeStart() {
		assertThrows(InvalidIntervalException.class, () -> new Interval(-1, 10));
	}

	@Test
	void zeroEnd() {
		assertThrows(InvalidIntervalException.class, () -> new Interval(1, 0));
	}

	@Test
	void negativeEnd() {
		assertThrows(InvalidIntervalException.class, () -> new Interval(1, -1));
	}

}
