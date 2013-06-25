package logic.evaluable.predicate;

import logic.Nameable;
import logic.factory.FactoryException;
import logic.function.Function;
import logic.function.reflexive.ReflexiveFunction;
import reading.lexing.Token;

import java.util.List;

import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.*;

/**
 * A {@code Factory} for creating {@code EqualityPredicate}s.
 * @author Steven Weston
 */
public class EqualityPredicateFactory<T extends Nameable> implements PredicateFactory<T> {
	@Override
	public Function<T, Boolean> createElement(List<Token> tokens) throws FactoryException {
		return createElement(tokens, null);
	}

	@Override
	public Function<T, Boolean> createElement(List<Token> tokens, List<Function<?, ?>> functions) throws FactoryException {
		if (matchesTwoNameTokens(tokens, functions)) {
			return new EqualityPredicate<>(tokens.get(0).getValue(), tokens.get(2).getValue());
		} else if (matchesFirstFunctionSecondNameToken(tokens, functions)) {
			return new EqualityPredicate<>((ReflexiveFunction<T>) functions.get(0), tokens.get(3).getValue());
		} else if (matchesFirstNameTokenSecondFunction(tokens, functions)) {
			return new EqualityPredicate<>(tokens.get(0).getValue(), (ReflexiveFunction<T>) functions.get(1));
		} else if (matchesTwoFunctions(tokens, functions)) {
			return new EqualityPredicate<>(
					(ReflexiveFunction<T>) functions.get(0),
					(ReflexiveFunction<T>) functions.get(1));
		}
		throw new FactoryException("Could not create EqualityPredicate");
	}

	/**
	 * Return whether the given {@code Token}s and {@code Function}s match the pattern of an equality predicate formed
	 * by two name tokens (i.e. of the form "x = y").
	 * @param tokens A list of {@code Token}s to match against.
	 * @param functions A list of {@code Function}s to match against.
	 * @return Whether there are two name tokens and no functions.
	 */
	boolean matchesTwoNameTokens(List<Token> tokens, List<Function<?, ?>> functions) {
		return tokens.size() == 3
				&& tokens.get(0).isOfType(NAME)
				&& tokens.get(1).isOfType(OPERATOR)
				&& tokens.get(1).getValue().equals(EqualityPredicate.EQUALITY_STRING)
				&& tokens.get(2).isOfType(NAME)
				&& (functions == null
					|| (functions.size() == 2
						&& matchFunction(0, false, functions)
						&& matchFunction(1, false, functions)));
	}

	/**
	 * Return whether the given {@code Token}s and {@code Function}s match the pattern of an equality predicate formed
	 * by one function and one name token (i.e. of the form "x = g").
	 * @param tokens A list of {@code Token}s to match against.
	 * @param functions A list of {@code Function}s to match against.
	 * @return Whether there is one function and one name token.
	 */
	boolean matchesFirstFunctionSecondNameToken(List<Token> tokens, List<Function<?, ?>> functions) {
		return tokens.size() == 4
				&& tokens.get(0).isOfType(OPEN_PAREN)
				&& tokens.get(1).isOfType(CLOSE_PAREN)
				&& tokens.get(2).isOfType(OPERATOR)
				&& tokens.get(2).getValue().equals(EqualityPredicate.EQUALITY_STRING)
				&& tokens.get(3).isOfType(NAME)
				&& functions != null
				&& functions.size() == 2
				&& matchFunction(0, true, functions)
				&& matchFunction(1, false, functions);
	}

	/**
	 * Return whether the given {@code Token}s and {@code Function}s match the pattern of an equality predicate formed
	 * by one name token and one function (i.e. of the form "f = y").
	 * @param tokens A list of {@code Token}s to match against.
	 * @param functions A list of {@code Function}s to match against.
	 * @return Whether there is one name token and one function
	 */
	boolean matchesFirstNameTokenSecondFunction(List<Token> tokens, List<Function<?, ?>> functions) {
		return tokens.size() == 4
				&& tokens.get(0).isOfType(NAME)
				&& tokens.get(1).isOfType(OPERATOR)
				&& tokens.get(1).getValue().equals(EqualityPredicate.EQUALITY_STRING)
				&& tokens.get(2).isOfType(OPEN_PAREN)
				&& tokens.get(3).isOfType(CLOSE_PAREN)
				&& functions != null
				&& functions.size() == 2
				&& matchFunction(0, false, functions)
				&& matchFunction(1, true, functions);
	}

	/**
	 * Return whether the given {@code Token}s and {@code Function}s match the pattern of an equality predicate formed
	 * by two functions (i.e. of the form "f = g").
	 * @param tokens A list of {@code Token}s to match against.
	 * @param functions A list of {@code Function}s to match against.
	 * @return Whether there are two functions and no name tokens.
	 */
	public boolean matchesTwoFunctions(List<Token> tokens, List<Function<?, ?>> functions) {
		return tokens.size() == 5
				&& tokens.get(0).isOfType(OPEN_PAREN)
				&& tokens.get(1).isOfType(CLOSE_PAREN)
				&& tokens.get(2).isOfType(OPERATOR)
				&& tokens.get(2).getValue().equals(EqualityPredicate.EQUALITY_STRING)
				&& tokens.get(3).isOfType(OPEN_PAREN)
				&& tokens.get(4).isOfType(CLOSE_PAREN)
				&& functions != null
				&& functions.size() == 2
				&& matchFunction(0, true, functions)
				&& matchFunction(1, true, functions);
	}

	/**
	 * Return whether the given index in a given {@code List} entails a {@code Function} whose state of existence
	 * matches a given {@code boolean}.
	 * @param index The index in the {@code List} to match.
	 * @param matchExistence {@code true} to match that the function exists and {@code false} to match that the
	 *                       function is null.
	 * @param functions A {@code List} of {@code Function}s
	 * @return Whether there are two functions and no name tokens.
	 */
	boolean matchFunction(int index, boolean matchExistence, List<Function<?, ?>> functions) {
		Function<?, ?> function = functions.get(index);
		if (matchExistence) {
			return function != null
					&& function instanceof ReflexiveFunction<?>;
		} else {
			return function == null;
		}
	}
}
