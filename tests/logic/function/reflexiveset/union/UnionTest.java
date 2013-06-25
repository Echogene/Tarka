package logic.function.reflexiveset.union;

import logic.TestClass;
import logic.set.NamedSet;
import logic.set.Set;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Steven Weston
 */
public class UnionTest {
	@Test
	public void testEvaluate() throws Exception {
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

		Union<TestClass> union = new Union<>(Arrays.asList("A", "B"), null);
		Set<TestClass> unionResult = union.evaluate(sets);

		assertTrue(unionResult.containsValue(a));
		assertTrue(unionResult.containsValue(b));
		assertFalse(unionResult.containsValue(c));
	}
}
