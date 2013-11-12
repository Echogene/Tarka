package logic.function.factory.validation;

import javafx.util.Pair;
import logic.factory.SimpleLogicLexer;
import logic.function.factory.validation.group.NumberedTokenGroupChecker;
import logic.function.factory.validation.group.TokenGroup;
import logic.function.factory.validation.group.TokenGroupCheckerWithNumber;
import logic.function.factory.validation.group.checkers.FunctionOrVariableChecker;
import logic.function.factory.validation.group.checkers.OperatorChecker;
import logic.function.factory.validation.group.checkers.VariableChecker;
import logic.type.map.MapToErrors;
import org.junit.Test;

import java.util.Arrays;

import static logic.function.factory.validation.group.TokenGroupCheckerWithNumber.Number.MANY;
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
				Arrays.<TokenGroupCheckerWithNumber>asList(
						new VariableChecker(),
						new OperatorChecker("+"),
						new VariableChecker()
				)
		);
		MapToErrors<TokenGroup> errors;
		errors = validator.validate(lexer.tokeniseString("(1 + 2)"));
		assertTrue("(1 + 2) should be valid", errors.allPassed());
	}

	@Test
	public void testSimpleValidationWithDisjunctiveCheckers() throws Exception {
		SimpleLogicTokenValidator validator = new SimpleLogicTokenValidator(
				Arrays.<TokenGroupCheckerWithNumber>asList(
						new FunctionOrVariableChecker(),
						new OperatorChecker("+"),
						new FunctionOrVariableChecker()
				)
		);
		MapToErrors<TokenGroup> errors;
		errors = validator.validate(lexer.tokeniseString("(() + 2)"));
		assertTrue("(() + 2) should be valid", errors.allPassed());
	}

	@Test
	public void testSimpleValidationFails() throws Exception {
		SimpleLogicTokenValidator validator = new SimpleLogicTokenValidator(
				Arrays.<TokenGroupCheckerWithNumber>asList(
						new VariableChecker(),
						new OperatorChecker("+"),
						new VariableChecker()
				),
				Arrays.asList(new Pair<>("(", ")"))
		);
		MapToErrors<TokenGroup> errors;
		errors = validator.validate(lexer.tokeniseString("[∀ ∨ +]"));
		assertTrue("[∀ ∨ +] should be totally invalid", errors.allFailed());
		assertEquals(4, errors.getErrorMessages().size());
	}

	@Test
	public void testValidationWithManyChecker() throws Exception {
		SimpleLogicTokenValidator validator = new SimpleLogicTokenValidator(
				Arrays.<TokenGroupCheckerWithNumber>asList(
						new OperatorChecker("⋃"),
						new NumberedTokenGroupChecker(MANY, new VariableChecker())
				)
		);
		MapToErrors<TokenGroup> errors;
		errors = validator.validate(lexer.tokeniseString("(⋃ x y z)"));
		assertTrue("(⋃ x y z) should be valid", errors.allPassed());
	}

	@Test
	public void testValidationWithCrazyCheckers() throws Exception {
		SimpleLogicTokenValidator validator = new SimpleLogicTokenValidator(
				Arrays.<TokenGroupCheckerWithNumber>asList(
						new OperatorChecker("⋃"),
						new NumberedTokenGroupChecker(MANY, new VariableChecker()),
						new OperatorChecker("+"),
						new NumberedTokenGroupChecker(MANY, new VariableChecker()),
						new OperatorChecker("−"),
						new NumberedTokenGroupChecker(MANY, new VariableChecker())
				)
		);
		MapToErrors<TokenGroup> errors;
		errors = validator.validate(lexer.tokeniseString("(⋃ x y z + − a b c)"));
		assertTrue("(⋃ x y z + − a b c) should be valid", errors.allPassed());
	}
}
