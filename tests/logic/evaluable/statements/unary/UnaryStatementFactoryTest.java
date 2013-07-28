package logic.evaluable.statements.unary;

import logic.TestClass;
import logic.evaluable.Evaluable;
import logic.evaluable.predicate.equality.EqualityPredicateFactory;
import logic.factory.FactoryTest;
import logic.factory.SimpleLogicLexerImpl;
import logic.function.Function;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * @author Steven Weston
 */
public class UnaryStatementFactoryTest extends FactoryTest<UnaryStatementFactory<TestClass>> {
	private static UnaryConnectiveFactory connectiveFactory;

	public UnaryStatementFactoryTest() {
		lexer   = new SimpleLogicLexerImpl();
		factory = new UnaryStatementFactory<>();
		connectiveFactory = new UnaryConnectiveFactory();
		functionFactory = new EqualityPredicateFactory<>();
	}

	@Test
	public void testCreateElement() throws Exception {
		Function<TestClass, Boolean> expected;
		Function<TestClass, Boolean> actual;
		Function<TestClass, Boolean> evaluable;

		evaluable = EqualityPredicateFactory.createElement("x", "y");
		expected = new UnaryStatement<>((Evaluable<TestClass>) evaluable);
		setUpTokens("(())");
		setUpFunctions("(x = y)");
		actual = factory.createElement(tokens, functions);
		assertEquals("Expect created unary statement to be equal to the factory-built one", expected, actual);

		evaluable = EqualityPredicateFactory.createElement("x", "y");
		expected = new UnaryStatement<>(connectiveFactory.createElement("¬"),
				(Evaluable<TestClass>) evaluable);
		setUpTokens("(¬())");
		setUpFunctions("(x = y)");
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

		setUpFunctions("(x = y)");
		assertTrue("Expect single function to match", factory.matchesFunction(functions));

		setUpFunctions("(x = y)");
		functions.add(null);
		assertFalse("Expect additional function to not match", factory.matchesFunction(functions));

		functions = new ArrayList<>(1);
		functions.add(null);
		assertFalse("Expect null function to not match", factory.matchesFunction(functions));
	}
}
