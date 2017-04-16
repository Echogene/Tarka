package logic.function.factory.validation;

import logic.factory.SimpleLogicLexer;
import logic.function.factory.FunctionFactory;
import logic.function.factory.validation.checking.CheckerWithNumber;
import logic.function.factory.validation.checking.checkers.FunctionOrVariableChecker;
import logic.function.factory.validation.checking.checkers.NumberedChecker;
import logic.function.factory.validation.checking.checkers.OperatorChecker;
import logic.function.factory.validation.checking.checkers.VariableChecker;
import logic.function.factory.validation.token.SimpleLogicTokenValidator;
import logic.function.factory.validation.token.group.TokenGroup;
import logic.oldtype.map.MapToErrors;
import org.junit.Test;

import java.util.Arrays;

import static logic.function.factory.validation.checking.CheckerWithNumber.Number.MANY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Steven Weston
 */
public class SimpleLogicTokenValidatorTest {

	private final SimpleLogicLexer lexer;

	public SimpleLogicTokenValidatorTest() {
		this.lexer = new SimpleLogicLexer();
	}

	@Test
	public void testSimpleValidation() throws Exception {
		SimpleLogicTokenValidator validator = new SimpleLogicTokenValidator(
				Arrays.<CheckerWithNumber>asList(
						new VariableChecker(),
						new OperatorChecker("+"),
						new VariableChecker()
				)
		);
		MapToErrors<TokenGroup> errors;
		errors = validator.validateTokens(lexer.tokeniseString("(1 + 2)"));
		assertTrue("(1 + 2) should be valid", errors.allPassed());
	}

	@Test
	public void testSimpleValidationWithDisjunctiveCheckers() throws Exception {
		SimpleLogicTokenValidator validator = new SimpleLogicTokenValidator(
				Arrays.<CheckerWithNumber>asList(
						new FunctionOrVariableChecker(),
						new OperatorChecker("+"),
						new FunctionOrVariableChecker()
				)
		);
		MapToErrors<TokenGroup> errors;
		errors = validator.validateTokens(lexer.tokeniseString("(() + 2)"));
		assertTrue("(() + 2) should be valid", errors.allPassed());
	}

	@Test
	public void testSimpleValidationFails() throws Exception {
		SimpleLogicTokenValidator validator = new SimpleLogicTokenValidator(
				Arrays.<CheckerWithNumber>asList(
						new VariableChecker(),
						new OperatorChecker("+"),
						new VariableChecker()
				),
				FunctionFactory.STANDARD_BRACKETS
		);
		MapToErrors<TokenGroup> errors;
		errors = validator.validateTokens(lexer.tokeniseString("[∀ ∨ +]"));
		assertTrue("[∀ ∨ +] should be totally invalid", errors.allFailed());
		assertEquals(4, errors.getErrorMessages().size());
	}

	@Test
	public void testValidationWithManyChecker() throws Exception {
		SimpleLogicTokenValidator validator = new SimpleLogicTokenValidator(
				Arrays.<CheckerWithNumber>asList(
						new OperatorChecker("⋃"),
						new NumberedChecker(MANY, new VariableChecker())
				)
		);
		MapToErrors<TokenGroup> errors;
		errors = validator.validateTokens(lexer.tokeniseString("(⋃ x y z)"));
		assertTrue("(⋃ x y z) should be valid", errors.allPassed());
	}

	@Test
	public void testValidationWithCrazyCheckers() throws Exception {
		SimpleLogicTokenValidator validator = new SimpleLogicTokenValidator(
				Arrays.<CheckerWithNumber>asList(
						new OperatorChecker("⋃"),
						new NumberedChecker(MANY, new VariableChecker()),
						new OperatorChecker("+"),
						new NumberedChecker(MANY, new VariableChecker()),
						new OperatorChecker("−"),
						new NumberedChecker(MANY, new VariableChecker())
				)
		);
		MapToErrors<TokenGroup> errors;
		errors = validator.validateTokens(lexer.tokeniseString("(⋃ x y z + − a b c)"));
		assertTrue("(⋃ x y z + − a b c) should be valid", errors.allPassed());
	}
}
