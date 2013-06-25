package logic.evaluable.statements.binary;

import org.junit.Test;

import static logic.evaluable.statements.binary.BinaryConnective.BinaryConnectiveType.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Steven Weston
 */
public class BinaryConnectiveTest {
	@Test
	public void testApply() throws Exception {
		BinaryConnective connective;
		connective = new BinaryConnective(OR);
		assertFalse(connective.apply(false, false));
		assertTrue(connective.apply(false, true));
		assertTrue(connective.apply(true, false));
		assertTrue(connective.apply(true, true));

		connective = new BinaryConnective(NOR);
		assertTrue(connective.apply(false, false));
		assertFalse(connective.apply(false, true));
		assertFalse(connective.apply(true, false));
		assertFalse(connective.apply(true, true));

		connective = new BinaryConnective(AND);
		assertFalse(connective.apply(false, false));
		assertFalse(connective.apply(false, true));
		assertFalse(connective.apply(true, false));
		assertTrue(connective.apply(true, true));

		connective = new BinaryConnective(NAND);
		assertTrue(connective.apply(false, false));
		assertTrue(connective.apply(false, true));
		assertTrue(connective.apply(true, false));
		assertFalse(connective.apply(true, true));

		connective = new BinaryConnective(IMPLIES);
		assertTrue(connective.apply(false, false));
		assertTrue(connective.apply(false, true));
		assertFalse(connective.apply(true, false));
		assertTrue(connective.apply(true, true));

		connective = new BinaryConnective(NIMPLIES);
		assertFalse(connective.apply(false, false));
		assertFalse(connective.apply(false, true));
		assertTrue(connective.apply(true, false));
		assertFalse(connective.apply(true, true));

		connective = new BinaryConnective(IFF);
		assertTrue(connective.apply(false, false));
		assertFalse(connective.apply(false, true));
		assertFalse(connective.apply(true, false));
		assertTrue(connective.apply(true, true));

		connective = new BinaryConnective(NIFF);
		assertFalse(connective.apply(false, false));
		assertTrue(connective.apply(false, true));
		assertTrue(connective.apply(true, false));
		assertFalse(connective.apply(true, true));

		connective = new BinaryConnective(IMPLIED_BY);
		assertTrue(connective.apply(false, false));
		assertFalse(connective.apply(false, true));
		assertTrue(connective.apply(true, false));
		assertTrue(connective.apply(true, true));

		connective = new BinaryConnective(NIMPLIED_BY);
		assertFalse(connective.apply(false, false));
		assertTrue(connective.apply(false, true));
		assertFalse(connective.apply(true, false));
		assertFalse(connective.apply(true, true));
	}
}
