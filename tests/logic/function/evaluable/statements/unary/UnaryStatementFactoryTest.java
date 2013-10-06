package logic.function.evaluable.statements.unary;

import logic.TestClass;
import logic.factory.FactoryTest;
import logic.factory.SimpleLogicLexerImpl;
import logic.function.Function;
import logic.function.evaluable.Evaluable;
import logic.function.evaluable.predicate.equality.EqualityPredicateFactory;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
	public void testCurlyBracketsThrowException() throws Exception {
		setUpTokens("{()}");
		setUpFunctions("(x = y)");
		expectFactoryException();

		setUpTokens("{¬()}");
		setUpFunctions("(x = y)");
		expectFactoryException();
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
}
