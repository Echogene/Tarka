package logic.function.set.union;

import logic.TestClass;
import logic.factory.FactoryTest;
import logic.function.set.identity.SetIdentityFunctionFactory;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Steven Weston
 */
public class UnionFactoryTest extends FactoryTest<UnionFactory<TestClass>> {

	public UnionFactoryTest() {
		factory = new UnionFactory<>();
		functionFactory = new SetIdentityFunctionFactory<>();
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

		expected = UnionFactory.createElement("X", "Y");
		setUpTokens("(X ∪ Y)");
		setUpFunctions();
		actual = (Union<TestClass>) factory.createElement(tokens, functions);
		assertEquals(expected, actual);

		setUpTokens("(X ∪ Y)");
		functions = null;
		actual = (Union<TestClass>) factory.createElement(tokens, functions);
		assertEquals(expected, actual);

		setUpTokens("(X ∪ Y)");
		actual = (Union<TestClass>) factory.createElement(tokens);
		assertEquals(expected, actual);

		setUpTokens("(X ∪ Y)");
		setUpFunctions();
		actual = (Union<TestClass>) factory.createElement(tokens, functions);
		assertEquals(expected, actual);

		setUpTokens("(() ∪ Y)");
		setUpFunctions("(Id X)");
		actual = (Union<TestClass>) factory.createElement(tokens, functions);
		assertEquals(expected, actual);

		setUpTokens("(X ∪ ())");
		setUpFunctions("(Id Y)");
		actual = (Union<TestClass>) factory.createElement(tokens, functions);
		assertEquals(expected, actual);

		setUpTokens("(() ∪ ())");
		setUpFunctions("(Id X)", "(Id Y)");
		actual = (Union<TestClass>) factory.createElement(tokens, functions);
		assertEquals(expected, actual);

		setUpTokens("(⋃ X Y)");
		setUpFunctions();
		actual = (Union<TestClass>) factory.createElement(tokens, functions);
		assertEquals(expected, actual);

		setUpTokens("(⋃ Y X)");
		setUpFunctions();
		actual = (Union<TestClass>) factory.createElement(tokens, functions);
		assertEquals(expected, actual);

		expected = UnionFactory.createElement("X", "Y", "Z");
		setUpTokens("(⋃ X Y Z)");
		setUpFunctions();
		actual = (Union<TestClass>) factory.createElement(tokens, functions);
		assertEquals(expected, actual);

		setUpTokens("(⋃ () () ())");
		setUpFunctions("(Id X)", "(Id Y)", "(Id Z)");
		actual = (Union<TestClass>) factory.createElement(tokens, functions);
		assertEquals(expected, actual);

		setUpTokens("(⋃ X () ())");
		setUpFunctions("(Id Y)", "(Id Z)");
		actual = (Union<TestClass>) factory.createElement(tokens, functions);
		assertEquals(expected, actual);
	}
}
