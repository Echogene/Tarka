package logic.function.factory.validation;

import javafx.util.Pair;
import logic.function.factory.oldvalidation.group.TokenGroup;
import logic.function.factory.validation.group.TokenGroupCheckerWithNumber;
import logic.function.factory.validation.group.checkers.FunctionChecker;
import logic.type.map.MapToErrors;
import reading.lexing.Token;
import util.CollectionUtils;
import util.CurrentIterator;

import java.util.ArrayList;
import java.util.List;

import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.CLOSE_BRACKET;
import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.OPEN_BRACKET;
import static logic.function.factory.validation.group.TokenGroupCheckerWithNumber.Number.ONE;

/**
 * @author Steven Weston
 */
public class SimpleLogicTokenValidator implements TokenValidator {

	private final CurrentIterator<TokenGroupCheckerWithNumber> currentChecker;
	private final List<Pair<String,String>> acceptedBracketPairs;

	public SimpleLogicTokenValidator(
			List<TokenGroupCheckerWithNumber> checkers,
			List<Pair<String, String>> acceptedBracketPairs
	) {
		List<TokenGroupCheckerWithNumber> extendedCheckers = new ArrayList<>(checkers);
		this.acceptedBracketPairs = acceptedBracketPairs;
		if (!this.acceptedBracketPairs.isEmpty()) {
			FunctionChecker outerBracketChecker = new FunctionChecker(acceptedBracketPairs);
			extendedCheckers.add(0, outerBracketChecker);
		}
		currentChecker = new CurrentIterator<>(extendedCheckers.iterator());
	}

	public SimpleLogicTokenValidator(List<TokenGroupCheckerWithNumber> checkers) {
		this(checkers, new ArrayList<>());
	}

	@Override
	public MapToErrors<TokenGroup> validate(List<Token> tokens) throws TokenValidationException {
		List<TokenGroup> groups = new ArrayList<>();
		if (!this.acceptedBracketPairs.isEmpty()) {
			groups.add(new TokenGroup(tokens.get(0), tokens.get(tokens.size() - 1)));
		}
		groups.addAll(groupTokens(CollectionUtils.stripFirstAndLast(tokens)));
		MapToErrors<TokenGroup> errors = new MapToErrors<>(groups, this::checkToken);
		return errors;
	}

	private void checkToken(TokenGroup group) throws TokenValidationException {
		while (true) {
			TokenGroupCheckerWithNumber current = currentChecker.current();
			if (current == null) {
				throw new TokenValidationException("There weren't enough checkers.");
			}
			try {
				current.check(group);
				if (ONE == current.getNumber()) {
					currentChecker.next();
				}
				return;
			} catch (TokenValidationException e) {
				if (ONE == current.getNumber()) {
					currentChecker.next();
					throw e;
				} else {
					currentChecker.next();
				}
			}
		}
	}

	public List<TokenGroup> groupTokens(List<Token> tokens) throws TokenValidationException {
		List<TokenGroup> tokenGroups = new ArrayList<>();
		Token lastToken = null;
		for (Token token : tokens) {
			if (token.isOfType(CLOSE_BRACKET)) {
				if (lastToken == null || !lastToken.isOfType(OPEN_BRACKET)) {
					throw new TokenValidationException("Closing brackets must be immediately preceded by opening brackets.");
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
