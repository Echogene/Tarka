package logic.function.factory;

import logic.TestClass;
import logic.factory.SimpleLogicLexerImpl;
import logic.function.Function;
import logic.function.factory.binary.BinaryValidator;
import logic.function.reflexive.identity.IdentityFunction;
import org.junit.BeforeClass;
import reading.lexing.Token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static logic.function.factory.ValidationResult.ValidationType;
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

	private List<logic.function.factory.validation.results.ValidationResult> getResult() throws ValidationException {
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
