package logic.factory;

import logic.StandardReader;
import logic.TestClass;
import logic.TestClassUniverse;
import logic.function.assignment.EvaluableAssignment;
import logic.function.assignment.ReflexiveAssignment;
import logic.function.assignment.SetAssignment;
import logic.function.evaluable.predicate.equality.EqualityPredicate;
import logic.function.evaluable.predicate.equality.EqualityPredicateFactory;
import logic.function.evaluable.predicate.membership.MembershipPredicate;
import logic.function.evaluable.predicate.membership.MembershipPredicateFactory;
import logic.function.evaluable.statements.binary.BinaryConnectiveFactory;
import logic.function.evaluable.statements.binary.BinaryStatement;
import logic.function.evaluable.statements.quantified.restricted.RestrictedQuantifiedStatement;
import logic.function.evaluable.statements.quantified.standard.QuantifiedStatement;
import logic.function.evaluable.statements.quantified.standard.QuantifierFactory;
import logic.function.evaluable.statements.unary.UnaryConnectiveFactory;
import logic.function.evaluable.statements.unary.UnaryStatement;
import logic.function.identity.EvaluableIdentityFunction;
import logic.function.identity.MemberIdentityFunction;
import logic.function.identity.SetIdentityFunction;
import logic.function.ifelse.ReflexiveIfElse;
import logic.function.set.simple.SimpleSet;
import logic.function.set.simple.SimpleSetFactory;
import logic.function.set.union.AbstractUnionFactory;
import logic.function.set.union.Union;
import logic.function.voidfunction.definition.constant.MemberDefinition;
import org.junit.BeforeClass;
import org.junit.Test;
import reading.evaluating.EvaluatorException;
import util.CollectionUtils;
import util.ExceptionalRunnable;

import java.text.MessageFormat;

import static org.junit.Assert.*;

/**
 * @author Steven Weston
 */
@SuppressWarnings("Convert2Diamond")
public class SimpleLogicReaderTest {

	private static SimpleLogicReader<TestClass> reader;
	private static final BinaryConnectiveFactory binaryConnectiveFactory = new BinaryConnectiveFactory();
	private static final UnaryConnectiveFactory unaryConnectiveFactory = new UnaryConnectiveFactory();
	private static final QuantifierFactory quantifierFactory = new QuantifierFactory();

	@BeforeClass
	public static void setUp() {
		TestClassUniverse universe = new TestClassUniverse();
		universe.put("x");
		universe.put("y");
		universe.putSet("X", "x");
		universe.putSet("Y", "y");
		reader = StandardReader.createStandardReader(universe);
	}

	@Test
	public void testCreateMemberIdentityFunction() throws Exception {
		MemberIdentityFunction<TestClass> expected;
		MemberIdentityFunction<TestClass> actual;

		expected = new MemberIdentityFunction<>("x");
		actual = reader.read("(x)");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateNestedMemberIdentityFunction() throws Exception {
		MemberIdentityFunction<TestClass> expected;
		MemberIdentityFunction<TestClass> actual;

		expected = new MemberIdentityFunction<>(new MemberIdentityFunction<TestClass>("x"));
		actual = reader.read("((x))");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateSetIdentityFunction() throws Exception {
		SetIdentityFunction<TestClass> expected;
		SetIdentityFunction<TestClass> actual;

		expected = new SetIdentityFunction<>("X");
		actual = reader.read("(X)");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateNestedSetIdentityFunction() throws Exception {
		SetIdentityFunction<TestClass> expected;
		SetIdentityFunction<TestClass> actual;

		expected = new SetIdentityFunction<>(new SetIdentityFunction<TestClass>("X"));
		actual = reader.read("((X))");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateEvaluableIdentityFunction() throws Exception {
		EvaluableIdentityFunction<TestClass> expected;
		EvaluableIdentityFunction<TestClass> actual;

		expected = new EvaluableIdentityFunction<>("⊤");
		actual = reader.read("(⊤)");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateNestedEvaluableIdentityFunction() throws Exception {
		EvaluableIdentityFunction<TestClass> expected;
		EvaluableIdentityFunction<TestClass> actual;

		expected = new EvaluableIdentityFunction<>(new EvaluableIdentityFunction<TestClass>("⊥"));
		actual = reader.read("((⊥))");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateEqualityPredicate() throws Exception {
		EqualityPredicate<TestClass> expected;
		EqualityPredicate<TestClass> actual;

		expected = EqualityPredicateFactory.createElement("x", "y");
		actual = reader.read("(x = y)");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateMembershipPredicate() throws Exception {
		MembershipPredicate<TestClass> expected;
		MembershipPredicate<TestClass> actual;

		expected = MembershipPredicateFactory.createElement("x", "X");
		actual = reader.read("(x ∊ X)");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateBinaryStatement() throws Exception {
		BinaryStatement<TestClass> expected;
		BinaryStatement<TestClass> actual;

		expected = new BinaryStatement<TestClass>(
				new EvaluableIdentityFunction<>("⊤"),
				binaryConnectiveFactory.createElement("∨"),
				new EvaluableIdentityFunction<>("⊥")
		);
		actual = reader.read("(⊤ ∨ ⊥)");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateNestedBinaryStatement() throws Exception {
		BinaryStatement<TestClass> expected;
		BinaryStatement<TestClass> actual;

		expected = new BinaryStatement<>(
				EqualityPredicateFactory.<TestClass>createElement("x", "y"),
				binaryConnectiveFactory.createElement("∧"),
				MembershipPredicateFactory.<TestClass>createElement("x", "X")
		);
		actual = reader.read("((x = y) ∧ (x ∊ X))");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateUnaryStatement() throws Exception {
		UnaryStatement<TestClass> expected;
		UnaryStatement<TestClass> actual;

		expected = new UnaryStatement<>(
				unaryConnectiveFactory.createElement("¬"),
				new EvaluableIdentityFunction<TestClass>("⊤")
		);
		actual = reader.read("(¬⊤)");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateNestedUnaryStatement() throws Exception {
		UnaryStatement<TestClass> expected;
		UnaryStatement<TestClass> actual;

		expected = new UnaryStatement<>(
				unaryConnectiveFactory.createElement("¬"),
				EqualityPredicateFactory.createElement("x", "y")
		);
		actual = reader.read("(¬(x = y))");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateQuantifiedStatement() throws Exception {
		QuantifiedStatement<TestClass> expected;
		QuantifiedStatement<TestClass> actual;

		expected = new QuantifiedStatement<>(
				quantifierFactory.createElement("∀"),
				"a",
				new EvaluableIdentityFunction<TestClass>("⊤")
		);
		actual = reader.read("(∀a ⊤)");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateNestedQuantifiedStatement() throws Exception {
		QuantifiedStatement<TestClass> expected;
		QuantifiedStatement<TestClass> actual;

		expected = new QuantifiedStatement<>(
				quantifierFactory.createElement("∀"),
				"a",
				EqualityPredicateFactory.createElement("a", "x")
		);
		actual = reader.read("(∀a(a = x))");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateBinaryUnion() throws Exception {
		Union<TestClass> expected;
		Union<TestClass> actual;

		expected = AbstractUnionFactory.createElement("X", "Y");
		actual = reader.read("(X ∪ Y)");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateNestedBinaryUnion() throws Exception {
		Union<TestClass> expected;
		Union<TestClass> actual;

		expected = AbstractUnionFactory.createElement(
				AbstractUnionFactory.<TestClass>createElement("X", "Y"),
				AbstractUnionFactory.<TestClass>createElement("Y", "X")
		);
		actual = reader.read("((X ∪ Y) ∪ (Y ∪ X))");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateMultaryUnion() throws Exception {
		Union<TestClass> expected;
		Union<TestClass> actual;

		expected = AbstractUnionFactory.createElement("X", "Y");
		actual = reader.read("(⋃ X Y)");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateNestedMultaryUnion() throws Exception {
		Union<TestClass> expected;
		Union<TestClass> actual;

		expected = AbstractUnionFactory.createElement(
				AbstractUnionFactory.<TestClass>createElement("X", "Y"),
				AbstractUnionFactory.<TestClass>createElement("Y", "X")
		);
		actual = reader.read("(⋃ (X ∪ Y) (Y ∪ X))");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateReflexiveAssignment() throws Exception {
		ReflexiveAssignment<TestClass> expected;
		ReflexiveAssignment<TestClass> actual;

		expected = new ReflexiveAssignment<TestClass>(
				new MemberIdentityFunction<>("a"),
				"a",
				new MemberIdentityFunction<>("x")
		);
		actual = reader.read("(a where a is x)");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateHeadNestedReflexiveAssignment() throws Exception {
		ReflexiveAssignment<TestClass> expected;
		ReflexiveAssignment<TestClass> actual;

		expected = new ReflexiveAssignment<>(
				new ReflexiveAssignment<TestClass>(
						new MemberIdentityFunction<TestClass>("b"),
						"b",
						new MemberIdentityFunction<>("a")
				),
				"a",
				new MemberIdentityFunction<TestClass>("x")
		);
		actual = reader.read("((b where b is a) where a is x)");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateTailNestedReflexiveAssignment() throws Exception {
		ReflexiveAssignment<TestClass> expected;
		ReflexiveAssignment<TestClass> actual;

		expected = new ReflexiveAssignment<>(
				new MemberIdentityFunction<>("a"),
				"a",
				new ReflexiveAssignment<TestClass>(
						new MemberIdentityFunction<>("b"),
						"b",
						new MemberIdentityFunction<TestClass>("x")
				)
		);
		actual = reader.read("(a where a is (b where b is x))");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateHeadAndTailNestedReflexiveAssignment() throws Exception {
		ReflexiveAssignment<TestClass> expected;
		ReflexiveAssignment<TestClass> actual;

		expected = new ReflexiveAssignment<>(
				new ReflexiveAssignment<TestClass>(
						new MemberIdentityFunction<>("a"),
						"a",
						new MemberIdentityFunction<>("b")
				),
				"b",
				new ReflexiveAssignment<TestClass>(
						new MemberIdentityFunction<>("c"),
						"c",
						new MemberIdentityFunction<>("x")
				)
		);
		actual = reader.read("((a where a is b) where b is (c where c is x))");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateSetAssignment() throws Exception {
		SetAssignment<TestClass> expected;
		SetAssignment<TestClass> actual;

		expected = new SetAssignment<TestClass>(
				new SetIdentityFunction<>("a"),
				"a",
				new SetIdentityFunction<>("X")
		);
		actual = reader.read("(a where a is X)");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateHeadNestedSetAssignment() throws Exception {
		SetAssignment<TestClass> expected;
		SetAssignment<TestClass> actual;

		expected = new SetAssignment<>(
				new SetAssignment<TestClass>(
						new SetIdentityFunction<TestClass>("b"),
						"b",
						new SetIdentityFunction<>("a")
				),
				"a",
				new SetIdentityFunction<TestClass>("X")
		);
		actual = reader.read("((b where b is a) where a is X)");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateTailNestedSetAssignment() throws Exception {
		SetAssignment<TestClass> expected;
		SetAssignment<TestClass> actual;

		expected = new SetAssignment<>(
				new SetIdentityFunction<>("a"),
				"a",
				new SetAssignment<TestClass>(
						new SetIdentityFunction<TestClass>("b"),
						"b",
						new SetIdentityFunction<>("X")
				)
		);
		actual = reader.read("(a where a is (b where b is X))");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateHeadAndTailNestedSetAssignment() throws Exception {
		SetAssignment<TestClass> expected;
		SetAssignment<TestClass> actual;

		expected = new SetAssignment<>(
				new SetAssignment<TestClass>(
						new SetIdentityFunction<>("a"),
						"a",
						new SetIdentityFunction<>("b")
				),
				"b",
				new SetAssignment<TestClass>(
						new SetIdentityFunction<>("c"),
						"c",
						new SetIdentityFunction<>("X")
				)
		);
		actual = reader.read("((a where a is b) where b is (c where c is X))");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateEvaluableAssignment() throws Exception {
		EvaluableAssignment<TestClass> expected;
		EvaluableAssignment<TestClass> actual;

		expected = new EvaluableAssignment<TestClass>(
				new EvaluableIdentityFunction<>("a"),
				"a",
				new EvaluableIdentityFunction<>("⊤")
		);
		actual = reader.read("(a where a is ⊤)");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateHeadNestedEvaluableAssignment() throws Exception {
		EvaluableAssignment<TestClass> expected;
		EvaluableAssignment<TestClass> actual;

		expected = new EvaluableAssignment<>(
				new EvaluableAssignment<TestClass>(
						new EvaluableIdentityFunction<TestClass>("b"),
						"b",
						new EvaluableIdentityFunction<>("a")
				),
				"a",
				new EvaluableIdentityFunction<TestClass>("⊤")
		);
		actual = reader.read("((b where b is a) where a is ⊤)");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateTailNestedEvaluableAssignment() throws Exception {
		EvaluableAssignment<TestClass> expected;
		EvaluableAssignment<TestClass> actual;

		expected = new EvaluableAssignment<>(
				new EvaluableIdentityFunction<>("a"),
				"a",
				new EvaluableAssignment<TestClass>(
						new EvaluableIdentityFunction<TestClass>("b"),
						"b",
						new EvaluableIdentityFunction<>("⊤")
				)
		);
		actual = reader.read("(a where a is (b where b is ⊤))");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateHeadAndTailNestedEvaluableAssignment() throws Exception {
		EvaluableAssignment<TestClass> expected;
		EvaluableAssignment<TestClass> actual;

		expected = new EvaluableAssignment<>(
				new EvaluableAssignment<TestClass>(
						new EvaluableIdentityFunction<>("a"),
						"a",
						new EvaluableIdentityFunction<>("b")
				),
				"b",
				new EvaluableAssignment<TestClass>(
						new EvaluableIdentityFunction<>("c"),
						"c",
						new EvaluableIdentityFunction<>("⊤")
				)
		);
		actual = reader.read("((a where a is b) where b is (c where c is ⊤))");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateMixedAssignment() throws Exception {
		EvaluableAssignment<TestClass> expected;
		EvaluableAssignment<TestClass> actual;

		expected = new EvaluableAssignment<>(
				EqualityPredicateFactory.<TestClass>createElement("a",  "x"),
				"a",
				new MemberIdentityFunction<>("x")
		);
		actual = reader.read("((a = x) where a is x)");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateRestrictedQuantifiedStatement() throws Exception {
		RestrictedQuantifiedStatement<TestClass> expected;
		RestrictedQuantifiedStatement<TestClass> actual;

		expected = new RestrictedQuantifiedStatement<TestClass>(
				quantifierFactory.createElement("∀"),
				"a",
				new SetIdentityFunction<>("X"),
				new EvaluableIdentityFunction<>("⊤")
		);
		actual = reader.read("(∀a ∊ X ⊤)");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateNestedRestrictedQuantifiedStatement() throws Exception {
		RestrictedQuantifiedStatement<TestClass> expected;
		RestrictedQuantifiedStatement<TestClass> actual;

		expected = new RestrictedQuantifiedStatement<TestClass>(
				quantifierFactory.createElement("∀"),
				"a",
				new SetIdentityFunction<>("X"),
				EqualityPredicateFactory.createElement("a", "x")
		);
		actual = reader.read("(∀a ∊ X (a = x))");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateMemberDefinition() throws Exception {
		MemberDefinition<TestClass> expected;
		MemberDefinition<TestClass> actual;

		expected = new MemberDefinition<>(
				"a",
				new MemberIdentityFunction<TestClass>("x")
		);
		actual = reader.read("(a ≔ x)");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateNestedMemberDefinition() throws Exception {
		MemberDefinition<TestClass> expected;
		MemberDefinition<TestClass> actual;

		expected = new MemberDefinition<>(
				"a",
				new ReflexiveAssignment<TestClass>(
						new MemberIdentityFunction<>("b"),
						"b",
						new MemberIdentityFunction<>("x")
				)
		);
		actual = reader.read("(a ≔ (b where b is x))");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateSimpleSet() throws Exception {
		SimpleSet<TestClass> expected;
		SimpleSet<TestClass> actual;

		expected = SimpleSetFactory.createElement("x", "y");
		actual = reader.read("{x y}");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateNestedSimpleSet() throws Exception {
		SimpleSet<TestClass> expected;
		SimpleSet<TestClass> actual;

		expected = SimpleSetFactory.createElement(
				new MemberIdentityFunction<TestClass>("x"),
				new ReflexiveAssignment<TestClass>(
						new MemberIdentityFunction<>("a"),
						"a",
						new MemberIdentityFunction<>("y")
				)
		);
		actual = reader.read("{x (a where a is y)}");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateReflexiveIfElse() throws Exception {
		ReflexiveIfElse<TestClass> expected;
		ReflexiveIfElse<TestClass> actual;

		expected = new ReflexiveIfElse<TestClass>(
				new EvaluableIdentityFunction<>("⊤"),
				new MemberIdentityFunction<>("x"),
				new MemberIdentityFunction<>("y")
		);
		actual = reader.read("(x if ⊤ otherwise y)");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateHeadNestedReflexiveIfElse() throws Exception {
		ReflexiveIfElse<TestClass> expected;
		ReflexiveIfElse<TestClass> actual;

		expected = new ReflexiveIfElse<>(
				new EvaluableIdentityFunction<TestClass>("⊤"),
				new ReflexiveAssignment<TestClass>(
						new MemberIdentityFunction<TestClass>("a"),
						"a",
						new MemberIdentityFunction<>("x")
				),
				new MemberIdentityFunction<TestClass>("y")
		);
		actual = reader.read("((a where a is x) if ⊤ otherwise y)");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateMidNestedReflexiveIfElse() throws Exception {
		ReflexiveIfElse<TestClass> expected;
		ReflexiveIfElse<TestClass> actual;

		expected = new ReflexiveIfElse<TestClass>(
				EqualityPredicateFactory.createElement("x", "y"),
				new MemberIdentityFunction<>("x"),
				new MemberIdentityFunction<>("y")
		);
		actual = reader.read("(x if (x = y) otherwise y)");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateTailNestedReflexiveIfElse() throws Exception {
		ReflexiveIfElse<TestClass> expected;
		ReflexiveIfElse<TestClass> actual;

		expected = new ReflexiveIfElse<>(
				new EvaluableIdentityFunction<>("⊤"),
				new MemberIdentityFunction<TestClass>("x"),
				new ReflexiveAssignment<TestClass>(
						new MemberIdentityFunction<TestClass>("a"),
						"a",
						new MemberIdentityFunction<TestClass>("y")
				)
		);
		actual = reader.read("(x if ⊤ otherwise (a where a is y))");
		assertEquals(expected, actual);
	}

	@Test
	public void testExceptionIsThrownIfVariableNotFoundInUniverseForIdentityFunction() throws Exception {
		testEvaluatorExceptionIsThrown(() -> reader.read("(a)"));
	}

	@Test
	public void testExceptionIsThrownIfVariableNotFoundInUniverseForEqualityPredicate() throws Exception {
		testEvaluatorExceptionIsThrown(() -> reader.read("(a = x)"));
	}

	@Test
	public void testExceptionIsThrownIfVariableNotFoundInUniverseForEqualityPredicate2() throws Exception {
		testEvaluatorExceptionIsThrown(() -> reader.read("(x = a)"));
	}

	@Test
	public void testExceptionIsThrownIfVariableNotFoundInUniverseForMembershipPredicate() throws Exception {
		testEvaluatorExceptionIsThrown(() -> reader.read("(x ∊ A)"));
	}

	@Test
	public void testExceptionIsThrownIfVariableNotFoundInUniverseForMembershipPredicate2() throws Exception {
		testEvaluatorExceptionIsThrown(() -> reader.read("(a ∊ X)"));
	}

	@Test
	public void testExceptionIsThrownIfVariableNotFoundInUniverseForBinaryStatement() throws Exception {
		testEvaluatorExceptionIsThrown(() -> reader.read("(a ∨ ⊤)"));
	}

	@Test
	public void testExceptionIsThrownIfVariableNotFoundInUniverseForBinaryStatement2() throws Exception {
		testEvaluatorExceptionIsThrown(() -> reader.read("(⊥ ∨ a)"));
	}

	@Test
	public void testExceptionIsThrownIfVariableNotFoundInUniverseForUnaryStatement() throws Exception {
		testEvaluatorExceptionIsThrown(() -> reader.read("(¬a)"));
	}

	@Test
	public void testExceptionIsThrownIfVariableNotFoundInUniverseForQuantifiedStatement() throws Exception {
		testEvaluatorExceptionIsThrown(() -> reader.read("(∀x a)"));
	}

	@Test
	public void testExceptionIsThrownIfVariableNotFoundInUniverseForBinaryUnion() throws Exception {
		testEvaluatorExceptionIsThrown(() -> reader.read("(A ∪ X)"));
	}

	@Test
	public void testExceptionIsThrownIfVariableNotFoundInUniverseForBinaryUnion2() throws Exception {
		testEvaluatorExceptionIsThrown(() -> reader.read("(X ∪ A)"));
	}

	@Test
	public void testExceptionIsThrownIfVariableNotFoundInUniverseForMultaryUnion() throws Exception {
		testEvaluatorExceptionIsThrown(() -> reader.read("(⋃ A X)"));
	}

	@Test
	public void testExceptionIsThrownIfVariableNotFoundInUniverseForMultaryUnion2() throws Exception {
		testEvaluatorExceptionIsThrown(() -> reader.read("(⋃ X A)"));
	}

	@Test
	public void testExceptionIsThrownIfVariableNotFoundInUniverseForAssignment() throws Exception {
		testEvaluatorExceptionIsThrown(() -> reader.read("(a where b is x)"));
	}

	@Test
	public void testExceptionIsThrownIfVariableNotFoundInUniverseForAssignment2() throws Exception {
		testEvaluatorExceptionIsThrown(() -> reader.read("(b where b is a)"));
	}

	@Test
	public void testExceptionIsThrownIfVariableNotFoundInUniverseForRestrictedQuantifiedStatement() throws Exception {
		testEvaluatorExceptionIsThrown(() -> reader.read("(∀x ∊ A ⊤)"));
	}

	@Test
	public void testExceptionIsThrownIfVariableNotFoundInUniverseForRestrictedQuantifiedStatement2() throws Exception {
		testEvaluatorExceptionIsThrown(() -> reader.read("(∀x ∊ X a)"));
	}

	@Test
	public void testExceptionIsThrownIfVariableNotFoundInUniverseForDefinition() throws Exception {
		testEvaluatorExceptionIsThrown(() -> reader.read("(b ≔ a)"));
	}

	@Test
	public void testExceptionIsThrownIfVariableNotFoundInUniverseForSimpleSet() throws Exception {
		testEvaluatorExceptionIsThrown(() -> reader.read("{a x}"));
	}

	@Test
	public void testExceptionIsThrownIfVariableNotFoundInUniverseForSimpleSet2() throws Exception {
		testEvaluatorExceptionIsThrown(() -> reader.read("{x a}"));
	}

	@Test
	public void testExceptionIsThrownIfVariableNotFoundInUniverseForIfElse() throws Exception {
		testEvaluatorExceptionIsThrown(() -> reader.read("(a if ⊤ otherwise x)"));
	}

	@Test
	public void testExceptionIsThrownIfVariableNotFoundInUniverseForIfElse2() throws Exception {
		testEvaluatorExceptionIsThrown(() -> reader.read("(y if a otherwise x)"));
	}

	@Test
	public void testExceptionIsThrownIfVariableNotFoundInUniverseForIfElse3() throws Exception {
		testEvaluatorExceptionIsThrown(() -> reader.read("(y if ⊤ otherwise a)"));
	}

	private void testEvaluatorExceptionIsThrown(ExceptionalRunnable runnable) {
		testExceptionIsThrown(runnable, EvaluatorException.class);
	}

	private void testExceptionIsThrown(ExceptionalRunnable runnable, Class<? extends Exception> exceptionClass) {
		try {
			runnable.run();
			fail("Should throw an exception.");
		} catch (Exception e) {
			assertTrue(
					MessageFormat.format(
							"Thrown exception should be a {0}.  It was {1}\n{2}",
							exceptionClass.getSimpleName(),
							e.getClass().getSimpleName(),
							CollectionUtils.arrayToString(e.getStackTrace(), "\n")
					),
					exceptionClass.isInstance(e)
			);
		}
	}
}
