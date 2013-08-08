package logic.evaluable.statements.quantified.standard;

import logic.TestClass;
import logic.evaluable.Evaluable;
import logic.evaluable.predicate.equality.EqualityPredicateFactory;
import logic.factory.FactoryTest;
import logic.function.Function;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
}
