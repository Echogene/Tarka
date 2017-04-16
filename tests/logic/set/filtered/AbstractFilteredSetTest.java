package logic.set.filtered;

import logic.TestClass;
import logic.set.finite.StandardSet;
import maths.number.integer.Integer;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Steven Weston
 */
public class AbstractFilteredSetTest {

	@Test
	public void testContainsValue() throws Exception {
		TestClass x = new TestClass("x");
		TestClass y = new TestClass("y");
		StandardSet<TestClass> X = new StandardSet<>("X");
		X.put(x);
		X.put(y);
		FiniteFilteredSet<TestClass> set = new FiniteFilteredSet<>("X", X, t -> t.equals(x));

		assertTrue(set.containsValue(x));
		assertFalse(set.containsValue(y));
	}

	@Test
	public void testIterator() throws Exception {
		TestClass x = new TestClass("x");
		TestClass y = new TestClass("y");
		TestClass z = new TestClass("z");
		StandardSet<TestClass> X = new StandardSet<>("X");
		X.put(x);
		X.put(y);
		X.put(z);
		FiniteFilteredSet<TestClass> set = new FiniteFilteredSet<>("X", X, t -> !t.equals(x));

		int count = 0;
		for (TestClass t : set) {
			assertTrue(!t.equals(x));
			count++;
		}
		assertEquals(2, count);

		assertEquals(new Integer(2), set.size());
	}
}
