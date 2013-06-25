package logic.evaluable.predicate;

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
public class MembershipPredicateTest {
	@Test
	public void testEvaluate() throws Exception {
		TestClass x = new TestClass("x");
		TestClass y = new TestClass("y");
		NamedSet<TestClass> universalSet = new NamedSet<>("universalSet");
		universalSet.put(x);
		universalSet.put(y);

		Set<TestClass> X = new NamedSet<>("X");
		X.put(x);
		Set<TestClass> Y = new NamedSet<>("Y");
		Y.put(y);
		Set<Set<TestClass>> sets = new NamedSet<>("sets");
		sets.put(X);
		sets.put(Y);

		TestClassUniverse universe = new TestClassUniverse();
		universe.setUniversalSet(universalSet);
		universe.setUniversalSetOfSets(sets);

		MembershipPredicate<TestClass> predicate;
		predicate = new MembershipPredicate<>("x", "X");
		assertTrue("Expect x to be in X", predicate.evaluate(universe));
		predicate = new MembershipPredicate<>("y", "X");
		assertFalse("Expect y to not be in X", predicate.evaluate(universe));
		predicate = new MembershipPredicate<>("x", "Y");
		assertFalse("Expect x to not be in Y", predicate.evaluate(universe));

		NamedSet<TestClass> variables = new NamedSet<>("variables");
		variables.put("x", y);
		universe.setVariables(variables);
		predicate = new MembershipPredicate<>("x", "X");
		assertFalse("Expect x to not be in X", predicate.evaluate(universe));
		predicate = new MembershipPredicate<>("y", "X");
		assertFalse("Expect y to not be in X", predicate.evaluate(universe));
		predicate = new MembershipPredicate<>("x", "Y");
		assertTrue("Expect x to be in Y", predicate.evaluate(universe));
		predicate = new MembershipPredicate<>("y", "Y");
		assertTrue("Expect x to be in Y", predicate.evaluate(universe));
	}
}
