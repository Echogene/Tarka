package logic.evaluable.predicate;

import logic.TestClass;
import logic.evaluable.predicate.equality.EqualityPredicateFactory;
import logic.factory.FactoryTest;
import logic.function.Function;
import logic.function.reflexive.identity.IdentityFunctionFactory;
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
	public void testCreateElements() throws Exception {
		Function<TestClass, Boolean> expected;
		Function<TestClass, Boolean> actual;

		expected =EqualityPredicateFactory.createElement("x", "y");
		setUpTokens("x = y");
		actual = factory.createElement(tokens);
		assertEquals("Expected created equality predicate to be equal to the factory-built one", expected, actual);

		setUpFunctions("", "y");
		setUpTokens("x = ()");
		actual = factory.createElement(tokens, functions);
		assertEquals("Expected created equality predicate to be equal to the factory-built one", expected, actual);

		setUpFunctions("x", "");
		setUpTokens("() = y");
		actual = factory.createElement(tokens, functions);
		assertEquals("Expected created equality predicate to be equal to the factory-built one", expected, actual);

		setUpFunctions("x", "y");
		setUpTokens("() = ()");
		actual = factory.createElement(tokens, functions);
		assertEquals("Expected created equality predicate to be equal to the factory-built one", expected, actual);
	}
}
