package logic.function.factory.validation.token;

import javafx.util.Pair;
import logic.function.factory.validation.checking.CheckerWithNumber;
import logic.function.factory.validation.checking.checkers.FunctionfulChecker;
import logic.function.factory.validation.token.group.TokenGroup;
import logic.oldtype.map.MapToErrors;
import reading.lexing.Token;
import util.CollectionUtils;
import util.CurrentIterator;

import java.util.ArrayList;
import java.util.List;

import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.CLOSE_BRACKET;
import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.OPEN_BRACKET;
import static logic.function.factory.validation.checking.CheckerWithNumber.Number.ONE;

/**
 * @author Steven Weston
 */
public class SimpleLogicTokenValidator implements TokenValidator {

	private final List<CheckerWithNumber> checkers;
	private final List<Pair<String,String>> acceptedBracketPairs;
	private CurrentIterator<CheckerWithNumber> currentChecker;

	public SimpleLogicTokenValidator(
			List<CheckerWithNumber> checkers,
			List<Pair<String, String>> acceptedBracketPairs
	) {
		this.checkers = new ArrayList<>(checkers);
		this.acceptedBracketPairs = acceptedBracketPairs;
		if (!this.acceptedBracketPairs.isEmpty()) {
			FunctionfulChecker outerBracketChecker = new FunctionfulChecker(acceptedBracketPairs, new ArrayList<>());
			this.checkers.add(0, outerBracketChecker);
		}
	}

	public SimpleLogicTokenValidator(List<CheckerWithNumber> checkers) {
		this(checkers, new ArrayList<>());
	}

	@Override
	public synchronized MapToErrors<TokenGroup> validateTokens(List<Token> tokens) throws TokenValidationException {
		resetIterator();
		List<TokenGroup> groups = new ArrayList<>();
		if (!this.acceptedBracketPairs.isEmpty()) {
			groups.add(new TokenGroup(tokens.get(0), tokens.get(tokens.size() - 1)));
		}
		groups.addAll(groupTokens(CollectionUtils.stripFirstAndLast(tokens)));
		MapToErrors<TokenGroup> errors = new MapToErrors<>(groups, this::checkToken);
		if (currentChecker.hasNext()
				|| (currentChecker.current() != null && currentChecker.current().getNumber() == ONE)) {
			throw new TokenValidationException("There were too many checkers.");
		}
		return errors;
	}

	private void resetIterator() {
		currentChecker = new CurrentIterator<>(checkers.iterator());
	}

	private synchronized void checkToken(TokenGroup group) throws TokenValidationException {
		while (true) {
			CheckerWithNumber current = currentChecker.current();
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

	private List<TokenGroup> groupTokens(List<Token> tokens) throws TokenValidationException {
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
