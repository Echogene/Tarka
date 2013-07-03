package logic.evaluable.statements.binary;

import logic.TestClass;
import logic.evaluable.Evaluable;
import logic.evaluable.predicate.EqualityPredicateFactory;
import logic.factory.SimpleLogicLexerImpl;
import logic.function.Function;
import org.junit.BeforeClass;
import org.junit.Test;
import reading.lexing.Token;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Steven Weston
 */
public class BinaryStatementFactoryTest {
	private static List<Token> tokens;
	private static List<Function<?, ?>> functions;
	private static SimpleLogicLexerImpl lexer;
	private static BinaryStatementFactory<TestClass> factory;
	private static BinaryConnectiveFactory connectiveFactory;
	private static EqualityPredicateFactory<TestClass> predicateFactory;

	@BeforeClass
	public static void setUp() {
		lexer   = new SimpleLogicLexerImpl();
		factory = new BinaryStatementFactory<>();
		connectiveFactory = new BinaryConnectiveFactory();
		predicateFactory = new EqualityPredicateFactory<>();
	}

	@Test
	public void testCreateElement() throws Exception {
		Function<TestClass, Boolean> expected;
		Function<TestClass, Boolean> actual;
		Function<TestClass, Boolean> evaluable1;
		Function<TestClass, Boolean> evaluable2;

		evaluable1 = EqualityPredicateFactory.createElement("x", "y");
		evaluable2 = EqualityPredicateFactory.createElement("y", "z");
		expected = new BinaryStatement<>(
				(Evaluable<TestClass>) evaluable1,
				(BinaryConnective) connectiveFactory.createElement("∨"),
				(Evaluable<TestClass>) evaluable2);
		setUpTokens("()∨()");
		setUpFunctions("x=y", "y=z");
		actual = factory.createElement(tokens, functions);
		assertEquals("Expect created binary statement to be equal to the factory-built one", expected, actual);
	}

	private void setUpFunctions(String equalityPredicateString1, String equalityPredicateString2) throws Exception {
		functions = new ArrayList<>(2);
		if (equalityPredicateString1.isEmpty()) {
			functions.add(null);
		} else {
			functions.add(predicateFactory.createElement(lexer.tokeniseString(equalityPredicateString1)));
		}
		if (equalityPredicateString2.isEmpty()) {
			functions.add(null);
		} else {
			functions.add(predicateFactory.createElement(lexer.tokeniseString(equalityPredicateString2)));
		}
	}

	private void setUpTokens(String tokenString) throws Exception {
		tokens = lexer.tokeniseString(tokenString);
	}
}
