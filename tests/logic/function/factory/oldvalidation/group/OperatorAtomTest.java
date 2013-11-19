package logic.function.factory.oldvalidation.group;

import logic.function.factory.oldvalidation.group.validators.OperatorAtom;
import logic.identity.MemberIdentityFunction;
import org.junit.Test;

import static java.util.Arrays.asList;

/**
 * @author Steven Weston
 */
public class OperatorAtomTest extends GroupValidatorTest {

	@Test
	public void testValidate() throws Exception {
		validator = new OperatorAtom(
				asList("∨", "∧")
		);

		validator.validate(
				newOperatorToken("∨"),
				null
		);
		expectValidationException(
				newOperatorToken("+"),
				null
		);
		expectValidationException(
				newNameToken("∨"),
				null
		);
		expectValidationException(
				newOperatorToken("∨"),
				new MemberIdentityFunction("x")
		);
	}
}
