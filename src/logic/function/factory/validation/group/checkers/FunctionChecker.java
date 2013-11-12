package logic.function.factory.validation.group.checkers;

import javafx.util.Pair;
import logic.function.factory.oldvalidation.group.TokenGroup;
import logic.function.factory.validation.TokenValidationException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Steven Weston
 */
public class FunctionChecker extends AtomicChecker {

	private final List<Pair<String, String>> acceptedBracketPairs;

	public FunctionChecker(List<Pair<String, String>> acceptedBracketPairs) {
		this.acceptedBracketPairs = acceptedBracketPairs;
	}

	public FunctionChecker() {
		this(new ArrayList<>());
	}

	@Override
	public void check(TokenGroup tokenGroup) throws TokenValidationException {
		if (!tokenGroup.representsFunction()) {
			throw new TokenValidationException(tokenGroup.toString() + " did not represent a function.");
		}
		if (!acceptedBracketPairs.isEmpty()) {
			Pair<String, String> pair = new Pair<>(tokenGroup.getOpeningBracket(), tokenGroup.getClosingBracket());
			if (!acceptedBracketPairs.contains(pair)) {
				throw new TokenValidationException(tokenGroup.toString() + " was not in " + acceptedBracketPairs.toString() + ".");
			}
		}
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " where in " + acceptedBracketPairs;
	}
}
