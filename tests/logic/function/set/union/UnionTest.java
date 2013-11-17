package logic.function.set.union;

import logic.TestClass;
import logic.TestClassUniverse;
import logic.set.ModifiableSet;
import logic.set.Set;
import logic.set.finite.StandardSet;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Steven Weston
 */
public class UnionTest {
	@Test
	public void testEvaluate() throws Exception {
		TestClassUniverse universe = new TestClassUniverse();
		StandardSet<Set<TestClass>> sets = new StandardSet<>("sets");

		ModifiableSet<TestClass> A = new StandardSet<>("A");
		TestClass a = new TestClass("a");
		A.put(a);
		ModifiableSet<TestClass> B = new StandardSet<>("B");
		TestClass b = new TestClass("b");
		B.put(b);
		ModifiableSet<TestClass> C = new StandardSet<>("C");
		TestClass c = new TestClass("c");
		C.put(c);

		sets.put(A);
		sets.put(B);
		sets.put(C);

		universe.setUniversalSetOfSets(sets);

		Union<TestClass> union = MultaryUnionFactory.createElement("A", "B");
		Set<TestClass> unionResult = union.evaluate(universe);

		assertTrue(unionResult.containsValue(a));
		assertTrue(unionResult.containsValue(b));
		assertFalse(unionResult.containsValue(c));
	}
}
