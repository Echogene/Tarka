package logic.function.factory.validation.checking.checkers;

import javafx.util.Pair;
import logic.function.Function;
import logic.function.factory.validation.function.FunctionValidationException;
import logic.function.factory.validation.token.TokenValidationException;
import logic.function.factory.validation.token.group.TokenGroup;
import util.CollectionUtils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import static java.text.MessageFormat.format;

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
				throw new TokenValidationException(
						MessageFormat.format(
								"{0} was not in {1}.",
								tokenGroup.toString(),
								acceptedBracketPairs.toString()
						)
				);
			}
		}
	}

	@Override
	public void check(Function<?, ?> function) throws FunctionValidationException {
		if (!acceptedFunctionClasses.isEmpty()) {
			boolean classFound = false;
			for (Class clazz : acceptedFunctionClasses) {
				if (clazz.isInstance(function)) {
					classFound = true;
					break;
				}
			}
			if (!classFound) {
				throw new FunctionValidationException(
						MessageFormat.format(
								"{0} was not in any of {1}.",
								function.toString(),
								CollectionUtils.simpleNames(acceptedFunctionClasses)
						)
				);
			}
		}
	}

	@Override
	public String toString() {
		return format(
				"{0} where in {1} surrounded with {2}",
				getClass().getSimpleName(),
				CollectionUtils.simpleNames(acceptedFunctionClasses),
				acceptedBracketPairs.isEmpty() ? "any brackets" : acceptedBracketPairs
		);
	}
}
