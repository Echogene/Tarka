package maths.number;

import maths.number.integer.Integer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Steven Weston
 */
public class IntegerTest {
	@Test
	public void testConstructors() throws Exception {
		checkConstructedIntegerHasSameName("1");
		checkConstructedIntegerHasSameName("10");
		checkConstructedIntegerHasSameName("1000");
		checkConstructedIntegerHasSameName("1000000");
		checkConstructedIntegerHasSameName("100000000000000");
		checkConstructedIntegerHasSameName("1000000000000000000000000000000000000000000000000000000000000000000000000");
	}

	private void checkConstructedIntegerHasSameName(String value) {
		maths.number.integer.Integer integer = new Integer(value);
		assertEquals(value, integer.getName());
	}
}
