package logic.evaluable.statements.unary;

import logic.TestClass;
import logic.TestClassUniverse;
import logic.evaluable.Evaluable;
import logic.evaluable.constants.LogicalConstant;
import logic.evaluable.predicate.membership.MembershipPredicate;
import logic.model.universe.Universe;
import logic.set.NamedSet;
import org.junit.Test;

import static logic.evaluable.statements.unary.UnaryConnective.UnaryConnectiveType.EMPTY;
import static logic.evaluable.statements.unary.UnaryConnective.UnaryConnectiveType.NEGATION;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Steven Weston
 */
public class UnaryStatementTest {

	public static final NamedSet<TestClass> EMPTY_SET = new NamedSet<>("∅");
	public static final LogicalConstant<TestClass> TAUTOLOGY = new LogicalConstant<>(true);
	public static final LogicalConstant<TestClass> CONTRADICTION = new LogicalConstant<>(false);

	@Test
	public void testEvaluateWithUniverse() throws Exception {
		TestClassUniverse universe = new TestClassUniverse();

		UnaryConnective c;
		UnaryStatement<TestClass> statement;

		testAllTheThings(TAUTOLOGY, CONTRADICTION, universe);

		MembershipPredicate<TestClass> membershipPredicate = new MembershipPredicate<>("x", "set");
		NamedSet<TestClass> set = new NamedSet<>("set");
		universe.getUniversalSetOfSets().put(set);
		TestClass x = new TestClass("x");
		universe.getUniversalSet().put(x);

		c = new UnaryConnective(EMPTY);
		statement = new UnaryStatement<>(c, membershipPredicate);
		assertFalse(statement.evaluate(universe));
		c = new UnaryConnective(NEGATION);
		statement = new UnaryStatement<>(c, membershipPredicate);
		assertTrue(statement.evaluate(universe));

		set.put(x);
		c = new UnaryConnective(EMPTY);
		statement = new UnaryStatement<>(c, membershipPredicate);
		assertTrue(statement.evaluate(universe));
		c = new UnaryConnective(NEGATION);
		statement = new UnaryStatement<>(c, membershipPredicate);
		assertFalse(statement.evaluate(universe));
	}

	private void testAllTheThings(LogicalConstant<TestClass> tautology, LogicalConstant<TestClass> contradiction, Universe<TestClass> setOrUniverse) throws Exception {
		UnaryConnective c;
		c = new UnaryConnective(EMPTY);
		assertUnaryStatement(tautology, c, setOrUniverse, true);
		assertUnaryStatement(contradiction, c, setOrUniverse, false);

		c = new UnaryConnective(NEGATION);
		assertUnaryStatement(tautology, c, setOrUniverse, false);
		assertUnaryStatement(contradiction, c, setOrUniverse, true);
	}

	private void assertUnaryStatement(
		Evaluable<TestClass> evaluable,
		UnaryConnective connective,
		Universe<TestClass> setOrUniverse,
		boolean assertTrue
	) throws Exception {

		UnaryStatement<TestClass> statement;
		statement = new UnaryStatement<>(connective, evaluable);
		TestClassUniverse universe = (TestClassUniverse) setOrUniverse;
		if (assertTrue) {
			assertTrue(statement.evaluate(universe));
		} else {
			assertFalse(statement.evaluate(universe));
		}
	}
}
