package logic.evaluable.predicate;

import logic.TestClass;
import logic.TestClassUniverse;
import logic.function.ParameterNotFoundException;
import logic.set.NamedSet;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Steven Weston
 */
public class EqualityPredicateTest {
	@Test
	public void testEvaluate() throws Exception {
		testEvaluateOnSimpleExpressions();
		testEvaluateOnVariableOverriding();
		testEvaluateOnTwoUniverseMembersMappedToTheSameElement();
	}

	private void testEvaluateOnSimpleExpressions() throws ParameterNotFoundException {
		TestClass x = new TestClass("x");
		TestClass y = new TestClass("y");

		NamedSet<TestClass> variables = new NamedSet<>("variables");
		variables.put(x);
		variables.put(y);

		EqualityPredicate<TestClass> predicate = EqualityPredicateFactory.createElement("x", "x");
		assertTrue("Expect x = x to evaluate to true", predicate.evaluate(variables));
		predicate = EqualityPredicateFactory.createElement("x", "y");
		assertFalse("Expect x = y to evaluate to false", predicate.evaluate(variables));
	}

	private void testEvaluateOnVariableOverriding() throws Exception {
		TestClass x = new TestClass("x");
		TestClass y = new TestClass("y");
		EqualityPredicate<TestClass> predicate = EqualityPredicateFactory.createElement("x", "y");
		NamedSet<TestClass> variables;

		TestClassUniverse universe = new TestClassUniverse();
		variables = new NamedSet<>("variables");
		variables.put("y", x);
		universe.setVariables(variables);

		NamedSet<TestClass> universalSet = new NamedSet<>("universalSet");
		universalSet.put(x);
		universalSet.put(y);
		universe.setUniversalSet(universalSet);

		assertTrue("Expect x = y to evaluate to true when y represents x", predicate.evaluate(universe));
	}

	private void testEvaluateOnTwoUniverseMembersMappedToTheSameElement() throws Exception {
		TestClass x = new TestClass("x");
		EqualityPredicate<TestClass> predicate = EqualityPredicateFactory.createElement("x", "y");

		TestClassUniverse universe = new TestClassUniverse();

		NamedSet<TestClass> universalSet = new NamedSet<>("universalSet");
		universalSet.put(x);
		universalSet.put("y", x);
		universe.setUniversalSet(universalSet);

		assertTrue("Expect x = y to evaluate to true when y represents x", predicate.evaluate(universe));
	}
}
