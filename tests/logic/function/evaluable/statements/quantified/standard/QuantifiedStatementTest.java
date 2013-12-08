package logic.function.evaluable.statements.quantified.standard;

import logic.TestClass;
import logic.TestClassModel;
import logic.TestClassUniverse;
import logic.function.evaluable.Evaluable;
import logic.function.evaluable.predicate.equality.EqualityPredicateFactory;
import logic.function.evaluable.statements.unary.UnaryConnective;
import logic.function.evaluable.statements.unary.UnaryStatement;
import logic.set.finite.StandardSet;
import org.junit.Test;

import static logic.function.evaluable.statements.quantified.standard.Quantifier.QuantifierType.EXISTS;
import static logic.function.evaluable.statements.quantified.standard.Quantifier.QuantifierType.FOR_ALL;
import static logic.function.evaluable.statements.unary.UnaryConnective.UnaryConnectiveType.NEGATION;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Steven Weston
 */
public class QuantifiedStatementTest {
	@Test
	public void testEvaluate() throws Exception {
		StandardSet<TestClass> universalSet = new StandardSet<>("universalSet");

		TestClassModel model = new TestClassModel();
		TestClassUniverse universe = model.getUniverse();
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

		assertTrue(statement.evaluate(model));
	}

	@Test
	public void testEvaluateFalseOnSetWithOneElement() throws Exception {
		StandardSet<TestClass> universalSet = new StandardSet<>("universalSet");

		TestClassModel model = new TestClassModel();
		TestClassUniverse universe = model.getUniverse();
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

		assertTrue(statement.evaluate(model));

		universalSet.put(new TestClass("a"));
		assertFalse(statement.evaluate(model));

		universalSet.put(new TestClass("b"));
		assertTrue(statement.evaluate(model));

		universalSet.put(new TestClass("c"));
		assertTrue(statement.evaluate(model));
	}
}
