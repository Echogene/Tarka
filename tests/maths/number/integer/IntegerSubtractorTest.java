package maths.number.integer;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Steven Weston
 */
public class IntegerSubtractorTest {
	@Test
	public void testSubtract() throws Exception {
		IntegerSubtractor subtractor = new IntegerSubtractor();

		assertEquals(new Integer(2), subtractor.subtract(new Integer(4), new Integer(2)));
	}
}
