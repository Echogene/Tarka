package logic.function.factory.validation;

import logic.function.factory.validation.group.GroupValidatorTest;
import org.junit.Test;

/**
 * @author Steven Weston
 */
public class WordAtomTest extends GroupValidatorTest {
	@Test
	public void testValidate() throws Exception {
		validator = new WordAtom("word");

		validator.validate(
				newNameToken("word"),
				null
		);
		expectValidationException(
				newNameToken("notword"),
				null
		);
	}
}
