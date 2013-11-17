package logic.function.factory.oldvalidation.group;

import logic.function.factory.oldvalidation.group.validators.FunctionAtom;
import logic.function.reflexive.ReflexiveFunction;
import logic.function.set.SetFunction;
import logic.identity.IdentityFunction;
import logic.identity.SetIdentityFunction;
import org.junit.Test;

import java.util.Arrays;

import static java.util.Arrays.asList;

/**
 * @author Steven Weston
 */
public class FunctionAtomTest extends GroupValidatorTest {

	@Test
	public void testValidate() throws Exception {
		validator = new FunctionAtom(
				asList("("),
				Arrays.<Class>asList(ReflexiveFunction.class),
				asList(")")
		);
		validator.validate(
				newFunctionGroup("(", ")"),
				new IdentityFunction("x")
		);

		validator = new FunctionAtom(
				asList("{", "("),
				Arrays.<Class>asList(SetFunction.class),
				asList("}", ")")
		);
		validator.validate(
				newFunctionGroup("{", "}"),
				new SetIdentityFunction<>("X")
		);
		validator.validate(
				newFunctionGroup("(", ")"),
				new SetIdentityFunction<>("X")
		);
		expectValidationException(
				newFunctionGroup("(", "}"),
				new SetIdentityFunction<>("X")
		);
		validator = new FunctionAtom(
				null,
				Arrays.<Class>asList(SetFunction.class),
				null
		);
		validator.validate(
				newFunctionGroup("{", "}"),
				new SetIdentityFunction<>("X")
		);
		validator.validate(
				newFunctionGroup("(", ")"),
				new SetIdentityFunction<>("X")
		);
		validator.validate(
				newFunctionGroup("(", "}"),
				new SetIdentityFunction<>("X")
		);
	}
}
