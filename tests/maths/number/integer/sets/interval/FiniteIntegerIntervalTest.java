package maths.number.integer.sets.interval;

import maths.number.integer.Integer;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static maths.number.integer.sets.interval.IntervalBound.BoundType.CLOSED;
import static maths.number.integer.sets.interval.IntervalBound.BoundType.OPEN;
import static org.junit.Assert.*;

/**
 * @author Steven Weston
 */
public class FiniteIntegerIntervalTest {

	private final FiniteIntegerIntervalFactory factory = new FiniteIntegerIntervalFactory();

	@Test
	public void testSize() throws Exception {
		FiniteIntegerInterval set;
		set = factory.createElement(OPEN, 1, 4, OPEN);
		assertEquals(new Integer(2), set.size());
		set = factory.createElement(OPEN, 1, 3, OPEN);
		assertEquals(new Integer(1), set.size());
		set = factory.createElement(OPEN, 1, 3, CLOSED);
		assertEquals(new Integer(2), set.size());
		set = factory.createElement(CLOSED, 1, 3, OPEN);
		assertEquals(new Integer(2), set.size());
		set = factory.createElement(CLOSED, 1, 3, CLOSED);
		assertEquals(new Integer(3), set.size());
		set = factory.createElement(OPEN, 1, 2, OPEN);
		assertEquals(new Integer(0), set.size());
		set = factory.createElement(OPEN, -1, 1, OPEN);
		assertEquals(new Integer(1), set.size());
	}

	@Test
	public void testContainsValue() {
		FiniteIntegerInterval set;
		set = factory.createElement(OPEN, 1, 4, OPEN);
		assertFalse(set.containsValue(new Integer(1)));
		assertTrue(set.containsValue(new Integer(2)));
		assertTrue(set.containsValue(new Integer(3)));
		assertFalse(set.containsValue(new Integer(4)));
		set = factory.createElement(OPEN, 2, 3, OPEN);
		assertFalse(set.containsValue(new Integer(1)));
		assertFalse(set.containsValue(new Integer(2)));
		assertFalse(set.containsValue(new Integer(3)));
		assertFalse(set.containsValue(new Integer(4)));
		set = factory.createElement(CLOSED, 1, 4, CLOSED);
		assertTrue(set.containsValue(new Integer(1)));
		assertTrue(set.containsValue(new Integer(2)));
		assertTrue(set.containsValue(new Integer(3)));
		assertTrue(set.containsValue(new Integer(4)));
	}

	@Test
	public void testIterator() throws Exception {
		FiniteIntegerInterval set;
		List<Integer> gatheredElements;
		set = factory.createElement(CLOSED, 2, 3, CLOSED);
		gatheredElements = new ArrayList<>();
		for (Integer integer : set) {
			gatheredElements.add(integer);
		}
		assertEquals(2, gatheredElements.size());
		assertEquals(new Integer(2), gatheredElements.get(0));
		assertEquals(new Integer(3), gatheredElements.get(1));

		set = factory.createElement(CLOSED, 2, 4, OPEN);
		gatheredElements = new ArrayList<>();
		for (Integer integer : set) {
			gatheredElements.add(integer);
		}
		assertEquals(2, gatheredElements.size());
		assertEquals(new Integer(2), gatheredElements.get(0));
		assertEquals(new Integer(3), gatheredElements.get(1));

		set = factory.createElement(OPEN, 1, 4, OPEN);
		gatheredElements = new ArrayList<>();
		for (Integer integer : set) {
			gatheredElements.add(integer);
		}
		assertEquals(2, gatheredElements.size());
		assertEquals(new Integer(2), gatheredElements.get(0));
		assertEquals(new Integer(3), gatheredElements.get(1));

		set = factory.createElement(OPEN, 1, 3, CLOSED);
		gatheredElements = new ArrayList<>();
		for (Integer integer : set) {
			gatheredElements.add(integer);
		}
		assertEquals(2, gatheredElements.size());
		assertEquals(new Integer(2), gatheredElements.get(0));
		assertEquals(new Integer(3), gatheredElements.get(1));
	}
}
