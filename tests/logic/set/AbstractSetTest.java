package logic.set;

import logic.TestClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Steven Weston
 */
public class AbstractSetTest {
	@Test
	public void testUnion() throws Exception {
		NamedSet<TestClass> A = new NamedSet<>("A");
		A.put(new TestClass("x"));
		A.put(new TestClass("y"));

		NamedSet<TestClass> B = new NamedSet<>("B");
		B.put(new TestClass("y"));
		B.put(new TestClass("z"));

		NamedSet<NamedSet<TestClass>> sets = new NamedSet<>("sets");
		sets.put(A);
		sets.put(B);

		Set<TestClass> union = AbstractSet.union(sets);
		assertEquals("Expect union's size to be 3", union.size(), 3);
		assertTrue("Expect union's name to be ⋃ A B or ⋃ B A",
				"⋃ A B".equals(union.getName()) || "⋃ B A".equals(union.getName()));
		assertTrue("Expect union to contain x", union.contains("x"));
		assertTrue("Expect union to contain y", union.contains("y"));
		assertTrue("Expect union to contain z", union.contains("z"));
	}
}
