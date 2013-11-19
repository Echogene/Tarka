package logic.function.evaluable.predicate;

import logic.TestClass;
import logic.factory.FactoryTest;
import logic.function.Function;
import logic.function.evaluable.predicate.equality.EqualityPredicateFactory;
import logic.function.identity.IdentityFunctionFactory;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Steven Weston
 */
public class EqualityPredicateFactoryTest extends FactoryTest<EqualityPredicateFactory<TestClass>> {

	public EqualityPredicateFactoryTest() {
		factory = new EqualityPredicateFactory<>();
		functionFactory = new IdentityFunctionFactory<TestClass>();
	}

	@Test
	public void testCurlyBracketsThrowException() throws Exception {
		setUpTokens("{x = y}");
		setUpFunctions("", "");
		expectFactoryException();
	}

	@Test
	public void testCreateElements() throws Exception {
		Function<TestClass, Boolean> expected;
		Function<TestClass, Boolean> actual;

		setUpTokens("(x = y)");
		expected =EqualityPredicateFactory.createElement("x", "y");
		actual = factory.createElement(tokens);
		assertEquals("Expected created equality predicate to be equal to the factory-built one", expected, actual);

		setUpTokens("(x = ())");
		setUpFunctions("(y)");
		actual = factory.createElement(tokens, functions);
		assertEquals("Expected created equality predicate to be equal to the factory-built one", expected, actual);

		setUpTokens("(() = y)");
		setUpFunctions("(x)");
		actual = factory.createElement(tokens, functions);
		assertEquals("Expected created equality predicate to be equal to the factory-built one", expected, actual);

		setUpTokens("(() = ())");
		setUpFunctions("(x)", "(y)");
		actual = factory.createElement(tokens, functions);
		assertEquals("Expected created equality predicate to be equal to the factory-built one", expected, actual);
	}
}
