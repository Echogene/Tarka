package logic.set.finite.multiset;

import logic.TestClass;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Steven Weston
 */
public class NamedMultiSetTest {
	@Test
	public void testStuff() throws Exception {
		NamedMultiSet<TestClass> multiSet = new NamedMultiSet<>("multiSet");

		assertFalse(multiSet.contains("x"));

		TestClass x = new TestClass("x");
		multiSet.add(x);
		assertTrue(multiSet.contains(x));

		multiSet.add(x);
		assertTrue(multiSet.contains(x));

		multiSet.remove(x);
		assertTrue(multiSet.contains(x));

		multiSet.remove(x);
		assertFalse(multiSet.contains(x));
	}
}
