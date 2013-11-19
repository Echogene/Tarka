package logic.function.set.union;

import logic.TestClass;
import logic.factory.FactoryTest;
import logic.function.identity.IdentityFunctionFactory;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Steven Weston
 */
public class MultaryUnionFactoryTest extends FactoryTest<MultaryUnionFactory<TestClass>> {

	public MultaryUnionFactoryTest() {
		factory = new MultaryUnionFactory<>();
		functionFactory = new IdentityFunctionFactory<>();
	}

	@Test
	public void testCurlyBracketsThrowException() throws Exception {
		setUpTokens("{X ∪ Y}");
		setUpFunctions("", "");
		expectFactoryException();

		setUpTokens("{⋃ X Y}");
		setUpFunctions("", "");
		expectFactoryException();
	}

	@Test
	public void testCreateElement() throws Exception {
		Union<TestClass> expected;
		Union<TestClass> actual;

		expected = MultaryUnionFactory.createElement("X", "Y");
		setUpTokens("(X ∪ Y)");
		setUpFunctions();
		actual = factory.createElement(tokens, functions);
		assertEquals(expected, actual);

		setUpTokens("(X ∪ Y)");
		functions = null;
		actual = factory.createElement(tokens, functions);
		assertEquals(expected, actual);

		setUpTokens("(X ∪ Y)");
		actual = factory.createElement(tokens);
		assertEquals(expected, actual);

		setUpTokens("(X ∪ Y)");
		setUpFunctions();
		actual = factory.createElement(tokens, functions);
		assertEquals(expected, actual);

		setUpTokens("(() ∪ Y)");
		setUpFunctions("(Id X)");
		actual = factory.createElement(tokens, functions);
		assertEquals(expected, actual);

		setUpTokens("(X ∪ ())");
		setUpFunctions("(Id Y)");
		actual = factory.createElement(tokens, functions);
		assertEquals(expected, actual);

		setUpTokens("(() ∪ ())");
		setUpFunctions("(Id X)", "(Id Y)");
		actual = factory.createElement(tokens, functions);
		assertEquals(expected, actual);

		setUpTokens("(⋃ X Y)");
		setUpFunctions();
		actual = factory.createElement(tokens, functions);
		assertEquals(expected, actual);

		setUpTokens("(⋃ Y X)");
		setUpFunctions();
		actual = factory.createElement(tokens, functions);
		assertEquals(expected, actual);

		expected = MultaryUnionFactory.createElement("X", "Y", "Z");
		setUpTokens("(⋃ X Y Z)");
		setUpFunctions();
		actual = factory.createElement(tokens, functions);
		assertEquals(expected, actual);

		setUpTokens("(⋃ () () ())");
		setUpFunctions("(Id X)", "(Id Y)", "(Id Z)");
		actual = factory.createElement(tokens, functions);
		assertEquals(expected, actual);

		setUpTokens("(⋃ X () ())");
		setUpFunctions("(Id Y)", "(Id Z)");
		actual = factory.createElement(tokens, functions);
		assertEquals(expected, actual);
	}
}
