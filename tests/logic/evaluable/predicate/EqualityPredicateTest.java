package logic.evaluable.predicate;

import logic.TestClass;
import logic.TestClassUniverse;
import logic.evaluable.predicate.equality.EqualityPredicate;
import logic.evaluable.predicate.equality.EqualityPredicateFactory;
import logic.set.finite.StandardSet;
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

	private void testEvaluateOnSimpleExpressions() throws Exception {
		TestClassUniverse universe = new TestClassUniverse();
		TestClass x = new TestClass("x");
		TestClass y = new TestClass("y");

		StandardSet<TestClass> variables = new StandardSet<>("variables");
		variables.put(x);
		variables.put(y);

		universe.setVariables(variables);

		EqualityPredicate<TestClass> predicate = EqualityPredicateFactory.createElement("x", "x");
		assertTrue("Expect x = x to evaluate to true", predicate.evaluate(universe));
		predicate = EqualityPredicateFactory.createElement("x", "y");
		assertFalse("Expect x = y to evaluate to false", predicate.evaluate(universe));
	}

	private void testEvaluateOnVariableOverriding() throws Exception {
		TestClass x = new TestClass("x");
		TestClass y = new TestClass("y");
		EqualityPredicate<TestClass> predicate = EqualityPredicateFactory.createElement("x", "y");
		StandardSet<TestClass> variables;

		TestClassUniverse universe = new TestClassUniverse();
		variables = new StandardSet<>("variables");
		variables.put("y", x);
		universe.setVariables(variables);

		StandardSet<TestClass> universalSet = new StandardSet<>("universalSet");
		universalSet.put(x);
		universalSet.put(y);
		universe.setUniversalSet(universalSet);

		assertTrue("Expect x = y to evaluate to true when y represents x", predicate.evaluate(universe));
	}

	private void testEvaluateOnTwoUniverseMembersMappedToTheSameElement() throws Exception {
		TestClass x = new TestClass("x");
		EqualityPredicate<TestClass> predicate = EqualityPredicateFactory.createElement("x", "y");

		TestClassUniverse universe = new TestClassUniverse();

		StandardSet<TestClass> universalSet = new StandardSet<>("universalSet");
		universalSet.put(x);
		universalSet.put("y", x);
		universe.setUniversalSet(universalSet);

		assertTrue("Expect x = y to evaluate to true when y represents x", predicate.evaluate(universe));
	}
}
