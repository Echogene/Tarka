package logic.evaluable.statements.unary;

import org.junit.Test;

import static logic.evaluable.statements.unary.UnaryConnective.UnaryConnectiveType.EMPTY;
import static logic.evaluable.statements.unary.UnaryConnective.UnaryConnectiveType.NEGATION;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Steven Weston
 */
public class UnaryConnectiveTest {
	@Test
	public void testApply() throws Exception {
		UnaryConnective connective;
		connective = new UnaryConnective(EMPTY);
		assertTrue(connective.apply(true));
		assertFalse(connective.apply(false));

		connective = new UnaryConnective(NEGATION);
		assertFalse(connective.apply(true));
		assertTrue(connective.apply(false));
	}
}
