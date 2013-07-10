package logic.function.factory;

import logic.TestClass;
import logic.factory.SimpleLogicLexerImpl;
import logic.function.Function;
import logic.function.reflexive.identity.IdentityFunction;
import org.junit.BeforeClass;
import org.junit.Test;
import reading.lexing.Token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static logic.function.factory.ValidationResult.ValidationType;
import static logic.function.factory.ValidationResult.ValidationType.FUNCTION;
import static logic.function.factory.ValidationResult.ValidationType.TOKEN;
import static org.junit.Assert.*;

/**
 * @author Steven Weston
 */
public class BinaryValidatorTest {
	private static List<Token> tokens;
	private static List<Function<?, ?>> functions;
	private static SimpleLogicLexerImpl lexer;
	private static BinaryValidator validator;

	@BeforeClass
	public static void setUp() {
		lexer     = new SimpleLogicLexerImpl();
		validator = new BinaryValidator(Arrays.asList("+"));
	}

	@Test
	public void testValidateForNoFunctions() throws Exception {
		setUpTokens("x + y");
		setUpFunctions("", "");
		assertValid(getResult(), TOKEN, TOKEN);

		setUpTokens("x + y");
		functions = null;
		assertValid(getResult(), TOKEN, TOKEN);

		setUpTokens("x + y");
		functions = new ArrayList<>();
		assertValid(getResult(), TOKEN, TOKEN);

		setUpTokens("( + y");
		setUpFunctions("", "");
		assertInvalid(getResult());

		setUpTokens("x a y");
		setUpFunctions("", "");
		assertInvalid(getResult());

		setUpTokens("x ∨ y");
		setUpFunctions("", "");
		assertInvalid(getResult());

		setUpTokens("x + (");
		setUpFunctions("", "");
		assertInvalid(getResult());

		setUpTokens("x + y");
		setUpFunctions("a", "");
		assertInvalid(getResult());

		setUpTokens("x + y");
		setUpFunctions("", "a");
		assertInvalid(getResult());
	}

	@Test
	public void testValidateForFirstFunction() throws Exception {
		setUpTokens("() + y");
		setUpFunctions("x", "");
		assertValid(getResult(), FUNCTION, TOKEN);

		setUpTokens("a) + y");
		setUpFunctions("x", "");
		assertInvalid(getResult());

		setUpTokens("(a + y");
		setUpFunctions("x", "");
		assertInvalid(getResult());

		setUpTokens("() a y");
		setUpFunctions("x", "");
		assertInvalid(getResult());

		setUpTokens("() ∨ y");
		setUpFunctions("x", "");
		assertInvalid(getResult());

		setUpTokens("() + (");
		setUpFunctions("x", "");
		assertInvalid(getResult());

		setUpTokens("() + y");
		setUpFunctions("", "");
		assertInvalid(getResult());

		setUpTokens("() + y");
		setUpFunctions("x", "a");
		assertInvalid(getResult());
	}

	@Test
	public void testValidateForSecondFunction() throws Exception {
		setUpTokens("x + ()");
		setUpFunctions("", "y");
		assertValid(getResult(), TOKEN, FUNCTION);

		setUpTokens("( + ()");
		setUpFunctions("x", "");
		assertInvalid(getResult());

		setUpTokens("x a ()");
		setUpFunctions("x", "");
		assertInvalid(getResult());

		setUpTokens("x ∨ ()");
		setUpFunctions("x", "");
		assertInvalid(getResult());

		setUpTokens("x + a)");
		setUpFunctions("x", "");
		assertInvalid(getResult());

		setUpTokens("x + (a");
		setUpFunctions("x", "");
		assertInvalid(getResult());

		setUpTokens("x + ()");
		setUpFunctions("", "");
		assertInvalid(getResult());

		setUpTokens("x + ()");
		setUpFunctions("a", "y");
		assertInvalid(getResult());

	}

	@Test
	public void testValidateForBothFunctions() throws Exception {
		setUpTokens("() + ()");
		setUpFunctions("x", "y");
		assertValid(getResult(), FUNCTION, FUNCTION);

		setUpTokens("a) + ()");
		setUpFunctions("x", "y");
		assertInvalid(getResult());

		setUpTokens("(a + ()");
		setUpFunctions("x", "y");
		assertInvalid(getResult());

		setUpTokens("() a ()");
		setUpFunctions("x", "y");
		assertInvalid(getResult());

		setUpTokens("() ∨ ()");
		setUpFunctions("x", "y");
		assertInvalid(getResult());

		setUpTokens("() + a)");
		setUpFunctions("x", "y");
		assertInvalid(getResult());

		setUpTokens("() + (a");
		setUpFunctions("x", "y");
		assertInvalid(getResult());

		setUpTokens("() + ()");
		setUpFunctions("", "y");
		assertInvalid(getResult());

		setUpTokens("() + ()");
		setUpFunctions("x", "");
		assertInvalid(getResult());
	}

	private ValidationResult getResult() {
		return validator.validate(tokens, functions);
	}

	private static void assertValid(ValidationResult result, ValidationType type1, ValidationType type2) throws Exception {
		assertTrue(result.isValid());
		assertMatchesTypes(result, type1, type2);
	}

	private static void assertInvalid(ValidationResult result) throws Exception {
		assertFalse(result.isValid());
	}

	private static void assertMatchesTypes(ValidationResult result, ValidationType type1, ValidationType type2) {
		assertEquals(result.get(0), type1);
		assertEquals(result.get(1), type2);
	}

	private void setUpFunctions(String identityFunctionParameter1, String identityFunctionParameter2) {
		functions = new ArrayList<>(2);
		if (identityFunctionParameter1.isEmpty()) {
			functions.add(null);
		} else {
			functions.add(new IdentityFunction<TestClass>(identityFunctionParameter1));
		}
		if (identityFunctionParameter2.isEmpty()) {
			functions.add(null);
		} else {
			functions.add(new IdentityFunction<TestClass>(identityFunctionParameter2));
		}
	}

	private void setUpTokens(String tokenString) throws Exception {
		tokens = lexer.tokeniseString(tokenString);
	}
}
