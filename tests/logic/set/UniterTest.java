package logic.set;

import logic.TestClass;
import logic.set.finite.StandardSet;
import maths.number.integer.Integer;
import maths.number.integer.model.universe.IntegerSet;
import maths.number.integer.model.universe.PrimeNumberSet;
import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.*;

/**
 * @author Steven Weston
 */
public class UniterTest {
	@Test
	public void testUnion() throws Exception {
		StandardSet<TestClass> A = new StandardSet<>("A");
		A.put(new TestClass("x"));
		A.put(new TestClass("y"));

		StandardSet<TestClass> B = new StandardSet<>("B");
		B.put(new TestClass("y"));
		B.put(new TestClass("z"));

		java.util.Set<StandardSet<TestClass>> sets = new HashSet<>();
		sets.add(A);
		sets.add(B);

		StandardSet<TestClass> union = (StandardSet<TestClass>) Uniter.unite(sets);
		assertEquals("Expect union's size to be 3", union.size().getValue().intValue(), 3);
		assertTrue("Expect union's name to be A ∪ B or B ∪ A",
				"A ∪ B".equals(union.getName()) || "B ∪ A".equals(union.getName()));
		assertTrue("Expect union to contain x", union.contains("x"));
		assertTrue("Expect union to contain y", union.contains("y"));
		assertTrue("Expect union to contain z", union.contains("z"));
	}

	@Test
	public void testUnionWithInfiniteSet() throws Exception {
		ModifiableSet<Integer> X = new StandardSet<>("X");
		X.put(new maths.number.integer.Integer(1));
		assertTrue(X.containsValue(new Integer(1)));
		assertFalse(X.containsValue(new Integer(2)));

		Set<Integer> ℙ = new PrimeNumberSet("ℙ");
		assertFalse(ℙ.containsValue(new Integer(1)));
		assertTrue(ℙ.containsValue(new Integer(2)));

		java.util.Set<Set<Integer>> sets = new HashSet<>();
		sets.add(X);
		sets.add(ℙ);

		Set<Integer> union = Uniter.unite(sets);

		assertTrue(union.containsValue(new Integer(1)));
		assertTrue(union.containsValue(new Integer(2)));
	}

	@Test
	public void testUnionOfInfiniteSets() throws Exception {
		Set<Integer> ℤ = new IntegerSet("ℤ");
		assertTrue(ℤ.containsValue(new Integer(1)));
		assertTrue(ℤ.containsValue(new Integer(2)));

		Set<Integer> ℙ = new PrimeNumberSet("ℙ");
		assertFalse(ℙ.containsValue(new Integer(1)));
		assertTrue(ℙ.containsValue(new Integer(2)));

		java.util.Set<Set<Integer>> sets = new HashSet<>();
		sets.add(ℤ);
		sets.add(ℙ);

		Set<Integer> union = Uniter.unite(sets);

		assertTrue(union.containsValue(new Integer(1)));
		assertTrue(union.containsValue(new Integer(2)));
	}
}
