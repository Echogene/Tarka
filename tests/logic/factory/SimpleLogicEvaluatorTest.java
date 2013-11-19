package logic.factory;

import logic.TestClass;
import logic.function.Function;
import logic.function.evaluable.Evaluable;
import logic.function.evaluable.predicate.equality.EqualityPredicate;
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
import logic.function.identity.MemberIdentityFunction;
import logic.model.universe.empty.EmptyUniverse;
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

	@BeforeClass
	public static void setUp() {
		factories = new ArrayList<>();
		factories.add(new EqualityPredicateFactory<>());
		factories.add(new MembershipPredicateFactory<>());
		factories.add(new IdentityFunctionFactory<>());
		factories.add(new BinaryStatementFactory<>());
		factories.add(new UnaryStatementFactory<>());
		factories.add(new QuantifiedStatementFactory<>());

		lexer     = new SimpleLogicLexer();
		parser    = new SimpleLogicParser();
		evaluator = new SimpleLogicEvaluator<>(factories, new EmptyUniverse<>(TestClass.class));

		binaryConnectiveFactory = new BinaryConnectiveFactory();
		unaryConnectiveFactory = new UnaryConnectiveFactory();
		quantifierFactory = new QuantifierFactory();
	}

	@Test
	public void testEvaluate() throws Exception {
		Function<?, ?> expected;
		Function<?, ?> actual;
		MemberIdentityFunction<TestClass> nestedX;
		MemberIdentityFunction<TestClass> nestedY;
		Evaluable<TestClass> evaluable1;
		Evaluable<TestClass> evaluable2;

		expected = EqualityPredicateFactory.createElement("x", "y");
		tokens = lexer.tokeniseString("(x=y)");
		tree = parser.parseTokens(tokens);
		actual = evaluator.evaluate(tree);
		assertEquals("Expect created equality predicated to be equal to the evaluated one", expected, actual);

		expected = EqualityPredicateFactory.createElement("x", "y");
		tokens = lexer.tokeniseString("((id x)=y)");
		tree = parser.parseTokens(tokens);
		actual = evaluator.evaluate(tree);
		assertEquals("Expect created equality predicated to be equal to the evaluated one", expected, actual);

		MemberIdentityFunction<TestClass> x = new MemberIdentityFunction<>("x");
		expected = new MemberIdentityFunction<>(x);
		tokens = lexer.tokeniseString("(id (id x))");
		tree = parser.parseTokens(tokens);
		actual = evaluator.evaluate(tree);
		assertEquals("Expect created equality predicated to be equal to the evaluated one", expected, actual);

		MemberIdentityFunction<TestClass> y = new MemberIdentityFunction<>("y");
		expected = new EqualityPredicate<>(new MemberIdentityFunction<>(x), y);
		tokens = lexer.tokeniseString("((id (id x))=y)");
		tree = parser.parseTokens(tokens);
		actual = evaluator.evaluate(tree);
		assertEquals("Expect created equality predicated to be equal to the evaluated one", expected, actual);

		expected = EqualityPredicateFactory.createElement("x", "y");
		tokens = lexer.tokeniseString("((id x)=(id y))");
		tree = parser.parseTokens(tokens);
		actual = evaluator.evaluate(tree);
		assertEquals("Expect created equality predicated to be equal to the evaluated one", expected, actual);

		nestedX = new MemberIdentityFunction<>(x);
		nestedY = new MemberIdentityFunction<>(y);
		expected = new EqualityPredicate<>(nestedX, nestedY);
		tokens = lexer.tokeniseString("((id (id x))=(id (id y)))");
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

		evaluable1 = EqualityPredicateFactory.createElement("x", "y");
		expected = new QuantifiedStatement<>(
				quantifierFactory.createElement("∀"),
				"x",
				evaluable1);
		tokens = lexer.tokeniseString("(∀x(x=y))");
		tree = parser.parseTokens(tokens);
		actual = evaluator.evaluate(tree);
		assertEquals("Expect created equality predicated to be equal to the evaluated one", expected, actual);
	}
}
