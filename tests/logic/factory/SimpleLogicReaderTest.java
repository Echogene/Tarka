package logic.factory;

import logic.StandardReader;
import logic.TestClass;
import logic.TestClassUniverse;
import logic.function.Function;
import logic.function.evaluable.predicate.equality.EqualityPredicateFactory;
import logic.function.evaluable.predicate.membership.MembershipPredicateFactory;
import logic.function.evaluable.statements.binary.BinaryConnectiveFactory;
import logic.function.evaluable.statements.binary.BinaryStatement;
import logic.function.evaluable.statements.quantified.standard.QuantifiedStatement;
import logic.function.evaluable.statements.quantified.standard.QuantifierFactory;
import logic.function.evaluable.statements.unary.UnaryConnectiveFactory;
import logic.function.evaluable.statements.unary.UnaryStatement;
import logic.function.identity.EvaluableIdentityFunction;
import logic.function.identity.MemberIdentityFunction;
import logic.function.identity.SetIdentityFunction;
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
				EqualityPredicateFactory.createElement("x", "y"),
				binaryConnectiveFactory.createElement("∧"),
				MembershipPredicateFactory.createElement("x", "X")
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

//	@Test
//	public void testRead() throws Exception {
//		Function<TestClass, ?> expected;
//		Function<?, ?> actual;
//
//		actual = reader.read("(∃!y(¬∀x∊(V ∪ W)(((z where z is x)∊X)∨(y∊(⋃ X Y {a b c})))))");
//		System.out.println(actual);
//	}
}
