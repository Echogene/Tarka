package logic.function.factory.validation;

import logic.function.factory.ValidationException;
import logic.function.factory.validation.group.TokenGroup;
import reading.lexing.Token;
import util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.CLOSE_BRACKET;
import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.OPEN_BRACKET;

/**
 * @author Steven Weston
 */
public class Validator {
	private List<Token> tokens;
	private final List<String> acceptedOpeningBrackets;
	private final List<String> acceptedClosingBrackets;
	private List<TokenGroup> tokenGroups;

	public Validator(List<String> acceptedOpeningBrackets, List<String> acceptedClosingBrackets) {
		this.acceptedOpeningBrackets = acceptedOpeningBrackets;
		this.acceptedClosingBrackets = acceptedClosingBrackets;
	}

	public void validateAndStripBrackets() throws ValidationException {
		if (tokens.size() < 2) {
			throw new ValidationException("There were fewer than two tokens.");
		}
		Token firstToken = tokens.get(0);
		Token lastToken = tokens.get(tokens.size() - 1);
		if (!firstToken.isOfType(OPEN_BRACKET)) {
			throw new ValidationException("The first token—" + firstToken.toString() + "—was not an opening bracket.");
		} else if (!acceptedOpeningBrackets.contains(firstToken.getValue())) {
			throw new ValidationException("The first token—" + firstToken.toString() + "—was not in " + acceptedOpeningBrackets.toString() + ".");
		} else if (!lastToken.isOfType(CLOSE_BRACKET)) {
			throw new ValidationException("The last token—" + lastToken.toString() + "—was not an opening bracket.");
		} else if (!acceptedClosingBrackets.contains(lastToken.getValue())) {
			throw new ValidationException("The first token—" + lastToken.toString() + "—was not in " + acceptedClosingBrackets.toString() + ".");
		}
		tokens = CollectionUtils.stripFirstAndLast(tokens);
	}

	public List<TokenGroup> groupTokens(List<Token> tokens) throws ValidationException {
		tokenGroups = new ArrayList<>();
		Token lastToken = null;
		for (Token token : tokens) {
			if (token.isOfType(CLOSE_BRACKET)) {
				if (lastToken == null || !lastToken.isOfType(OPEN_BRACKET)) {
					throw new ValidationException("Closing brackets must be immediately preceded by opening brackets.");
				}
				tokenGroups.add(new TokenGroup(lastToken, token));
			} else if (!token.isOfType(OPEN_BRACKET)) {
				tokenGroups.add(new TokenGroup(token));
			}
			lastToken = token;
		}
		return tokenGroups;
	}
}
