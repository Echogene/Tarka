package logic.function.set.complex;

import logic.TestClass;
import logic.TestClassUniverse;
import logic.function.FunctionTest;
import logic.function.evaluable.predicate.membership.MembershipPredicateFactory;
import logic.set.Set;
import logic.set.filtered.FiniteFilteredSet;
import logic.set.finite.FiniteSet;
import logic.set.finite.StandardSet;
import logic.set.infinite.InfiniteSet;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Steven Weston
 */
public class ComplexSetTest extends FunctionTest<TestClass, TestClassUniverse, ComplexSet<TestClass>> {

	public ComplexSetTest() {
		super(new TestClassUniverse());
		universe.put("x");
		universe.put("y");
		universe.put("z");
		universe.putSet("X", "x", "y", "z");
		universe.putSet("Y", "y");
		StandardSet<Set<TestClass>> sets = (StandardSet<Set<TestClass>>) universe.getUniversalSetOfSets();
		sets.put("Z", new InfiniteSet<TestClass>() {
			@Override
			public boolean containsValue(TestClass thing) {
				return true;
			}

			@Override
			public String getName() {
				return "Z";
			}
		});
	}

	@Test
	public void testEvaluateFiniteSet() throws Exception {
		function = new ComplexSet<>(
				"a",
				(Set<TestClass>) universe.get("X"),
				MembershipPredicateFactory.createElement("a", "Y")
		);

		Set<TestClass> filteredSet = function.evaluate(universe);

		assertTrue(filteredSet instanceof FiniteSet);
		assertTrue(filteredSet instanceof FiniteFilteredSet);
		assertFalse(filteredSet.containsValue(new TestClass("x")));
		assertTrue(filteredSet.containsValue(new TestClass("y")));
		assertFalse(filteredSet.containsValue(new TestClass("z")));
	}

	@Test
	public void testEvaluateNonFiniteSet() throws Exception {
		function = new ComplexSet<>(
				"a",
				(Set<TestClass>) universe.get("Z"),
				MembershipPredicateFactory.createElement("a", "Y")
		);

		Set<TestClass> filteredSet = function.evaluate(universe);

		assertFalse(filteredSet.containsValue(new TestClass("x")));
		assertTrue(filteredSet.containsValue(new TestClass("y")));
		assertFalse(filteredSet.containsValue(new TestClass("z")));
	}
}
