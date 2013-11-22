package logic.factory;

import logic.StandardReader;
import logic.TestClass;
import logic.TestClassUniverse;
import logic.function.Function;
import logic.function.assignment.EvaluableAssignment;
import logic.function.assignment.ReflexiveAssignment;
import logic.function.assignment.SetAssignment;
import logic.function.evaluable.predicate.equality.EqualityPredicateFactory;
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
import logic.function.set.simple.SimpleSetFactory;
import logic.function.set.union.AbstractUnionFactory;
import logic.function.voidfunction.definition.member.MemberDefinition;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Steven Weston
 */
public class SimpleLogicReaderTest {

	private static SimpleLogicReader reader;
	private static TestClassUniverse universe;
	private static BinaryConnectiveFactory binaryConnectiveFactory = new BinaryConnectiveFactory();
	private static UnaryConnectiveFactory unaryConnectiveFactory = new UnaryConnectiveFactory();
	private static QuantifierFactory quantifierFactory = new QuantifierFactory();

	@BeforeClass
	public static void setUp() {
		universe = new TestClassUniverse();
		universe.put("x");
		universe.put("y");
		universe.putSet("X", "x");
		universe.putSet("Y", "y");
		reader = StandardReader.createStandardReader(universe);
	}

	@Test
	public void testCreateMemberIdentityFunction() throws Exception {
		Function<TestClass, ?> expected;
		Function<?, ?> actual;

		expected = new MemberIdentityFunction<TestClass>("x");
		actual = reader.read("(x)");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateNestedMemberIdentityFunction() throws Exception {
		Function<TestClass, ?> expected;
		Function<?, ?> actual;

		expected = new MemberIdentityFunction<>(new MemberIdentityFunction<TestClass>("x"));
		actual = reader.read("((x))");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateSetIdentityFunction() throws Exception {
		Function<TestClass, ?> expected;
		Function<?, ?> actual;

		expected = new SetIdentityFunction<TestClass>("X");
		actual = reader.read("(X)");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateNestedSetIdentityFunction() throws Exception {
		Function<TestClass, ?> expected;
		Function<?, ?> actual;

		expected = new SetIdentityFunction<TestClass>(new SetIdentityFunction<TestClass>("X"));
		actual = reader.read("((X))");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateEvaluableIdentityFunction() throws Exception {
		Function<TestClass, ?> expected;
		Function<?, ?> actual;

		expected = new EvaluableIdentityFunction<TestClass>("⊤");
		actual = reader.read("(⊤)");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateNestedEvaluableIdentityFunction() throws Exception {
		Function<TestClass, ?> expected;
		Function<?, ?> actual;

		expected = new EvaluableIdentityFunction<TestClass>(new EvaluableIdentityFunction<TestClass>("⊥"));
		actual = reader.read("((⊥))");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateEqualityPredicate() throws Exception {
		Function<TestClass, ?> expected;
		Function<?, ?> actual;

		expected = EqualityPredicateFactory.createElement("x", "y");
		actual = reader.read("(x = y)");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateMembershipPredicate() throws Exception {
		Function<TestClass, ?> expected;
		Function<?, ?> actual;

		expected = MembershipPredicateFactory.createElement("x", "X");
		actual = reader.read("(x ∊ X)");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateBinaryStatement() throws Exception {
		Function<TestClass, ?> expected;
		Function<?, ?> actual;

		expected = new BinaryStatement<TestClass>(
				new EvaluableIdentityFunction<TestClass>("⊤"),
				binaryConnectiveFactory.createElement("∨"),
				new EvaluableIdentityFunction<TestClass>("⊥")
		);
		actual = reader.read("(⊤ ∨ ⊥)");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateNestedBinaryStatement() throws Exception {
		Function<TestClass, ?> expected;
		Function<?, ?> actual;

		expected = new BinaryStatement<TestClass>(
				EqualityPredicateFactory.<TestClass>createElement("x", "y"),
				binaryConnectiveFactory.createElement("∧"),
				MembershipPredicateFactory.<TestClass>createElement("x", "X")
		);
		actual = reader.read("((x = y) ∧ (x ∊ X))");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateUnaryStatement() throws Exception {
		Function<TestClass, ?> expected;
		Function<?, ?> actual;

		expected = new UnaryStatement<TestClass>(
				unaryConnectiveFactory.createElement("¬"),
				new EvaluableIdentityFunction<TestClass>("⊤")
		);
		actual = reader.read("(¬⊤)");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateNestedUnaryStatement() throws Exception {
		Function<TestClass, ?> expected;
		Function<?, ?> actual;

		expected = new UnaryStatement<TestClass>(
				unaryConnectiveFactory.createElement("¬"),
				EqualityPredicateFactory.createElement("x", "y")
		);
		actual = reader.read("(¬(x = y))");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateQuantifiedStatement() throws Exception {
		Function<TestClass, ?> expected;
		Function<?, ?> actual;

		expected = new QuantifiedStatement<TestClass>(
				quantifierFactory.createElement("∀"),
				"a",
				new EvaluableIdentityFunction<TestClass>("⊤")
		);
		actual = reader.read("(∀a ⊤)");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateNestedQuantifiedStatement() throws Exception {
		Function<TestClass, ?> expected;
		Function<?, ?> actual;

		expected = new QuantifiedStatement<TestClass>(
				quantifierFactory.createElement("∀"),
				"a",
				EqualityPredicateFactory.createElement("a", "x")
		);
		actual = reader.read("(∀a(a = x))");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateBinaryUnion() throws Exception {
		Function<TestClass, ?> expected;
		Function<?, ?> actual;

		expected = AbstractUnionFactory.createElement("X", "Y");
		actual = reader.read("(X ∪ Y)");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateNestedBinaryUnion() throws Exception {
		Function<TestClass, ?> expected;
		Function<?, ?> actual;

		expected = AbstractUnionFactory.createElement(
				AbstractUnionFactory.<TestClass>createElement("X", "Y"),
				AbstractUnionFactory.<TestClass>createElement("Y", "X")
		);
		actual = reader.read("((X ∪ Y) ∪ (Y ∪ X))");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateMultaryUnion() throws Exception {
		Function<TestClass, ?> expected;
		Function<?, ?> actual;

		expected = AbstractUnionFactory.createElement("X", "Y");
		actual = reader.read("(⋃ X Y)");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateNestedMultaryUnion() throws Exception {
		Function<TestClass, ?> expected;
		Function<?, ?> actual;

		expected = AbstractUnionFactory.createElement(
				AbstractUnionFactory.<TestClass>createElement("X", "Y"),
				AbstractUnionFactory.<TestClass>createElement("Y", "X")
		);
		actual = reader.read("(⋃ (X ∪ Y) (Y ∪ X))");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateReflexiveAssignment() throws Exception {
		Function<TestClass, ?> expected;
		Function<?, ?> actual;

		expected = new ReflexiveAssignment<TestClass>(
				new MemberIdentityFunction<TestClass>("a"),
				"a",
				new MemberIdentityFunction<TestClass>("x")
		);
		actual = reader.read("(a where a is x)");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateHeadNestedReflexiveAssignment() throws Exception {
		Function<TestClass, ?> expected;
		Function<?, ?> actual;

		expected = new ReflexiveAssignment<TestClass>(
				new ReflexiveAssignment<TestClass>(
						new MemberIdentityFunction<TestClass>("b"),
						"b",
						new MemberIdentityFunction<TestClass>("a")
				),
				"a",
				new MemberIdentityFunction<TestClass>("x")
		);
		actual = reader.read("((b where b is a) where a is x)");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateTailNestedReflexiveAssignment() throws Exception {
		Function<TestClass, ?> expected;
		Function<?, ?> actual;

		expected = new ReflexiveAssignment<TestClass>(
				new MemberIdentityFunction<TestClass>("a"),
				"a",
				new ReflexiveAssignment<TestClass>(
						new MemberIdentityFunction<TestClass>("b"),
						"b",
						new MemberIdentityFunction<TestClass>("x")
				)
		);
		actual = reader.read("(a where a is (b where b is x))");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateHeadAndTailNestedReflexiveAssignment() throws Exception {
		Function<TestClass, ?> expected;
		Function<?, ?> actual;

		expected = new ReflexiveAssignment<TestClass>(
				new ReflexiveAssignment<TestClass>(
						new MemberIdentityFunction<TestClass>("a"),
						"a",
						new MemberIdentityFunction<TestClass>("b")
				),
				"b",
				new ReflexiveAssignment<TestClass>(
						new MemberIdentityFunction<TestClass>("c"),
						"c",
						new MemberIdentityFunction<TestClass>("x")
				)
		);
		actual = reader.read("((a where a is b) where b is (c where c is x))");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateSetAssignment() throws Exception {
		Function<TestClass, ?> expected;
		Function<?, ?> actual;

		expected = new SetAssignment<TestClass>(
				new SetIdentityFunction<TestClass>("a"),
				"a",
				new SetIdentityFunction<TestClass>("X")
		);
		actual = reader.read("(a where a is X)");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateHeadNestedSetAssignment() throws Exception {
		Function<TestClass, ?> expected;
		Function<?, ?> actual;

		expected = new SetAssignment<TestClass>(
				new SetAssignment<TestClass>(
						new SetIdentityFunction<TestClass>("b"),
						"b",
						new SetIdentityFunction<TestClass>("a")
				),
				"a",
				new SetIdentityFunction<TestClass>("X")
		);
		actual = reader.read("((b where b is a) where a is X)");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateTailNestedSetAssignment() throws Exception {
		Function<TestClass, ?> expected;
		Function<?, ?> actual;

		expected = new SetAssignment<TestClass>(
				new SetIdentityFunction<TestClass>("a"),
				"a",
				new SetAssignment<TestClass>(
						new SetIdentityFunction<TestClass>("b"),
						"b",
						new SetIdentityFunction<TestClass>("X")
				)
		);
		actual = reader.read("(a where a is (b where b is X))");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateHeadAndTailNestedSetAssignment() throws Exception {
		Function<TestClass, ?> expected;
		Function<?, ?> actual;

		expected = new SetAssignment<TestClass>(
				new SetAssignment<TestClass>(
						new SetIdentityFunction<TestClass>("a"),
						"a",
						new SetIdentityFunction<TestClass>("b")
				),
				"b",
				new SetAssignment<TestClass>(
						new SetIdentityFunction<TestClass>("c"),
						"c",
						new SetIdentityFunction<TestClass>("X")
				)
		);
		actual = reader.read("((a where a is b) where b is (c where c is X))");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateEvaluableAssignment() throws Exception {
		Function<TestClass, ?> expected;
		Function<?, ?> actual;

		expected = new EvaluableAssignment<TestClass>(
				new EvaluableIdentityFunction< TestClass >("a"),
				"a",
				new EvaluableIdentityFunction<TestClass>("⊤")
		);
		actual = reader.read("(a where a is ⊤)");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateHeadNestedEvaluableAssignment() throws Exception {
		Function<TestClass, ?> expected;
		Function<?, ?> actual;

		expected = new EvaluableAssignment<TestClass>(
				new EvaluableAssignment<TestClass>(
						new EvaluableIdentityFunction<TestClass>("b"),
						"b",
						new EvaluableIdentityFunction<TestClass>("a")
				),
				"a",
				new EvaluableIdentityFunction<TestClass>("⊤")
		);
		actual = reader.read("((b where b is a) where a is ⊤)");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateTailNestedEvaluableAssignment() throws Exception {
		Function<TestClass, ?> expected;
		Function<?, ?> actual;

		expected = new EvaluableAssignment<TestClass>(
				new EvaluableIdentityFunction<TestClass>("a"),
				"a",
				new EvaluableAssignment<TestClass>(
						new EvaluableIdentityFunction<TestClass>("b"),
						"b",
						new EvaluableIdentityFunction<TestClass>("⊤")
				)
		);
		actual = reader.read("(a where a is (b where b is ⊤))");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateHeadAndTailNestedEvaluableAssignment() throws Exception {
		Function<TestClass, ?> expected;
		Function<?, ?> actual;

		expected = new EvaluableAssignment<TestClass>(
				new EvaluableAssignment<TestClass>(
						new EvaluableIdentityFunction<TestClass>("a"),
						"a",
						new EvaluableIdentityFunction<TestClass>("b")
				),
				"b",
				new EvaluableAssignment<TestClass>(
						new EvaluableIdentityFunction<TestClass>("c"),
						"c",
						new EvaluableIdentityFunction<TestClass>("⊤")
				)
		);
		actual = reader.read("((a where a is b) where b is (c where c is ⊤))");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateMixedAssignment() throws Exception {
		Function<TestClass, ?> expected;
		Function<?, ?> actual;

		expected = new EvaluableAssignment<TestClass>(
				EqualityPredicateFactory.<TestClass>createElement("a",  "x"),
				"a",
				new MemberIdentityFunction<TestClass>("x")
		);
		actual = reader.read("((a = x) where a is x)");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateRestrictedQuantifiedStatement() throws Exception {
		Function<TestClass, ?> expected;
		Function<?, ?> actual;

		expected = new RestrictedQuantifiedStatement<TestClass>(
				quantifierFactory.createElement("∀"),
				"a",
				new SetIdentityFunction<TestClass>("X"),
				new EvaluableIdentityFunction<TestClass>("⊤")
		);
		actual = reader.read("(∀a ∊ X ⊤)");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateNestedRestrictedQuantifiedStatement() throws Exception {
		Function<TestClass, ?> expected;
		Function<?, ?> actual;

		expected = new RestrictedQuantifiedStatement<TestClass>(
				quantifierFactory.createElement("∀"),
				"a",
				new SetIdentityFunction<TestClass>("X"),
				EqualityPredicateFactory.createElement("a", "x")
		);
		actual = reader.read("(∀a ∊ X (a = x))");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateMemberDefinition() throws Exception {
		Function<TestClass, ?> expected;
		Function<?, ?> actual;

		expected = new MemberDefinition<TestClass>(
				"a",
				new MemberIdentityFunction<TestClass>("x")
		);
		actual = reader.read("(a ≔ x)");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateNestedMemberDefinition() throws Exception {
		Function<TestClass, ?> expected;
		Function<?, ?> actual;

		expected = new MemberDefinition<TestClass>(
				"a",
				new ReflexiveAssignment<TestClass>(
						new MemberIdentityFunction<TestClass>("b"),
						"b",
						new MemberIdentityFunction<TestClass>("x")
				)
		);
		actual = reader.read("(a ≔ (b where b is x))");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateSimpleSet() throws Exception {
		Function<TestClass, ?> expected;
		Function<?, ?> actual;

		expected = SimpleSetFactory.<TestClass>createElement("x", "y");
		actual = reader.read("{x y}");
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateNestedSimpleSet() throws Exception {
		Function<TestClass, ?> expected;
		Function<?, ?> actual;

		expected = SimpleSetFactory.<TestClass>createElement(
				new MemberIdentityFunction<TestClass>("x"),
				new ReflexiveAssignment<TestClass>(
						new MemberIdentityFunction<TestClass>("a"),
						"a",
						new MemberIdentityFunction<TestClass>("y")
				)
		);
		actual = reader.read("{x (a where a is y)}");
		assertEquals(expected, actual);
	}


//	@Test
//	public void testRead() throws Exception {
//		Function<TestClass, ?> expected;
//		Function<?, ?> actual;
//
//		actual = reader.read("(∃!y(¬∀x∊(V ∪ W)(((z where z is x)∊X)∨(y∊(⋃ X Y {a b c})))))");
//		System.out.println(actual);
//	}
}
