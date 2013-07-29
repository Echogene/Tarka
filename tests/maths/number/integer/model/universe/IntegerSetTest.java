package maths.number.integer.model.universe;

import maths.number.integer.Integer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Steven Weston
 */
public class IntegerSetTest {
	@Test
	public void testGet() throws Exception {
		IntegerSet set = new IntegerSet("Integers");

		assertEquals(new Integer(5), set.get("5"));

		set.put("x", new Integer(6));

		assertEquals(new Integer(6), set.get("x"));
	}
}
