package logic.function.evaluable.statements.unary;

/**
 * @author Steven Weston
 */
public class UnaryStatementTest {
	/* todo: fix me
	public static final StandardSet<TestClass> EMPTY_SET = new StandardSet<>("∅");
	public static final LogicalConstant<TestClass> TAUTOLOGY = new LogicalConstant<>(true);
	public static final LogicalConstant<TestClass> CONTRADICTION = new LogicalConstant<>(false);

	@Test
	public void testEvaluateWithUniverse() throws Exception {
		TestClassUniverse universe = new TestClassUniverse();

		UnaryConnective c;
		UnaryStatement<TestClass> statement;

		testAllTheThings(TAUTOLOGY, CONTRADICTION, universe);

		MembershipPredicate<TestClass> membershipPredicate = MembershipPredicateFactory.createElement("x", "set");
		StandardSet<TestClass> set = new StandardSet<>("set");
		((ModifiableSet<Set<TestClass>>) universe.getUniversalSetOfSets()).put(set);
		TestClass x = new TestClass("x");
		((ModifiableSet<TestClass>) universe.getUniversalSet()).put(x);

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
	}*/
}
