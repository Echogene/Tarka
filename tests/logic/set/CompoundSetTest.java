package logic.set;

import logic.set.finite.StandardSet;
import logic.set.infinite.CompoundSet;
import maths.number.integer.Integer;
import maths.number.integer.model.universe.PrimeNumberSet;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Steven Weston
 */
public class CompoundSetTest {
	@Test
	public void testContains() throws Exception {
		StandardSet<Integer> X = new StandardSet<>("X");
		X.put(new Integer(1));
		assertTrue(X.contains("1"));
		assertFalse(X.contains("2"));
		assertFalse(X.contains("4"));

		PrimeNumberSet ℙ = new PrimeNumberSet("ℙ");
		assertFalse(ℙ.contains("1"));
		assertTrue(ℙ.contains("2"));
		assertFalse(ℙ.contains("4"));

		CompoundSet<Integer> Y = new CompoundSet<>("Y", X, ℙ);
		assertTrue(Y.containsValue(new Integer(1)));
		assertTrue(Y.containsValue(new Integer(2)));
		assertFalse(Y.containsValue(new Integer(4)));
	}
}
