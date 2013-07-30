package logic.set;

import logic.TestClass;
import logic.set.finite.FiniteSet;
import maths.number.integer.Integer;
import maths.number.integer.model.universe.IntegerSet;
import maths.number.integer.model.universe.PrimeNumberSet;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Steven Weston
 */
public class UniterTest {
	@Test
	public void testUnion() throws Exception {
		FiniteSet<TestClass> A = new FiniteSet<>("A");
		A.put(new TestClass("x"));
		A.put(new TestClass("y"));

		FiniteSet<TestClass> B = new FiniteSet<>("B");
		B.put(new TestClass("y"));
		B.put(new TestClass("z"));

		FiniteSet<FiniteSet<TestClass>> sets = new FiniteSet<>("sets");
		sets.put(A);
		sets.put(B);

		Set<TestClass> union = Uniter.unite(sets);
		assertEquals("Expect union's size to be 3", union.size(), 3);
		assertTrue("Expect union's name to be A ∪ B or B ∪ A",
				"A ∪ B".equals(union.getName()) || "B ∪ A".equals(union.getName()));
		assertTrue("Expect union to contain x", union.contains("x"));
		assertTrue("Expect union to contain y", union.contains("y"));
		assertTrue("Expect union to contain z", union.contains("z"));
	}

	@Test
	public void testUnionWithInfiniteSet() throws Exception {
		ModifiableSet<Integer> X = new FiniteSet<>("X");
		X.put(new maths.number.integer.Integer(1));
		assertTrue(X.contains("1"));
		assertFalse(X.contains("2"));

		Set<Integer> ℙ = new PrimeNumberSet("ℙ");
		assertFalse(ℙ.contains("1"));
		assertTrue(ℙ.contains("2"));

		FiniteSet<Set<Integer>> sets = new FiniteSet<>("sets");
		sets.put(X);
		sets.put(ℙ);

		Set<Integer> union = Uniter.unite(sets);

		assertTrue(union.contains("1"));
		assertTrue(union.contains("2"));
	}

	@Test
	public void testUnionOfInfiniteSets() throws Exception {
		Set<Integer> ℤ = new IntegerSet("ℤ");
		assertTrue(ℤ.contains("1"));
		assertTrue(ℤ.contains("2"));

		Set<Integer> ℙ = new PrimeNumberSet("ℙ");
		assertFalse(ℙ.contains("1"));
		assertTrue(ℙ.contains("2"));

		FiniteSet<Set<Integer>> sets = new FiniteSet<>("sets");
		sets.put(ℤ);
		sets.put(ℙ);

		Set<Integer> union = Uniter.unite(sets);

		assertTrue(union.contains("1"));
		assertTrue(union.contains("2"));
	}
}
