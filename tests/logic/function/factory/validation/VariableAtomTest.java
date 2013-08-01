package logic.function.factory.validation;

import logic.function.factory.validation.group.GroupValidatorTest;
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
