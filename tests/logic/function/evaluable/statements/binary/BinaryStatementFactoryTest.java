package logic.function.evaluable.statements.binary;

import logic.TestClass;
import logic.factory.FactoryTest;
import logic.function.Function;
import logic.function.evaluable.Evaluable;
import logic.function.evaluable.predicate.equality.EqualityPredicateFactory;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Steven Weston
 */
public class BinaryStatementFactoryTest extends FactoryTest<BinaryStatementFactory<TestClass>> {
	private static BinaryConnectiveFactory connectiveFactory;

	public BinaryStatementFactoryTest() {
		super();
		factory = new BinaryStatementFactory<>();
		connectiveFactory = new BinaryConnectiveFactory();
		functionFactory = new EqualityPredicateFactory<>();
	}

	@Test
	public void testCurlyBracketsThrowException() throws Exception {
		setUpTokens("{()∨()}");
		setUpFunctions("(x=y)", "(y=z)");
		expectFactoryException();
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
				connectiveFactory.createElement("∨"),
				(Evaluable<TestClass>) evaluable2);
		setUpTokens("(()∨())");
		setUpFunctions("(x=y)", "(y=z)");
		actual = factory.createElement(tokens, functions);
		assertEquals("Expect created binary statement to be equal to the factory-built one", expected, actual);
	}
}
