package logic.function.reflexiveset.union;

import logic.Nameable;
import logic.factory.FactoryException;
import logic.function.Function;
import logic.function.reflexiveset.ReflexiveSetFunction;
import logic.function.reflexiveset.ReflexiveSetFunctionFactory;
import logic.set.Set;
import reading.lexing.Token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.*;

/**
 * @author Steven Weston
 */
public class UnionFactory<T extends Nameable> implements ReflexiveSetFunctionFactory<T> {
	@Override
	public Function<Set<T>, Set<T>> createElement(List<Token> tokens, List<Function<?, ?>> functions) throws FactoryException {
		if (matchesBinaryUnionWithStrings(tokens, functions)) {
			return new Union<>(Arrays.asList(tokens.get(0).getValue(), tokens.get(2).getValue()), convert(functions));
		} else if (matchesBinaryUnionWithFirstFunction(tokens, functions)) {
			return new Union<>(Arrays.asList(tokens.get(3).getValue()), convert(functions));
		} else if (matchesBinaryUnionWithSecondFunction(tokens, functions)) {
			return new Union<>(Arrays.asList(tokens.get(0).getValue()), convert(functions));
		} else if (matchesBinaryUnionWithBothFunctions(tokens, functions)) {
			return new Union<>(null, convert(functions));
		} else {
			try {return constructNAryUnion(tokens, functions);}
			catch (UnionFactoryException ignored) {
				throw new FactoryException("Could not create Union");
			}
		}
	}

	private Union<T> constructNAryUnion(List<Token> tokens, List<Function<?, ?>> functions) throws UnionFactoryException {
		Collection<String> strings = new ArrayList<>();
		Collection<ReflexiveSetFunction<T>> reflexiveFunctions = new ArrayList<>();
		int currentFunctionIndex = 0;
		int currentTokenIndex = 0;
		Token lastToken = null;
		for (Token token : tokens) {
			if (token.isOfType(CLOSE_PAREN)) {
				if (lastToken == null || !lastToken.isOfType(OPEN_PAREN)) {
					throw new UnionFactoryException();
				}
				reflexiveFunctions.add((ReflexiveSetFunction<T>) functions.get(currentFunctionIndex++));
			} else if (token.isOfType(OPEN_PAREN)) {

			} else if (token.isOfType(OPERATOR)) {
				if (!token.getValue() .equals(Union.N_ARY_SYMBOL)
						|| currentTokenIndex != 0) {
					throw new UnionFactoryException();
				}
			} else if (token.isOfType(NAME)) {
				strings.add(token.getValue());
				currentFunctionIndex++;
			}
			currentTokenIndex++;
			lastToken = token;
		}
		return new Union<>(strings, reflexiveFunctions);
	}

	List<ReflexiveSetFunction<T>> convert(List<Function<?, ?>> functions) {
		if (functions == null) {
			return null;
		}
		List<ReflexiveSetFunction<T>> output = new ArrayList<>();
		for (Function<?, ?> function : functions) {
			output.add((ReflexiveSetFunction<T>) function);
		}
		return output;
	}

	@Override
	public Function<Set<T>, Set<T>> createElement(List<Token> tokens) throws FactoryException {
		return createElement(tokens, null);
	}

	boolean matchesBinaryUnionWithStrings(List<Token> tokens, List<Function<?, ?>> functions) {
		return tokens != null
				&& tokens.size() == 3
				&& tokens.get(0).isOfType(NAME)
				&& tokens.get(1).isOfType(OPERATOR)
				&& tokens.get(1).getValue().equals(Union.BINARY_SYMBOL)
				&& tokens.get(2).isOfType(NAME)
				&& (functions == null
					|| functions.isEmpty()
					|| (functions.size() == 2
						&& functions.get(0) == null
						&& functions.get(1) == null));
	}

	boolean matchesBinaryUnionWithFirstFunction(List<Token> tokens, List<Function<?, ?>> functions) {
		return tokens != null
				&& tokens.size() == 4
				&& tokens.get(0).isOfType(OPEN_PAREN)
				&& tokens.get(1).isOfType(CLOSE_PAREN)
				&& tokens.get(2).isOfType(OPERATOR)
				&& tokens.get(2).getValue().equals(Union.BINARY_SYMBOL)
				&& tokens.get(3).isOfType(NAME)
				&& functions != null
				&& functions.size() == 2
				&& functions.get(0) != null
				&& functions.get(0) instanceof ReflexiveSetFunction<?>
				&& functions.get(1) == null;
	}

	boolean matchesBinaryUnionWithSecondFunction(List<Token> tokens, List<Function<?, ?>> functions) {
		return tokens != null
				&& tokens.size() == 4
				&& tokens.get(0).isOfType(NAME)
				&& tokens.get(1).isOfType(OPERATOR)
				&& tokens.get(1).getValue().equals(Union.BINARY_SYMBOL)
				&& tokens.get(2).isOfType(OPEN_PAREN)
				&& tokens.get(3).isOfType(CLOSE_PAREN)
				&& functions != null
				&& functions.size() == 2
				&& functions.get(0) == null
				&& functions.get(1) != null
				&& functions.get(1) instanceof ReflexiveSetFunction<?>;
	}

	boolean matchesBinaryUnionWithBothFunctions(List<Token> tokens, List<Function<?, ?>> functions) {
		return tokens != null
				&& tokens.size() == 5
				&& tokens.get(0).isOfType(OPEN_PAREN)
				&& tokens.get(1).isOfType(CLOSE_PAREN)
				&& tokens.get(2).isOfType(OPERATOR)
				&& tokens.get(2).getValue().equals(Union.BINARY_SYMBOL)
				&& tokens.get(3).isOfType(OPEN_PAREN)
				&& tokens.get(4).isOfType(CLOSE_PAREN)
				&& functions != null
				&& functions.size() == 2
				&& functions.get(0) != null
				&& functions.get(0) instanceof ReflexiveSetFunction<?>
				&& functions.get(1) != null
				&& functions.get(1) instanceof ReflexiveSetFunction<?>;
	}

	private static class UnionFactoryException extends Exception {}
}
