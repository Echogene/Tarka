package logic.evaluable.statements.binary;

import logic.TestClass;
import logic.evaluable.Evaluable;
import logic.evaluable.predicate.EqualityPredicate;
import logic.evaluable.predicate.EqualityPredicateFactory;
import logic.factory.SimpleLogicLexerImpl;
import logic.function.Function;
import logic.function.reflexive.IdentityFunction;
import org.junit.BeforeClass;
import org.junit.Test;
import reading.lexing.Token;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

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

		IdentityFunction<TestClass> x = new IdentityFunction<>("x");
		IdentityFunction<TestClass> y = new IdentityFunction<>("y");
		IdentityFunction<TestClass> z = new IdentityFunction<>("z");
		evaluable1 = new EqualityPredicate<>(x, y);
		evaluable2 = new EqualityPredicate<>(y, z);
		expected = new BinaryStatement<>(
				(Evaluable<TestClass>) evaluable1,
				(BinaryConnective) connectiveFactory.createElement("∨"),
				(Evaluable<TestClass>) evaluable2);
		setUpTokens("()∨()");
		setUpFunctions("x=y", "y=z");
		actual = factory.createElement(tokens, functions);
		assertEquals("Expect created binary statement to be equal to the factory-built one", expected, actual);
	}

	@Test
	public void testMatchesTokens() throws Exception {
		setUpTokens("()∨()");
		assertTrue("Expect standard tokens to match", factory.matchesTokens(tokens));

		setUpTokens("x)∨()");
		assertFalse("Expect bad bracket to not match", factory.matchesTokens(tokens));

		setUpTokens("(x∨()");
		assertFalse("Expect bad bracket to not match", factory.matchesTokens(tokens));

		setUpTokens("()∨x)");
		assertFalse("Expect bad bracket to not match", factory.matchesTokens(tokens));

		setUpTokens("()∨(x");
		assertFalse("Expect bad bracket to not match", factory.matchesTokens(tokens));

		setUpTokens("() x ()");
		assertFalse("Expect bad operator to not match", factory.matchesTokens(tokens));

		setUpTokens("() = ()");
		assertFalse("Expect wrong operator to not match", factory.matchesTokens(tokens));
	}

	@Test
	public void testMatchesFunctions() throws Exception {
		setUpFunctions("x = y", "y = z");
		assertTrue("Expect standard functions to match", factory.matchesFunctions(functions));

		functions = null;
		assertFalse("Expect null functions to not match", factory.matchesFunctions(functions));

		functions = new ArrayList<>(1);
		functions.add(null);
		assertFalse("Expect wrong number of functions to not match", factory.matchesFunctions(functions));

		setUpFunctions("", "y = z");
		assertFalse("Expect missing function to not match", factory.matchesFunctions(functions));

		setUpFunctions("x = y", "");
		assertFalse("Expect missing function to not match", factory.matchesFunctions(functions));

		setUpFunctions("", "");
		assertFalse("Expect missing functions to not match", factory.matchesFunctions(functions));
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
