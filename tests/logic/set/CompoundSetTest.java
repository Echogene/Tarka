package logic.set;

import maths.number.integer.Integer;
import maths.number.integer.model.universe.PrimeNumberSet;
import org.junit.Test;

import static junit.framework.Assert.assertNull;
import static org.junit.Assert.*;

/**
 * @author Steven Weston
 */
public class CompoundSetTest {
	@Test
	public void testContains() throws Exception {
		FiniteSet<Integer> X = new FiniteSet<>("X");
		X.put(new Integer(1));
		assertTrue(X.contains("1"));
		assertFalse(X.contains("2"));
		assertFalse(X.contains("4"));

		PrimeNumberSet ℙ = new PrimeNumberSet("ℙ");
		assertFalse(ℙ.contains("1"));
		assertTrue(ℙ.contains("2"));
		assertFalse(ℙ.contains("4"));

		CompoundSet<Integer> Y = new CompoundSet<>("Y", X, ℙ);
		assertTrue(Y.contains("1"));
		assertTrue(Y.contains("2"));
		assertFalse(Y.contains("4"));

		assertTrue(Y.containsValue(new Integer(1)));
		assertTrue(Y.containsValue(new Integer(2)));
		assertFalse(Y.containsValue(new Integer(4)));

		assertEquals(new Integer(1), Y.get("1"));
		assertEquals(new Integer(2), Y.get("2"));
		assertNull(Y.get("4"));
	}
}
