package maths.number.integer;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Steven Weston
 */
public class IntegerSummorTest {
	@Test
	public void testAdd() throws Exception {
		Integer augand = new Integer(2);
		Integer addend = new Integer(2);

		IntegerSummor summor = new IntegerSummor();

		Integer sum = summor.add(augand, addend);
		assertEquals("2 + 2 = 4", "4", sum.getName());
	}
}
