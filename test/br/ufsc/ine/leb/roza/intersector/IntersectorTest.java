package br.ufsc.ine.leb.roza.intersector;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IntersectorTest {

	private Interval oneToTen;
	private Interval oneToFive;
	private Interval tenToTen;
	private Interval oneToThree;
	private Interval eightToTen;
	private Interval fourToSeven;
	private Interval threeToSeven;

	@BeforeEach
	void setup() {
		oneToTen = new Interval(1, 10);
		oneToFive = new Interval(1, 5);
		tenToTen = new Interval(10, 10);
		oneToThree = new Interval(1, 3);
		eightToTen = new Interval(8, 10);
		threeToSeven = new Interval(3, 7);
		fourToSeven = new Interval(4, 7);
	}

	@Test
	void withoutIntersection() throws Exception {
		Intersector<?> intersector = new Intersector<>("a", "b", 10);
		assertEquals(BigDecimal.ZERO, intersector.evaluate());
		assertEquals(0, intersector.getIntervals().size());
	}

	@Test
	void oneFullSegment() throws Exception {
		Intersector<String> intersector = new Intersector<>("a", "b", 10);
		intersector.addSegment(1, 10);
		assertEquals(BigDecimal.ONE, intersector.evaluate());
		assertEquals(1, intersector.getIntervals().size());
		assertEquals(oneToTen, intersector.getIntervals().get(0));
	}

	@Test
	void oneSegmentInTheBegin() throws Exception {
		Intersector<String> intersector = new Intersector<>("a", "b", 10);
		intersector.addSegment(1, 5);
		assertEquals(new BigDecimal("0.5"), intersector.evaluate());
		assertEquals(1, intersector.getIntervals().size());
		assertEquals(oneToFive, intersector.getIntervals().get(0));
	}

	@Test
	void oneSegmentInTheEnd() throws Exception {
		Intersector<String> intersector = new Intersector<>("a", "b", 10);
		intersector.addSegment(10, 10);
		assertEquals(new BigDecimal("0.1"), intersector.evaluate());
		assertEquals(1, intersector.getIntervals().size());
		assertEquals(tenToTen, intersector.getIntervals().get(0));
	}

	@Test
	void twoSegments() throws Exception {
		Intersector<String> intersector = new Intersector<>("a", "b", 10);
		intersector.addSegment(1, 3);
		intersector.addSegment(8, 10);
		assertEquals(new BigDecimal("0.6"), intersector.evaluate());
		assertEquals(2, intersector.getIntervals().size());
		assertEquals(oneToThree, intersector.getIntervals().get(0));
		assertEquals(eightToTen, intersector.getIntervals().get(1));
	}

	@Test
	void twoSegmentsWithOverlap() throws Exception {
		Intersector<String> intersector = new Intersector<>("a", "b", 10);
		intersector.addSegment(4, 6);
		intersector.addSegment(5, 7);
		assertEquals(new BigDecimal("0.4"), intersector.evaluate());
		assertEquals(1, intersector.getIntervals().size());
		assertEquals(fourToSeven, intersector.getIntervals().get(0));
	}

	@Test
	void threeSegmentsWithOverlap() throws Exception {
		Intersector<String> intersector = new Intersector<>("a", "b", 10);
		intersector.addSegment(3, 4);
		intersector.addSegment(6, 7);
		intersector.addSegment(4, 6);
		assertEquals(new BigDecimal("0.5"), intersector.evaluate());
		assertEquals(1, intersector.getIntervals().size());
		assertEquals(threeToSeven, intersector.getIntervals().get(0));
	}

}
