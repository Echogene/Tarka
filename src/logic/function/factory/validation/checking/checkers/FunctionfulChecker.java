package logic.function.factory.validation.checking.checkers;

import javafx.util.Pair;
import logic.function.Function;
import logic.function.factory.validation.function.FunctionValidationException;
import logic.function.factory.validation.token.TokenValidationException;
import logic.function.factory.validation.token.group.TokenGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Steven Weston
 */
public class FunctionfulChecker extends AtomicChecker {

	private final List<Pair<String, String>> acceptedBracketPairs;
	private final List<Class> acceptedFunctionClasses;

	public FunctionfulChecker(
			List<Pair<String, String>> acceptedBracketPairs,
			List<Class> acceptedFunctionClasses
	) {
		this.acceptedBracketPairs = acceptedBracketPairs;
		this.acceptedFunctionClasses = acceptedFunctionClasses;
	}

	public FunctionfulChecker() {
		this(new ArrayList<>(), new ArrayList<>());
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
	public void check(Function<?, ?> function) throws FunctionValidationException {
		if (!acceptedFunctionClasses.isEmpty()) {
			if (!acceptedFunctionClasses.contains(function.getClass())) {
				throw new FunctionValidationException(function.toString() + " was not in any of " + acceptedFunctionClasses.toString() + ".");
			}
		}
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " where in " + acceptedBracketPairs;
	}
}
