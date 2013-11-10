package logic.function.factory.oldvalidation;

import logic.function.factory.oldvalidation.group.GroupValidatorTest;
import org.junit.Test;

/**
 * @author Steven Weston
 */
public class VariableAtomTest extends GroupValidatorTest {
	@Test
	public void testValidate() throws Exception {
		validator = new VariableAtom();

		validator.validate(
				newNameToken("x"),
				null
		);
		validator.validate(
				newNameToken("y"),
				null
		);
	}
}
