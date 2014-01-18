package logic.function.evaluable.statements.unary;

import logic.TestClass;
import logic.TestClassModel;
import logic.TestClassUniverse;
import logic.function.evaluable.Evaluable;
import logic.function.evaluable.predicate.membership.MembershipPredicate;
import logic.function.evaluable.predicate.membership.MembershipPredicateFactory;
import logic.function.identity.EvaluableIdentityFunction;
import logic.set.ModifiableSet;
import logic.set.Set;
import logic.set.finite.StandardSet;
import org.junit.Test;

import static logic.function.evaluable.statements.unary.UnaryConnective.UnaryConnectiveType.EMPTY;
import static logic.function.evaluable.statements.unary.UnaryConnective.UnaryConnectiveType.NEGATION;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Steven Weston
 */
public class UnaryStatementTest {

	public static final StandardSet<TestClass> EMPTY_SET = new StandardSet<>("∅");
	private static final EvaluableIdentityFunction<TestClass> TAUTOLOGY = new EvaluableIdentityFunction<>(true);
	private static final EvaluableIdentityFunction<TestClass> CONTRADICTION = new EvaluableIdentityFunction<>(false);

	@Test
	public void testEvaluateWithUniverse() throws Exception {
		TestClassModel model = new TestClassModel();
		TestClassUniverse universe = model.getUniverse();

		UnaryConnective c;
		UnaryStatement<TestClass> statement;

		testAllTheThings(TAUTOLOGY, CONTRADICTION, model);

		MembershipPredicate<TestClass> membershipPredicate = MembershipPredicateFactory.createElement("x", "set");
		StandardSet<TestClass> set = new StandardSet<>("set");
		((ModifiableSet<Set<TestClass>>) universe.getUniversalSetOfSets()).put(set);
		TestClass x = new TestClass("x");
		((ModifiableSet<TestClass>) universe.getUniversalSet()).put(x);

		c = new UnaryConnective(EMPTY);
		statement = new UnaryStatement<>(c, membershipPredicate);
		assertFalse(statement.evaluate(model));
		c = new UnaryConnective(NEGATION);
		statement = new UnaryStatement<>(c, membershipPredicate);
		assertTrue(statement.evaluate(model));

		set.put(x);
		c = new UnaryConnective(EMPTY);
		statement = new UnaryStatement<>(c, membershipPredicate);
		assertTrue(statement.evaluate(model));
		c = new UnaryConnective(NEGATION);
		statement = new UnaryStatement<>(c, membershipPredicate);
		assertFalse(statement.evaluate(model));
	}

	private void testAllTheThings(Evaluable<TestClass, ?> tautology, Evaluable<TestClass, ?> contradiction, TestClassModel model) throws Exception {
		UnaryConnective c;
		c = new UnaryConnective(EMPTY);
		assertUnaryStatement(tautology, c, model, true);
		assertUnaryStatement(contradiction, c, model, false);

		c = new UnaryConnective(NEGATION);
		assertUnaryStatement(tautology, c, model, false);
		assertUnaryStatement(contradiction, c, model, true);
	}

	private void assertUnaryStatement(
		Evaluable<TestClass, ?> evaluable,
		UnaryConnective connective,
		TestClassModel model,
		boolean assertTrue
	) throws Exception {

		UnaryStatement<TestClass> statement;
		statement = new UnaryStatement<>(connective, evaluable);
		if (assertTrue) {
			assertTrue(statement.evaluate(model));
		} else {
			assertFalse(statement.evaluate(model));
		}
	}
}
