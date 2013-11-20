package logic.factory;

import logic.TestClass;
import logic.TestClassUniverse;
import logic.function.Function;
import logic.function.evaluable.Evaluable;
import logic.function.evaluable.predicate.equality.EqualityPredicateFactory;
import logic.function.evaluable.predicate.membership.MembershipPredicateFactory;
import logic.function.evaluable.statements.binary.BinaryConnectiveFactory;
import logic.function.evaluable.statements.binary.BinaryStatement;
import logic.function.evaluable.statements.binary.BinaryStatementFactory;
import logic.function.evaluable.statements.quantified.standard.QuantifiedStatement;
import logic.function.evaluable.statements.quantified.standard.QuantifiedStatementFactory;
import logic.function.evaluable.statements.quantified.standard.QuantifierFactory;
import logic.function.evaluable.statements.unary.UnaryConnectiveFactory;
import logic.function.evaluable.statements.unary.UnaryStatementFactory;
import logic.function.factory.FunctionFactory;
import logic.function.identity.IdentityFunctionFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import reading.lexing.Token;
import reading.parsing.ParseTree;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Steven Weston
 */
public class SimpleLogicEvaluatorTest {
	private static SimpleLogicLexer lexer;
	private static SimpleLogicParser parser;
	private static SimpleLogicEvaluator evaluator;

	private static ParseTree tree;
	private static List<Token> tokens;

	private static BinaryConnectiveFactory binaryConnectiveFactory;
	private static UnaryConnectiveFactory unaryConnectiveFactory;
	private static QuantifierFactory quantifierFactory;

	private static List<FunctionFactory<TestClass, ?, ?>> factories;

	private static TestClassUniverse universe;

	@BeforeClass
	public static void setUp() {
		factories = new ArrayList<>();
		Class<TestClass> universeType = TestClass.class;
		factories.add(new EqualityPredicateFactory<>(universeType));
		factories.add(new MembershipPredicateFactory<>(universeType));
		factories.add(new IdentityFunctionFactory<>(universeType));
		factories.add(new BinaryStatementFactory<>(universeType));
		factories.add(new UnaryStatementFactory<>(universeType));
		factories.add(new QuantifiedStatementFactory<>(universeType));

		universe = new TestClassUniverse();
		universe.put("x");
		universe.put("y");
		universe.put("z");

		lexer     = new SimpleLogicLexer();
		parser    = new SimpleLogicParser();
		evaluator = new SimpleLogicEvaluator<>(factories, universe);

		binaryConnectiveFactory = new BinaryConnectiveFactory();
		unaryConnectiveFactory = new UnaryConnectiveFactory();
		quantifierFactory = new QuantifierFactory();
	}

	@Test
	public void testEvaluate() throws Exception {
		Function<?, ?> expected;
		Function<?, ?> actual;
		Evaluable<TestClass> evaluable1;
		Evaluable<TestClass> evaluable2;

		expected = EqualityPredicateFactory.createElement("x", "y");
		tokens = lexer.tokeniseString("(x=y)");
		tree = parser.parseTokens(tokens);
		actual = evaluator.evaluate(tree);
		assertEquals("Expect created equality predicated to be equal to the evaluated one", expected, actual);

		evaluable1 = EqualityPredicateFactory.createElement("x", "y");
		evaluable2 = EqualityPredicateFactory.createElement("y", "z");
		expected = new BinaryStatement<>(
				evaluable1,
				binaryConnectiveFactory.createElement("∧"),
				evaluable2);
		tokens = lexer.tokeniseString("((x=y)∧(y=z))");
		tree = parser.parseTokens(tokens);
		actual = evaluator.evaluate(tree);
		assertEquals("Expect created equality predicated to be equal to the evaluated one", expected, actual);

		evaluable1 = EqualityPredicateFactory.createElement("a", "y");
		expected = new QuantifiedStatement<>(
				quantifierFactory.createElement("∀"),
				"a",
				evaluable1);
		tokens = lexer.tokeniseString("(∀a(a=y))");
		tree = parser.parseTokens(tokens);
		actual = evaluator.evaluate(tree);
		assertEquals("Expect created equality predicated to be equal to the evaluated one", expected, actual);
	}
}
