package logic.evaluable.statements.quantified.standard;

import logic.TestClass;
import logic.TestClassUniverse;
import logic.evaluable.Evaluable;
import logic.evaluable.predicate.equality.EqualityPredicateFactory;
import logic.evaluable.statements.unary.UnaryConnective;
import logic.evaluable.statements.unary.UnaryStatement;
import logic.set.NamedSet;
import org.junit.Test;

import static logic.evaluable.statements.quantified.standard.Quantifier.QuantifierType.EXISTS;
import static logic.evaluable.statements.quantified.standard.Quantifier.QuantifierType.FOR_ALL;
import static logic.evaluable.statements.unary.UnaryConnective.UnaryConnectiveType.NEGATION;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Steven Weston
 */
public class QuantifiedStatementTest {
	@Test
	public void testEvaluate() throws Exception {
		NamedSet<TestClass> universalSet = new NamedSet<>("universalSet");

		TestClassUniverse universe = new TestClassUniverse();
		universe.setUniversalSet(universalSet);

		Quantifier nestedQuantifier = new Quantifier(EXISTS);
		Evaluable<TestClass> evaluable = EqualityPredicateFactory.createElement("x", "y");
		QuantifiedStatement<TestClass> nestedStatement =
				new QuantifiedStatement<>(nestedQuantifier, "y", evaluable);
		// ∃y(x=y)

		Quantifier quantifier = new Quantifier(FOR_ALL);
		QuantifiedStatement<TestClass> statement =
				new QuantifiedStatement<>(quantifier, "x", nestedStatement);
		// ∀x∃y(x=y) -- This should be a tautology.

		universalSet.put(new TestClass("a"));
		universalSet.put(new TestClass("b"));
		universalSet.put(new TestClass("c"));

		assertTrue(statement.evaluate(universe));
	}

	@Test
	public void testEvaluateFalseOnSetWithOneElement() throws Exception {
		NamedSet<TestClass> universalSet = new NamedSet<>("universalSet");

		TestClassUniverse universe = new TestClassUniverse();
		universe.setUniversalSet(universalSet);

		Quantifier nestedQuantifier = new Quantifier(EXISTS);
		Evaluable<TestClass> innerEvaluable = EqualityPredicateFactory.createElement("x", "y");
		UnaryConnective connective = new UnaryConnective(NEGATION);
		Evaluable<TestClass> evaluable = new UnaryStatement<>(connective, innerEvaluable);
		QuantifiedStatement<TestClass> nestedStatement = new QuantifiedStatement<>(nestedQuantifier, "y", evaluable);
		// ∃y¬(x=y)

		Quantifier quantifier = new Quantifier(FOR_ALL);
		QuantifiedStatement<TestClass> statement = new QuantifiedStatement<>(quantifier, "x", nestedStatement);
		// ∀x∃y¬(x=y)

		assertTrue(statement.evaluate(universe));

		universalSet.put(new TestClass("a"));
		assertFalse(statement.evaluate(universe));

		universalSet.put(new TestClass("b"));
		assertTrue(statement.evaluate(universe));

		universalSet.put(new TestClass("c"));
		assertTrue(statement.evaluate(universe));
	}
}
