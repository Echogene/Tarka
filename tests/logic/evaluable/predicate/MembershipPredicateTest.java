package logic.evaluable.predicate;

import logic.TestClass;
import logic.TestClassUniverse;
import logic.evaluable.predicate.membership.MembershipPredicate;
import logic.set.ModifiableSet;
import logic.set.Set;
import logic.set.finite.StandardSet;
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
		StandardSet<TestClass> universalSet = new StandardSet<>("universalSet");
		universalSet.put(x);
		universalSet.put(y);

		ModifiableSet<TestClass> X = new StandardSet<>("X");
		X.put(x);
		ModifiableSet<TestClass> Y = new StandardSet<>("Y");
		Y.put(y);
		StandardSet<Set<TestClass>> sets = new StandardSet<>("sets");
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

		StandardSet<TestClass> variables = new StandardSet<>("variables");
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
