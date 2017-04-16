package logic.function.factory.validation.token.group;

import reading.lexing.Token;

import java.util.ArrayList;
import java.util.List;

import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.*;

/**
 * @author Steven Weston
 */
public class TokenGroup {

	private final List<Token> group;

	public TokenGroup(Token token) {
		group = new ArrayList<>();
		group.add(token);
	}

	public TokenGroup(Token openBracket, Token closeBracket) {
		group = new ArrayList<>();
		group.add(openBracket);
		group.add(closeBracket);
	}

	public boolean representsFunction() {
		return group.size() == 2
				&& group.get(0).isOfType(OPEN_BRACKET)
				&& group.get(1).isOfType(CLOSE_BRACKET);
	}

	public boolean representsName() {
		return group.size() == 1
				&& group.get(0).isOfType(NAME);
	}

	public boolean representsOperator() {
		return group.size() == 1
				&& group.get(0).isOfType(OPERATOR);
	}

	public boolean representsQuantifier() {
		return group.size() == 1
				&& group.get(0).isOfType(QUANTIFIER);
	}

	public String getOpeningBracket() {
		return group.get(0).getValue();
	}

	public String getClosingBracket() {
		return group.get(1).getValue();
	}

	@Override
	public String toString() {
		return representsFunction() ? group.toString() : group.get(0).toString();
	}

	public String getValue() {
		return group.get(0).getValue();
	}
}
