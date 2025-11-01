package br.ufsc.ine.leb.roza.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RingTest {

	private Ring<String> ring;

	@BeforeEach
	void setup() {
		ring = new Ring<>();
	}

	@Test
	void oneElement() {
		ring.add("one");
		assertEquals("one", ring.next());
		assertEquals("one", ring.next());
	}

	@Test
	void twoElements() {
		ring.add("one");
		ring.add("two");
		assertEquals("one", ring.next());
		assertEquals("two", ring.next());
		assertEquals("one", ring.next());
		assertEquals("two", ring.next());
	}

	@Test
	void twoElementsSecondAddedAfterFirstIteration() {
		ring.add("one");
		ring.next();
		ring.add("two");
		assertEquals("two", ring.next());
		assertEquals("one", ring.next());
		assertEquals("two", ring.next());
	}

	@Test
	void empty() {
		assertThrows(NoSuchElementException.class, () -> ring.next());
	}

	@Test
	void resetWithoutIterate() {
		ring.add("one");
		ring.add("two");
		ring.reset();
		assertEquals("one", ring.next());
		assertEquals("two", ring.next());
	}

	@Test
	void resetAfterIterating() {
		ring.add("one");
		ring.add("two");
		ring.next();
		ring.reset();
		assertEquals("one", ring.next());
		assertEquals("two", ring.next());
	}

}
