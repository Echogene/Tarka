package logic.function.set.simple;

import logic.set.NamedSet;
import logic.set.Set;
import maths.number.integer.Integer;
import maths.number.integer.model.universe.IntegerUniverse;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Steven Weston
 */
public class SimpleSetTest {

	@Test
	public void testResult() throws Exception {
		Set<Integer> expected;
		Set<Integer> actual;
		SimpleSet<Integer> set = SimpleSetFactory.createElement("x", "2");

		IntegerUniverse universe = new IntegerUniverse();

		universe.assignVariable("x");
		universe.setVariable("x", new Integer(1));

		expected = new NamedSet<>(set.getName());
		expected.put(new Integer(1));
		expected.put(new Integer(2));

		actual = set.evaluate(universe);
		assertEquals(expected, actual);

		universe.setVariable("x", new Integer(2));

		expected = new NamedSet<>(set.getName());
		expected.put(new Integer(2));

		actual = set.evaluate(universe);
		assertEquals(expected, actual);
	}
}
