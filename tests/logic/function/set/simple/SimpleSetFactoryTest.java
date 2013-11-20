package logic.function.set.simple;

import logic.factory.FactoryTest;
import maths.number.integer.Integer;
import org.junit.Test;

import static logic.function.set.simple.SimpleSetFactory.createElement;
import static org.junit.Assert.assertEquals;

/**
 * @author Steven Weston
 */
public class SimpleSetFactoryTest extends FactoryTest<SimpleSetFactory<Integer>> {

	public SimpleSetFactoryTest() {
		factory = new SimpleSetFactory<>(Integer.class);
	}

	@Test
	public void testParenthesesThrowException() throws Exception {
		setUpTokens("(1 2)");
		setUpFunctions();
		expectFactoryException();
	}

	@Test
	public void testCreateElement() throws Exception {
		SimpleSet<Integer> expected;
		SimpleSet<Integer> actual;

		expected = createElement("1", "2");

		setUpTokens("{1 2}");
		setUpFunctions();
		actual = (SimpleSet<Integer>) factory.createElement(tokens, functions);

		assertEquals(expected, actual);
	}
}
