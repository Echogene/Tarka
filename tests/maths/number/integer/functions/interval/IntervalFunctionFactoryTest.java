package maths.number.integer.functions.interval;

import logic.factory.FactoryTest;
import logic.identity.IdentityFunctionFactory;
import maths.number.integer.Integer;
import maths.number.integer.sets.interval.FiniteIntegerIntervalFactory;
import org.junit.Test;

import static maths.number.integer.sets.interval.IntervalBound.BoundType.CLOSED;
import static maths.number.integer.sets.interval.IntervalBound.BoundType.OPEN;
import static org.junit.Assert.assertEquals;

/**
 * @author Steven Weston
 */
public class IntervalFunctionFactoryTest extends FactoryTest<IntervalFunctionFactory<Integer>> {

	private final FiniteIntegerIntervalFactory intervalFactory;
	private final IdentityFunctionFactory<Integer> identityFunctionFactory;

	public IntervalFunctionFactoryTest() {
		intervalFactory = new FiniteIntegerIntervalFactory();
		factory = new IntervalFunctionFactory<>(intervalFactory);
		identityFunctionFactory = new IdentityFunctionFactory<>();
	}

	@Test
	public void testCreation() throws Exception {
		IntervalFunction<Integer> expected;
		IntervalFunction<Integer> actual;

		setUpTokens("(0 1)");
		setUpFunctions();
		expected = new IntervalFunction<>(
				OPEN,
				identityFunctionFactory.construct("0"),
				identityFunctionFactory.construct("1"),
				OPEN,
				intervalFactory
		);
		actual = (IntervalFunction<Integer>) factory.createElement(tokens, functions);
		assertEquals(expected, actual);

		setUpTokens("[0 1]");
		setUpFunctions();
		expected = new IntervalFunction<>(
				CLOSED,
				identityFunctionFactory.construct("0"),
				identityFunctionFactory.construct("1"),
				CLOSED,
				intervalFactory
		);
		actual = (IntervalFunction<Integer>) factory.createElement(tokens, functions);
		assertEquals(expected, actual);
	}
}
