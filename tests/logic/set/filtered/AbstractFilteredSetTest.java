package logic.set.filtered;

import logic.TestClass;
import logic.set.finite.StandardSet;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
}
