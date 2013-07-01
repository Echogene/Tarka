package logic.evaluable.predicate;

import logic.TestClass;
import logic.TestClassUniverse;
import logic.function.ParameterNotFoundException;
import logic.function.reflexive.IdentityFunction;
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

		IdentityFunction<TestClass> xFunction = new IdentityFunction<>("x");
		IdentityFunction<TestClass> yFunction = new IdentityFunction<>("y");
		EqualityPredicate<TestClass> predicate = new EqualityPredicate<>(xFunction, xFunction);
		assertTrue("Expect x = x to evaluate to true", predicate.evaluate(variables));
		predicate = new EqualityPredicate<>(xFunction, yFunction);
		assertFalse("Expect x = y to evaluate to false", predicate.evaluate(variables));
	}

	private void testEvaluateOnVariableOverriding() throws Exception {
		TestClass x = new TestClass("x");
		TestClass y = new TestClass("y");
		IdentityFunction<TestClass> xFunction = new IdentityFunction<>("x");
		IdentityFunction<TestClass> yFunction = new IdentityFunction<>("y");
		EqualityPredicate<TestClass> predicate = new EqualityPredicate<>(xFunction, yFunction);
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
		IdentityFunction<TestClass> yFunction = new IdentityFunction<>("y");
		IdentityFunction<TestClass> xFunction = new IdentityFunction<>("x");
		EqualityPredicate<TestClass> predicate = new EqualityPredicate<>(xFunction, yFunction);

		TestClassUniverse universe = new TestClassUniverse();

		NamedSet<TestClass> universalSet = new NamedSet<>("universalSet");
		universalSet.put(x);
		universalSet.put("y", x);
		universe.setUniversalSet(universalSet);

		assertTrue("Expect x = y to evaluate to true when y represents x", predicate.evaluate(universe));
	}
}
