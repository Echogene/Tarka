package logic.evaluable.statements.unary;

import logic.TestClass;
import logic.evaluable.Evaluable;
import logic.evaluable.predicate.equality.EqualityPredicateFactory;
import logic.factory.SimpleLogicLexerImpl;
import logic.function.Function;
import org.junit.BeforeClass;
import org.junit.Test;
import reading.lexing.Token;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Steven Weston
 */
public class UnaryStatementFactoryTest {
	private static List<Token> tokens;
	private static List<Function<?, ?>> functions;
	private static SimpleLogicLexerImpl lexer;
	private static UnaryStatementFactory<TestClass> factory;
	private static UnaryConnectiveFactory connectiveFactory;
	private static EqualityPredicateFactory<TestClass> predicateFactory;

	@BeforeClass
	public static void setUp() {
		lexer   = new SimpleLogicLexerImpl();
		factory = new UnaryStatementFactory<>();
		connectiveFactory = new UnaryConnectiveFactory();
		predicateFactory = new EqualityPredicateFactory<>();
	}

	@Test
	public void testCreateElement() throws Exception {
		Function<TestClass, Boolean> expected;
		Function<TestClass, Boolean> actual;
		Function<TestClass, Boolean> evaluable;

		evaluable = EqualityPredicateFactory.createElement("x", "y");
		expected = new UnaryStatement<>((Evaluable<TestClass>) evaluable);
		setUpTokens("()");
		setUpFunction("x = y");
		actual = factory.createElement(tokens, functions);
		assertEquals("Expect created unary statement to be equal to the factory-built one", expected, actual);

		evaluable = EqualityPredicateFactory.createElement("x", "y");
		expected = new UnaryStatement<>((UnaryConnective) connectiveFactory.createElement("¬"),
				(Evaluable<TestClass>) evaluable);
		setUpTokens("¬()");
		setUpFunction("x = y");
		actual = factory.createElement(tokens, functions);
		assertEquals("Expect created unary statement to be equal to the factory-built one", expected, actual);
	}

	@Test
	public void testMatchesTwoBrackets() throws Exception {
		setUpTokens("()");
		assertTrue("Expect two brackets to match", factory.matchesTwoBrackets(tokens));

		setUpTokens("x)");
		assertFalse("Expect bad bracket to not match", factory.matchesTwoBrackets(tokens));

		setUpTokens("(x");
		assertFalse("Expect bad bracket to not match", factory.matchesTwoBrackets(tokens));
	}

	@Test
	public void testMatchesStandardTokens() throws Exception {
		setUpTokens("¬()");
		assertTrue("Expect standard tokens to match", factory.matchesStandardTokens(tokens));

		setUpTokens("x()");
		assertFalse("Expect bad operator to not match", factory.matchesStandardTokens(tokens));

		setUpTokens("∨()");
		assertFalse("Expect wrong operator to not match", factory.matchesStandardTokens(tokens));

		setUpTokens("¬x)");
		assertFalse("Expect bad bracket to not match", factory.matchesStandardTokens(tokens));

		setUpTokens("¬(x");
		assertFalse("Expect bad bracket to not match", factory.matchesStandardTokens(tokens));
	}

	@Test
	public void testMatchesFunction() throws Exception {
		functions = null;
		assertFalse("Expect null functions to not match", factory.matchesFunction(functions));

		setUpFunction("x = y");
		assertTrue("Expect single function to match", factory.matchesFunction(functions));

		setUpFunction("x = y");
		functions.add(null);
		assertFalse("Expect additional function to not match", factory.matchesFunction(functions));

		functions = new ArrayList<>(1);
		functions.add(null);
		assertFalse("Expect null function to not match", factory.matchesFunction(functions));
	}

	private void setUpFunction(String equalityPredicateString1) throws Exception {
		functions = new ArrayList<>(1);
		if (equalityPredicateString1.isEmpty()) {
			functions.add(null);
		} else {
			functions.add(predicateFactory.createElement(lexer.tokeniseString(equalityPredicateString1)));
		}
	}

	private void setUpTokens(String tokenString) throws Exception {
		tokens = lexer.tokeniseString(tokenString);
	}
}
