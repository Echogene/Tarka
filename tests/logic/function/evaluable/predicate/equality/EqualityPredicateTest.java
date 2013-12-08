package logic.function.evaluable.predicate.equality;

import logic.TestClass;
import logic.TestClassModel;
import logic.TestClassUniverse;
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
		TestClassModel model = new TestClassModel();
		TestClassUniverse universe = model.getUniverse();
		TestClass x = new TestClass("x");
		TestClass y = new TestClass("y");

		StandardSet<Object> variables = new StandardSet<>("variables");
		variables.put(x);
		variables.put(y);

		universe.setVariables(variables);

		EqualityPredicate<TestClass> predicate = EqualityPredicateFactory.createElement("x", "x");
		assertTrue("Expect x = x to evaluate to true", predicate.evaluate(model));
		predicate = EqualityPredicateFactory.createElement("x", "y");
		assertFalse("Expect x = y to evaluate to false", predicate.evaluate(model));
	}

	private void testEvaluateOnVariableOverriding() throws Exception {
		TestClass x = new TestClass("x");
		TestClass y = new TestClass("y");
		EqualityPredicate<TestClass> predicate = EqualityPredicateFactory.createElement("x", "y");
		StandardSet<Object> variables;

		TestClassModel model = new TestClassModel();
		TestClassUniverse universe = model.getUniverse();
		variables = new StandardSet<>("variables");
		variables.put("y", x);
		universe.setVariables(variables);

		StandardSet<TestClass> universalSet = new StandardSet<>("universalSet");
		universalSet.put(x);
		universalSet.put(y);
		universe.setUniversalSet(universalSet);

		assertTrue("Expect x = y to evaluate to true when y represents x", predicate.evaluate(model));
	}

	private void testEvaluateOnTwoUniverseMembersMappedToTheSameElement() throws Exception {
		TestClass x = new TestClass("x");
		EqualityPredicate<TestClass> predicate = EqualityPredicateFactory.createElement("x", "y");

		TestClassModel model = new TestClassModel();
		TestClassUniverse universe = model.getUniverse();

		StandardSet<TestClass> universalSet = new StandardSet<>("universalSet");
		universalSet.put(x);
		universalSet.put("y", x);
		universe.setUniversalSet(universalSet);

		assertTrue("Expect x = y to evaluate to true when y represents x", predicate.evaluate(model));
	}
}
