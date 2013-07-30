package logic.set;

import maths.number.integer.Integer;
import maths.number.integer.model.universe.PrimeNumberSet;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Steven Weston
 */
public class FiniteSetTest {
	@Test
	public void testUnionWithInfiniteSet() throws Exception {
		ModifiableSet<Integer> X = new FiniteSet<>("X");
		X.put(new Integer(1));
		assertTrue(X.contains("1"));

		Set<Integer> ℙ = new PrimeNumberSet("ℙ");
		assertFalse(ℙ.contains("1"));

		Set<Integer> union = AbstractSet.union(X, ℙ);

		assertTrue(union.contains("1"));
	}
}
