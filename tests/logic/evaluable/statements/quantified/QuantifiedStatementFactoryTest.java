package logic.evaluable.statements.quantified;

import logic.TestClass;
import logic.evaluable.Evaluable;
import logic.evaluable.predicate.equality.EqualityPredicateFactory;
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
public class QuantifiedStatementFactoryTest {
	private static List<Token> tokens;
	private static List<Function<?, ?>> functions;
	private static SimpleLogicLexerImpl lexer;
	private static QuantifiedStatementFactory<TestClass> factory;
	private static QuantifierFactory quantifierFactory;
	private static EqualityPredicateFactory<TestClass> predicateFactory;

	@BeforeClass
	public static void setUp() {
		lexer   = new SimpleLogicLexerImpl();
		factory = new QuantifiedStatementFactory<>();
		quantifierFactory = new QuantifierFactory();
		predicateFactory = new EqualityPredicateFactory<>();
	}

	@Test
	public void testCreateElement() throws Exception {
		Function<TestClass, Boolean> expected;
		Function<TestClass, Boolean> actual;

		Function<TestClass, Boolean> evaluable1;

		evaluable1 = EqualityPredicateFactory.createElement("x", "y");
		expected = new QuantifiedStatement<>(
				(Quantifier) quantifierFactory.createElement("∀"),
				"x",
				(Evaluable<TestClass>) evaluable1
		);
		setUpTokens("∀x()");
		setUpFunction("x=y");
		actual = factory.createElement(tokens, functions);
		assertEquals("Expect created quantified statement to be equal to the factory-built one", expected, actual);

		evaluable1 = EqualityPredicateFactory.createElement("x", "y");
		expected = new QuantifiedStatement<>(
				(Quantifier) quantifierFactory.createElement("¬∀"),
				"x",
				(Evaluable<TestClass>) evaluable1
		);
		setUpTokens("¬∀x()");
		setUpFunction("x=y");
		actual = factory.createElement(tokens, functions);
		assertEquals("Expect created quantified statement to be equal to the factory-built one", expected, actual);

		evaluable1 = EqualityPredicateFactory.createElement("x", "y");
		expected = new QuantifiedStatement<>(
				(Quantifier) quantifierFactory.createElement("∃!"),
				"x",
				(Evaluable<TestClass>) evaluable1
		);
		setUpTokens("∃!x()");
		setUpFunction("x=y");
		actual = factory.createElement(tokens, functions);
		assertEquals("Expect created quantified statement to be equal to the factory-built one", expected, actual);
	}

	@Test
	public void testMatchesTokens() throws Exception {
		setUpTokens("∀x()");
		assertTrue("Expect standard tokens to match", factory.matchesTokens(tokens));

		setUpTokens("∃!x()");
		assertTrue("Expect standard tokens to match", factory.matchesTokens(tokens));

		setUpTokens("¬∀x()");
		assertTrue("Expect standard tokens to match", factory.matchesTokens(tokens));

		tokens = null;
		assertFalse("Expect null tokens to not match", factory.matchesTokens(tokens));

		setUpTokens("∀x y()");
		assertFalse("Expect wrong number of tokens to not match", factory.matchesTokens(tokens));

		setUpTokens("x x()");
		assertFalse("Expect bad quantifier token to not match", factory.matchesTokens(tokens));

		setUpTokens("∀ ∃()");
		assertFalse("Expect bad name token to not match", factory.matchesTokens(tokens));

		setUpTokens("∀x x)");
		assertFalse("Expect bad bracket token to not match", factory.matchesTokens(tokens));

		setUpTokens("∀x (x");
		assertFalse("Expect bad bracket token to not match", factory.matchesTokens(tokens));
	}

	@Test
	public void testMatchesFunctions() throws Exception {
		setUpFunction("x = y");
		assertTrue("Expect one function to match", factory.matchesFunctions(functions));

		functions = new ArrayList<>(2);
		functions.add(null);
		functions.add(predicateFactory.createElement(lexer.tokeniseString("x = y")));
		assertTrue("Expect two functions with first null to match", factory.matchesFunctions(functions));

		functions = null;
		assertFalse("Expect null function to not match", factory.matchesFunctions(functions));

		setUpFunction("x = y");
		functions.add(null);
		assertFalse("Expect wrong order of functions to not match", factory.matchesFunctions(functions));

		setUpFunction("");
		assertFalse("Expect missing function to not match", factory.matchesFunctions(functions));

		functions = new ArrayList<>();
		functions.add(new IdentityFunction<TestClass>("x"));
		assertFalse("Expect wrong function type to not match", factory.matchesFunctions(functions));
	}

	private void setUpFunction(String equalityPredicateString) throws Exception {
		functions = new ArrayList<>(1);
		if (equalityPredicateString.isEmpty()) {
			functions.add(null);
		} else {
			functions.add(predicateFactory.createElement(lexer.tokeniseString(equalityPredicateString)));
		}
	}

	private void setUpTokens(String tokenString) throws Exception {
		tokens = lexer.tokeniseString(tokenString);
	}
}
