package logic.set.finite;

import logic.TestClass;
import logic.set.Set;
import maths.number.integer.Integer;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * @author Steven Weston
 */
@SuppressWarnings("unchecked")
public class FiniteImpoundSetTest {

	@Test
	public void testSmallestSet() throws Exception {
		StandardSet<TestClass> emptySet = createSet("∅");
		StandardSet<TestClass> oneElement = createSet("1", "x");
		StandardSet<TestClass> twoElements = createSet("2", "y", "z");

		FiniteImpoundSet<TestClass> intersection;
		intersection = createIntersection(emptySet, oneElement, twoElements);
		assertEquals(emptySet, intersection.smallestSet());

		intersection = createIntersection(oneElement, twoElements);
		assertEquals(oneElement, intersection.smallestSet());
	}

	@Test
	public void testContainsValue() throws Exception {
		StandardSet<TestClass> emptySet = createSet("∅");
		StandardSet<TestClass> oneElement = createSet("1", "x");
		StandardSet<TestClass> twoElements = createSet("2", "y", "z");
		StandardSet<TestClass> threeElements = createSet("3", "x", "y", "z");

		FiniteImpoundSet<TestClass> intersection;
		intersection = createIntersection(emptySet, oneElement, twoElements, threeElements);
		assertFalse(intersection.containsValue(new TestClass("x")));
		assertFalse(intersection.containsValue(new TestClass("y")));
		assertFalse(intersection.containsValue(new TestClass("z")));

		intersection = createIntersection(oneElement, threeElements);
		assertTrue(intersection.containsValue(new TestClass("x")));
		assertFalse(intersection.containsValue(new TestClass("y")));
		assertFalse(intersection.containsValue(new TestClass("z")));

		intersection = createIntersection(twoElements, threeElements);
		assertFalse(intersection.containsValue(new TestClass("x")));
		assertTrue(intersection.containsValue(new TestClass("y")));
		assertTrue(intersection.containsValue(new TestClass("z")));
	}

	@Test
	public void testIterator() throws Exception {
		StandardSet<TestClass> emptySet = createSet("∅");
		StandardSet<TestClass> oneElement = createSet("1", "x");
		StandardSet<TestClass> twoElements = createSet("2", "y", "z");
		StandardSet<TestClass> threeElements = createSet("3", "x", "y", "z");
		StandardSet<TestClass> otherThreeElements = createSet("3", "a", "b", "c");

		FiniteImpoundSet<TestClass> intersection;
		List<TestClass> elements;

		intersection = createIntersection(threeElements);
		elements = getElementList(intersection);
		assertEquals("Expect " + elements + " to have 3 elements.", 3, elements.size());

		intersection = createIntersection(twoElements, threeElements);
		elements = getElementList(intersection);
		assertEquals("Expect " + elements + " to have 2 elements.", 2, elements.size());

		intersection = createIntersection(oneElement, threeElements);
		elements = getElementList(intersection);
		assertEquals("Expect " + elements + " to have 1 element.", 1, elements.size());

		intersection = createIntersection(emptySet, oneElement, twoElements, threeElements);
		elements = getElementList(intersection);
		assertEquals("Expect " + elements + " to be empty.", 0, elements.size());

		intersection = createIntersection(twoElements, otherThreeElements);
		elements = getElementList(intersection);
		assertEquals("Expect " + elements + " to be empty.", 0, elements.size());

		intersection = createIntersection(threeElements, otherThreeElements);
		elements = getElementList(intersection);
		assertEquals("Expect " + elements + " to be empty.", 0, elements.size());

		intersection = createIntersection(oneElement, twoElements, threeElements);
		elements = getElementList(intersection);
		assertEquals("Expect " + elements + " to be empty.", 0, elements.size());
	}

	@Test
	public void testSize() throws Exception {
		StandardSet<TestClass> emptySet = createSet("∅");
		StandardSet<TestClass> oneElement = createSet("1", "x");
		StandardSet<TestClass> twoElements = createSet("2", "y", "z");
		StandardSet<TestClass> threeElements = createSet("3", "x", "y", "z");
		StandardSet<TestClass> otherThreeElements = createSet("3", "a", "b", "c");

		FiniteImpoundSet<TestClass> intersection;

		intersection = createIntersection(threeElements);
		assertEquals(new Integer(3), intersection.size());

		intersection = createIntersection(twoElements, threeElements);
		assertEquals(new Integer(2), intersection.size());

		intersection = createIntersection(oneElement, threeElements);
		assertEquals(new Integer(1), intersection.size());

		intersection = createIntersection(emptySet, oneElement, twoElements, threeElements);
		assertEquals(new Integer(0), intersection.size());

		intersection = createIntersection(twoElements, otherThreeElements);
		assertEquals(new Integer(0), intersection.size());

		intersection = createIntersection(threeElements, otherThreeElements);
		assertEquals(new Integer(0), intersection.size());

		intersection = createIntersection(oneElement, twoElements, threeElements);
		assertEquals(new Integer(0), intersection.size());
	}

	private List<TestClass> getElementList(FiniteImpoundSet<TestClass> intersection) {
		List<TestClass> elements;
		elements = new ArrayList<>();
		for (TestClass test : intersection) {
			elements.add(test);
		}
		return elements;
	}

	private StandardSet<TestClass> createSet(String name, String... tests) {
		StandardSet<TestClass> output = new StandardSet<>(name);
		for (String test : tests) {
			output.put(new TestClass(test));
		}
		return output;
	}

	private FiniteImpoundSet<TestClass> createIntersection(Set<TestClass>... intersectees) {
		java.util.Set<Set<TestClass>> set = new HashSet<>();
		Collections.addAll(set, intersectees);

		return new FiniteImpoundSet<>("", set);
	}
}
