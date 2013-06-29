package logic.factory;

import logic.TestClass;
import logic.evaluable.Evaluable;
import logic.evaluable.constants.LogicalConstantFactory;
import logic.evaluable.predicate.EqualityPredicate;
import logic.evaluable.predicate.EqualityPredicateFactory;
import logic.evaluable.predicate.MembershipPredicateFactory;
import logic.evaluable.statements.binary.BinaryConnective;
import logic.evaluable.statements.binary.BinaryConnectiveFactory;
import logic.evaluable.statements.binary.BinaryStatement;
import logic.evaluable.statements.binary.BinaryStatementFactory;
import logic.evaluable.statements.quantified.QuantifiedStatement;
import logic.evaluable.statements.quantified.QuantifiedStatementFactory;
import logic.evaluable.statements.quantified.Quantifier;
import logic.evaluable.statements.quantified.QuantifierFactory;
import logic.evaluable.statements.unary.UnaryConnectiveFactory;
import logic.evaluable.statements.unary.UnaryStatementFactory;
import logic.function.Function;
import logic.function.factory.FunctionFactory;
import logic.function.reflexive.IdentityFunction;
import logic.function.reflexive.IdentityFunctionFactory;
import logic.function.reflexiveset.identity.SetIdentityFunctionFactory;
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
public class SimpleLogicEvaluatorImplTest {
	private static SimpleLogicLexerImpl lexer;
	private static SimpleLogicParserImpl parser;
	private static SimpleLogicEvaluatorImpl evaluator;

	private static ParseTree tree;
	private static List<Token> tokens;

	private static BinaryConnectiveFactory binaryConnectiveFactory;
	private static UnaryConnectiveFactory unaryConnectiveFactory;
	private static QuantifierFactory quantifierFactory;

	private static List<FunctionFactory<?, ?>> factories;

	@BeforeClass
	public static void setUp() {
		factories = new ArrayList<>();
		factories.add(new EqualityPredicateFactory<>());
		factories.add(new MembershipPredicateFactory<>());
		factories.add(new SetIdentityFunctionFactory<TestClass>());
		factories.add(new IdentityFunctionFactory<TestClass>());
		factories.add(new BinaryStatementFactory<>());
		factories.add(new LogicalConstantFactory<>());
		factories.add(new UnaryStatementFactory<>());
		factories.add(new QuantifiedStatementFactory<>());

		lexer     = new SimpleLogicLexerImpl();
		parser    = new SimpleLogicParserImpl();
		evaluator = new SimpleLogicEvaluatorImpl(factories);

		binaryConnectiveFactory = new BinaryConnectiveFactory();
		unaryConnectiveFactory = new UnaryConnectiveFactory();
		quantifierFactory = new QuantifierFactory();
	}

	@Test
	public void testEvaluate() throws Exception {
		Function<?, ?> expected;
		Function<?, ?> actual;
		IdentityFunction<TestClass> function1;
		IdentityFunction<TestClass> function2;
		IdentityFunction<TestClass> function3;
		IdentityFunction<TestClass> function4;
		Evaluable<TestClass> evaluable1;
		Evaluable<TestClass> evaluable2;

		expected = new EqualityPredicate<>("x","y");
		tokens = lexer.tokeniseString("(x=y)");
		tree = parser.parseTokens(tokens);
		actual = evaluator.evaluate(tree);
		assertEquals("Expect created equality predicated to be equal to the evaluated one", expected, actual);

		function1 = new IdentityFunction<>("x");
		expected = new EqualityPredicate<>(function1, "y");
		tokens = lexer.tokeniseString("((id x)=y)");
		tree = parser.parseTokens(tokens);
		actual = evaluator.evaluate(tree);
		assertEquals("Expect created equality predicated to be equal to the evaluated one", expected, actual);

		function1 = new IdentityFunction<>("x");
		expected = new IdentityFunction<>(function1);
		tokens = lexer.tokeniseString("(id (id x))");
		tree = parser.parseTokens(tokens);
		actual = evaluator.evaluate(tree);
		assertEquals("Expect created equality predicated to be equal to the evaluated one", expected, actual);

		function1 = new IdentityFunction<>("x");
		function2 = new IdentityFunction<>(function1);
		expected = new EqualityPredicate<>(function2, "y");
		tokens = lexer.tokeniseString("((id (id x))=y)");
		tree = parser.parseTokens(tokens);
		actual = evaluator.evaluate(tree);
		assertEquals("Expect created equality predicated to be equal to the evaluated one", expected, actual);

		function1 = new IdentityFunction<>("x");
		function2 = new IdentityFunction<>("y");
		expected = new EqualityPredicate<>(function1, function2);
		tokens = lexer.tokeniseString("((id x)=(id y))");
		tree = parser.parseTokens(tokens);
		actual = evaluator.evaluate(tree);
		assertEquals("Expect created equality predicated to be equal to the evaluated one", expected, actual);

		function1 = new IdentityFunction<>("x");
		function2 = new IdentityFunction<>(function1);
		function3 = new IdentityFunction<>("y");
		function4 = new IdentityFunction<>(function3);
		expected = new EqualityPredicate<>(function2, function4);
		tokens = lexer.tokeniseString("((id (id x))=(id (id y)))");
		tree = parser.parseTokens(tokens);
		actual = evaluator.evaluate(tree);
		assertEquals("Expect created equality predicated to be equal to the evaluated one", expected, actual);

		evaluable1 = new EqualityPredicate<>("x","y");
		evaluable2 = new EqualityPredicate<>("y","z");
		expected = new BinaryStatement<>(
				evaluable1,
				(BinaryConnective) binaryConnectiveFactory.createElement("∧"),
				evaluable2);
		tokens = lexer.tokeniseString("((x=y)∧(y=z))");
		tree = parser.parseTokens(tokens);
		actual = evaluator.evaluate(tree);
		assertEquals("Expect created equality predicated to be equal to the evaluated one", expected, actual);

		evaluable1 = new EqualityPredicate<>("x","y");
		expected = new QuantifiedStatement<>(
				(Quantifier) quantifierFactory.createElement("∀"),
				"x",
				evaluable1);
		tokens = lexer.tokeniseString("(∀x(x=y))");
		tree = parser.parseTokens(tokens);
		actual = evaluator.evaluate(tree);
		assertEquals("Expect created equality predicated to be equal to the evaluated one", expected, actual);
	}
}
