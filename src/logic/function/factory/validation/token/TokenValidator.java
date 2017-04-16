package logic.function.factory.validation.token;

import logic.function.factory.validation.token.group.TokenGroup;
import logic.oldtype.map.MapToErrors;
import reading.lexing.Token;

import java.util.List;

/**
 * @author Steven Weston
 */
public interface TokenValidator {

	/**
	 * Given the list of tokens (including the outer brackets), generate a map of validation errors encountered.
	 * @param tokens The list of tokens to validate
	 * @return A map containing error messages for invalid tokens
	 * @throws TokenValidationException when something very wrong happens (the tokens are not well-formed)
	 */
	MapToErrors<TokenGroup> validateTokens(List<Token> tokens) throws TokenValidationException;
}
