package logic.function.factory;

import logic.TestClass;
import logic.factory.SimpleLogicLexerImpl;
import logic.function.Function;
import logic.function.factory.multary.MultaryValidator;
import logic.function.reflexiveset.identity.SetIdentityFunction;
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
public class MultaryValidatorTest {
	private static List<Token> tokens;
	private static List<Function<?, ?>> functions;
	private static SimpleLogicLexerImpl lexer;
	private static MultaryValidator validator;

	@BeforeClass
	public static void setUp() {
		lexer     = new SimpleLogicLexerImpl();
		validator = new MultaryValidator(Arrays.asList("⋃"), SetIdentityFunction.class);
	}

	@Test
	public void testValidate() throws Exception {
		setUpTokens("⋃ X");
		setUpFunctions("");
		assertValid(getResult(), TOKEN);

		setUpTokens("⋃ X Y");
		setUpFunctions("", "");
		assertValid(getResult(), TOKEN, TOKEN);

		setUpTokens("⋃ X Y Z");
		setUpFunctions("", "", "");
		assertValid(getResult(), TOKEN, TOKEN, TOKEN);

		setUpTokens("⋃ ()");
		setUpFunctions("X");
		assertValid(getResult(), FUNCTION);

		setUpTokens("⋃ () ()");
		setUpFunctions("X", "Y");
		assertValid(getResult(), FUNCTION, FUNCTION);

		setUpTokens("⋃ () Y ()");
		setUpFunctions("X", "", "Z");
		assertValid(getResult(), FUNCTION, TOKEN, FUNCTION);

		setUpTokens("⋃ X Y ()");
		setUpFunctions("", "", "Z");
		assertValid(getResult(), TOKEN, TOKEN, FUNCTION);

		setUpTokens("⋃ ()");
		setUpFunctions("");
		assertInvalid(getResult());

		setUpTokens("⋃ ()");
		setUpFunctions();
		assertInvalid(getResult());

		setUpTokens("⋃ X");
		setUpFunctions();
		assertInvalid(getResult());

		setUpTokens("⋃ X");
		setUpFunctions("X");
		assertInvalid(getResult());

		setUpTokens("⋃ X");
		setUpFunctions("", "");
		assertInvalid(getResult());
	}

	private ValidationResult getResult() {
		return validator.validate(tokens, functions);
	}

	private static void assertValid(ValidationResult result, ValidationType... types) throws Exception {
		assertTrue(result.isValid());
		assertMatchesTypes(result, types);
	}

	private static void assertInvalid(ValidationResult result) throws Exception {
		assertFalse(result.isValid());
	}

	private static void assertMatchesTypes(ValidationResult result, ValidationType... types) {
		for (int i = 0; i < types.length; i++) {
			assertEquals(result.get(i), types[i]);
		}
		assertEquals(result.size(), types.length);
	}

	private void setUpFunctions(String... identityFunctionParameters) {
		functions = new ArrayList<>();
		for (String identityFunctionParameter : identityFunctionParameters) {
			if (identityFunctionParameter == null || identityFunctionParameter.isEmpty()) {
				functions.add(null);
			} else {
				functions.add(new SetIdentityFunction<TestClass>(identityFunctionParameter));
			}
		}
	}

	private void setUpTokens(String tokenString) throws Exception {
		tokens = lexer.tokeniseString(tokenString);
	}

}
