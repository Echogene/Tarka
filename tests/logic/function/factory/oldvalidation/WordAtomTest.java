package logic.function.factory.oldvalidation;

import logic.function.factory.oldvalidation.group.GroupValidatorTest;
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
