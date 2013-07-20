package logic.function.reflexiveset.union;

import logic.TestClass;
import logic.factory.FactoryTest;
import logic.function.reflexiveset.identity.SetIdentityFunctionFactory;
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
	public void testCreateElement() throws Exception {
		Union<TestClass> expected;
		Union<TestClass> actual;

		expected = UnionFactory.createElement("X", "Y");
		setUpTokens("X ∪ Y");
		setUpFunctions(null, null);
		actual = (Union<TestClass>) factory.createElement(tokens, functions);
		assertEquals(expected, actual);

		setUpTokens("X ∪ Y");
		functions = null;
		actual = (Union<TestClass>) factory.createElement(tokens, functions);
		assertEquals(expected, actual);

		setUpTokens("X ∪ Y");
		actual = (Union<TestClass>) factory.createElement(tokens);
		assertEquals(expected, actual);

		setUpTokens("X ∪ Y");
		setUpFunctions();
		actual = (Union<TestClass>) factory.createElement(tokens, functions);
		assertEquals(expected, actual);

		setUpTokens("() ∪ Y");
		setUpFunctions("Id X", null);
		actual = (Union<TestClass>) factory.createElement(tokens, functions);
		assertEquals(expected, actual);

		setUpTokens("X ∪ ()");
		setUpFunctions(null, "Id Y");
		actual = (Union<TestClass>) factory.createElement(tokens, functions);
		assertEquals(expected, actual);

		setUpTokens("() ∪ ()");
		setUpFunctions("Id X", "Id Y");
		actual = (Union<TestClass>) factory.createElement(tokens, functions);
		assertEquals(expected, actual);

		setUpTokens("⋃ X Y");
		setUpFunctions(null, null);
		actual = (Union<TestClass>) factory.createElement(tokens, functions);
		assertEquals(expected, actual);

		setUpTokens("⋃ Y X");
		setUpFunctions(null, null);
		actual = (Union<TestClass>) factory.createElement(tokens, functions);
		assertEquals(expected, actual);

		expected = UnionFactory.createElement("X", "Y", "Z");
		setUpTokens("⋃ X Y Z");
		setUpFunctions(null, null, null);
		actual = (Union<TestClass>) factory.createElement(tokens, functions);
		assertEquals(expected, actual);

		setUpTokens("⋃ () () ()");
		setUpFunctions("Id X", "Id Y", "Id Z");
		actual = (Union<TestClass>) factory.createElement(tokens, functions);
		assertEquals(expected, actual);

		setUpTokens("⋃ X () ()");
		setUpFunctions(null, "Id Y", "Id Z");
		actual = (Union<TestClass>) factory.createElement(tokens, functions);
		assertEquals(expected, actual);
	}
}
