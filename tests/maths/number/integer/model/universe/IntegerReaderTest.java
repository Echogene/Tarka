package maths.number.integer.model.universe;

import logic.factory.SimpleLogicReader;
import logic.function.evaluable.Evaluable;
import maths.number.integer.Integer;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Steven Weston
 */
public class IntegerReaderTest {
	@Test
	public void testReader() throws Exception {
		IntegerUniverse universe = new IntegerUniverse();
		SimpleLogicReader<Integer> reader = IntegerReader.createStandardReader(universe);

		@SuppressWarnings("unchecked")
		Evaluable<Integer> twoPlusTwoIsFour = (Evaluable<Integer>) reader.read("((2 + 2) = 4)");
		assertTrue(twoPlusTwoIsFour.evaluate(universe));
		@SuppressWarnings("unchecked")
		Evaluable<Integer> twoPlusTwoIsFive = (Evaluable<Integer>) reader.read("((2 + 2) = 5)");
		assertFalse(twoPlusTwoIsFive.evaluate(universe));
		@SuppressWarnings("unchecked")
		Evaluable<Integer> associative = (Evaluable<Integer>) reader.read("((2 + 3) = (3 + 2))");
		assertTrue(associative.evaluate(universe));
		@SuppressWarnings("unchecked")
		Evaluable<Integer> sum = (Evaluable<Integer>) reader.read("((Σ 2 3 4) = 9)");
		assertTrue(sum.evaluate(universe));
		@SuppressWarnings("unchecked")
		Evaluable<Integer> negative = (Evaluable<Integer>) reader.read("((2 + -2) = 0)");
		assertTrue(negative.evaluate(universe));
		@SuppressWarnings("unchecked")
		Evaluable<Integer> subtraction = (Evaluable<Integer>) reader.read("((2 − 2) = 0)");
		assertTrue(subtraction.evaluate(universe));
		@SuppressWarnings("unchecked")
		Evaluable<Integer> membership = (Evaluable<Integer>) reader.read("(2 ∊ {1 2 3})");
		assertTrue(membership.evaluate(universe));
	}
}
