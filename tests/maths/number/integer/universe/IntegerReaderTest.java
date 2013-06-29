package maths.number.integer.universe;

import logic.evaluable.Evaluable;
import logic.factory.SimpleLogicReaderImpl;
import maths.number.integer.Integer;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * @author Steven Weston
 */
public class IntegerReaderTest {
	@Test
	public void testReader() throws Exception {
		SimpleLogicReaderImpl<Integer> reader = IntegerReader.createStandardReader();
		IntegerUniverse universe = new IntegerUniverse();

		@SuppressWarnings("unchecked")
		Evaluable<Integer> twoPlusTwoIsFour = (Evaluable<Integer>) reader.read("((2 + 2) = 4)");
		assertTrue(twoPlusTwoIsFour.evaluate(universe));
	}
}
