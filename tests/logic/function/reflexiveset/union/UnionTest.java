package logic.function.reflexiveset.union;

import logic.TestClass;
import logic.TestClassUniverse;
import logic.set.NamedSet;
import logic.set.Set;
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
		Set<Set<TestClass>> sets = new NamedSet<>("sets");

		Set<TestClass> A = new NamedSet<>("A");
		TestClass a = new TestClass("a");
		A.put(a);
		Set<TestClass> B = new NamedSet<>("B");
		TestClass b = new TestClass("b");
		B.put(b);
		Set<TestClass> C = new NamedSet<>("C");
		TestClass c = new TestClass("c");
		C.put(c);

		sets.put(A);
		sets.put(B);
		sets.put(C);

		universe.setUniversalSetOfSets(sets);

		Union<TestClass> union = UnionFactory.createElement("A", "B");
		Set<TestClass> unionResult = union.evaluate(universe);

		assertTrue(unionResult.containsValue(a));
		assertTrue(unionResult.containsValue(b));
		assertFalse(unionResult.containsValue(c));
	}
}
