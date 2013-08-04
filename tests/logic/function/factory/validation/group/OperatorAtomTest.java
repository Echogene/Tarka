package logic.function.factory.validation.group;

import logic.function.factory.validation.group.validators.OperatorAtom;
import logic.function.reflexive.identity.IdentityFunction;
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
				new IdentityFunction("x")
		);
	}
}
