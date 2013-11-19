package logic.function.evaluable.statements.binary;

/**
 * @author Steven Weston
 */
public class BinaryStatementTest {
	/* todo: redo this
	public static final LogicalConstant<TestClass> CONTRADICTION = new LogicalConstant<>(false);
	public static final LogicalConstant<TestClass> TAUTOLOGY = new LogicalConstant<>(true);

	@Test
	public void testEvaluateWithUniverse() throws Exception {
		TestClassUniverse universe = new TestClassUniverse();
		testAllTheThings(TAUTOLOGY, CONTRADICTION, universe);

		MembershipPredicate<TestClass> membershipPredicate = MembershipPredicateFactory.createElement("x", "set");
		StandardSet<TestClass> set = new StandardSet<>("set");
		((ModifiableSet<Set<TestClass>>) universe.getUniversalSetOfSets()).put(set);
		TestClass x = new TestClass("x");
		((ModifiableSet<TestClass>) universe.getUniversalSet()).put(x);
		testAllTheThings(TAUTOLOGY, membershipPredicate, universe);

		set.put(x);
		testAllTheThings(membershipPredicate, CONTRADICTION, universe);
	}

	private void testAllTheThings(Evaluable<TestClass> t, Evaluable<TestClass> f, Universe<TestClass> o) throws Exception {
		assertBinaryStatementStates(f, new BinaryConnective(OR),          t, o, false, true,  true,  true);
		assertBinaryStatementStates(f, new BinaryConnective(NOR),         t, o, true,  false, false, false);
		assertBinaryStatementStates(f, new BinaryConnective(AND),         t, o, false, false, false, true);
		assertBinaryStatementStates(f, new BinaryConnective(NAND),        t, o, true,  true,  true,  false);
		assertBinaryStatementStates(f, new BinaryConnective(IMPLIES),     t, o, true,  true,  false, true);
		assertBinaryStatementStates(f, new BinaryConnective(NIMPLIES),    t, o, false, false, true,  false);
		assertBinaryStatementStates(f, new BinaryConnective(IFF),         t, o, true,  false, false, true);
		assertBinaryStatementStates(f, new BinaryConnective(NIFF),        t, o, false, true,  true,  false);
		assertBinaryStatementStates(f, new BinaryConnective(IMPLIED_BY),  t, o, true,  false, true,  true);
		assertBinaryStatementStates(f, new BinaryConnective(NIMPLIED_BY), t, o, false, true,  false, false);
	}

	private void assertBinaryStatementStates(
			Evaluable<TestClass> contradiction,
			BinaryConnective connective,
			Evaluable<TestClass> tautology,
			Universe<TestClass> setOrUniverse,
			boolean state1,
			boolean state2,
			boolean state3,
			boolean state4) throws Exception {

		assertBinaryStatement(contradiction, connective, contradiction, setOrUniverse, state1);
		assertBinaryStatement(contradiction, connective, tautology, setOrUniverse, state2);
		assertBinaryStatement(tautology, connective, contradiction, setOrUniverse, state3);
		assertBinaryStatement(tautology, connective, tautology, setOrUniverse, state4);
	}

	private void assertBinaryStatement(
			Evaluable<TestClass> firstEvaluable,
			BinaryConnective connective,
			Evaluable<TestClass> secondEvaluable,
			Universe<TestClass> setOrUniverse,
			boolean assertTrue)
			throws Exception {
		BinaryStatement<TestClass> statement;
		statement = new BinaryStatement<>(firstEvaluable, connective, secondEvaluable);
		TestClassUniverse universe = (TestClassUniverse) setOrUniverse;
		if (assertTrue) {
			assertTrue(statement.evaluate(universe));
		} else {
			assertFalse(statement.evaluate(universe));
		}
	}*/
}
