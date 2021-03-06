package logic.function.set.complex;

import logic.StandardReader;
import logic.TestClass;
import logic.TestClassModel;
import logic.TestClassUniverse;
import logic.factory.SimpleLogicReader;
import logic.function.FunctionTest;
import logic.function.assignment.SetAssignment;
import logic.function.evaluable.predicate.membership.MembershipPredicateFactory;
import logic.function.identity.SetIdentityFunction;
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
public class ComplexSetTest extends FunctionTest<TestClass, TestClassUniverse, TestClassModel, ComplexSet<TestClass>> {

	private final SimpleLogicReader<TestClass> reader;

	public ComplexSetTest() {
		super(new TestClassModel());
		universe.put("x");
		universe.put("y");
		universe.put("z");
		universe.putSet("X", "x", "y", "z");
		universe.putSet("Y", "y");
		StandardSet<Set<TestClass>> sets = universe.getUniversalSetOfSets();
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
		model.setReader(StandardReader.createStandardReader(universe));
		reader = model.getReader();
	}

	@Test
	public void testEvaluateFiniteSet() throws Exception {
		function = new ComplexSet<>(
				"a",
				new SetIdentityFunction<TestClass>("X"),
				MembershipPredicateFactory.createElement("a", "Y")
		);

		Set<TestClass> filteredSet = function.evaluate(model);

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
				new SetIdentityFunction<TestClass>("Z"),
				MembershipPredicateFactory.createElement("a", "Y")
		);

		Set<TestClass> filteredSet = function.evaluate(model);

		assertFalse(filteredSet.containsValue(new TestClass("x")));
		assertTrue(filteredSet.containsValue(new TestClass("y")));
		assertFalse(filteredSet.containsValue(new TestClass("z")));
	}

	@Test
	public void testEvaluateWhenSuchThatClauseUsesVariable() throws Exception {
		SetAssignment<TestClass> function = reader.read("({a ∊ X | (a = b)} where b is x)");

		Set<TestClass> filteredSet = function.evaluate(model);

		assertTrue(filteredSet.containsValue(new TestClass("x")));
		assertFalse(filteredSet.containsValue(new TestClass("y")));
		assertFalse(filteredSet.containsValue(new TestClass("z")));
	}
}
