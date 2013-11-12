package logic.function.factory.oldvalidation.group;

import logic.factory.SimpleLogicLexerToken;
import logic.function.Function;
import logic.function.factory.ValidationException;
import logic.function.factory.validation.group.TokenGroup;

import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.*;
import static org.junit.Assert.fail;

/**
 * @author Steven Weston
 */
public class GroupValidatorTest {
	protected GroupValidator validator;

	protected void expectValidationException(TokenGroup group, Function function) {
		try {
			validator.validate(group, function);
			fail();
		} catch (ValidationException e) {

		}
	}

	protected TokenGroup newNameToken(String word) {
		return new TokenGroup(new SimpleLogicLexerToken(NAME, word));
	}

	protected TokenGroup newOperatorToken(String symbol) {
		return new TokenGroup(new SimpleLogicLexerToken(OPERATOR, symbol));
	}

	protected TokenGroup newFunctionGroup(String openBracket, String closeBracket) {
		return new TokenGroup(
				new SimpleLogicLexerToken(OPEN_BRACKET, openBracket),
				new SimpleLogicLexerToken(CLOSE_BRACKET, closeBracket)
		);
	}
}
