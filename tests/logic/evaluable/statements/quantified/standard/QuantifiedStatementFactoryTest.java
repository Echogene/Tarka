package logic.evaluable.statements.quantified.standard;

import logic.TestClass;
import logic.evaluable.Evaluable;
import logic.evaluable.predicate.equality.EqualityPredicateFactory;
import logic.factory.FactoryTest;
import logic.function.Function;
import logic.function.reflexive.identity.IdentityFunction;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * @author Steven Weston
 */
public class QuantifiedStatementFactoryTest extends FactoryTest<QuantifiedStatementFactory<TestClass>> {
	
	private static QuantifierFactory quantifierFactory;
	
	public QuantifiedStatementFactoryTest() {
		factory = new QuantifiedStatementFactory<>();
		quantifierFactory = new QuantifierFactory();
		functionFactory = new EqualityPredicateFactory<>();
	}

	@Test
	public void testCurlyBracketsThrowException() throws Exception {
		setUpTokens("{∀x()}");
		setUpFunctions("(x=y)");
		expectFactoryException();

		setUpTokens("(∀x{})");
		setUpFunctions("(x=y)");
		expectFactoryException();
	}

	@Test
	public void testCreateElement() throws Exception {
		Function<TestClass, Boolean> expected;
		Function<TestClass, Boolean> actual;

		Function<TestClass, Boolean> evaluable1;

		evaluable1 = EqualityPredicateFactory.createElement("x", "y");
		expected = new QuantifiedStatement<>(
				quantifierFactory.createElement("∀"),
				"x",
				(Evaluable<TestClass>) evaluable1
		);
		setUpTokens("(∀x())");
		setUpFunctions("(x=y)");
		actual = factory.createElement(tokens, functions);
		assertEquals("Expect created quantified statement to be equal to the factory-built one", expected, actual);

		evaluable1 = EqualityPredicateFactory.createElement("x", "y");
		expected = new QuantifiedStatement<>(
				quantifierFactory.createElement("¬∀"),
				"x",
				(Evaluable<TestClass>) evaluable1
		);
		setUpTokens("(¬∀x())");
		setUpFunctions("(x=y)");
		actual = factory.createElement(tokens, functions);
		assertEquals("Expect created quantified statement to be equal to the factory-built one", expected, actual);

		evaluable1 = EqualityPredicateFactory.createElement("x", "y");
		expected = new QuantifiedStatement<>(
				quantifierFactory.createElement("∃!"),
				"x",
				(Evaluable<TestClass>) evaluable1
		);
		setUpTokens("(∃!x())");
		setUpFunctions("(x=y)");
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
		setUpFunctions("(x = y)");
		assertTrue("Expect one function to match", factory.matchesFunctions(functions));

		functions = new ArrayList<>(2);
		functions.add(null);
		functions.add(functionFactory.createElement(lexer.tokeniseString("(x = y)")));
		assertTrue("Expect two functions with first null to match", factory.matchesFunctions(functions));

		functions = null;
		assertFalse("Expect null function to not match", factory.matchesFunctions(functions));

		setUpFunctions("(x = y)");
		functions.add(null);
		assertFalse("Expect wrong order of functions to not match", factory.matchesFunctions(functions));

		setUpFunctions("");
		assertFalse("Expect missing function to not match", factory.matchesFunctions(functions));

		functions = new ArrayList<>();
		functions.add(new IdentityFunction<TestClass>("x"));
		assertFalse("Expect wrong function type to not match", factory.matchesFunctions(functions));
	}
}
