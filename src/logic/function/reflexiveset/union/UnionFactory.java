package logic.function.reflexiveset.union;

import logic.Nameable;
import logic.factory.FactoryException;
import logic.function.Function;
import logic.function.factory.BinaryValidator;
import logic.function.factory.ValidationResult;
import logic.function.reflexiveset.ReflexiveSetFunction;
import logic.function.reflexiveset.ReflexiveSetFunctionFactory;
import logic.function.reflexiveset.identity.SetIdentityFunction;
import logic.set.Set;
import reading.lexing.Token;

import java.util.*;

import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.*;
import static logic.function.factory.ValidationResult.ValidationType.FUNCTION;
import static logic.function.factory.ValidationResult.ValidationType.TOKEN;

/**
 * @author Steven Weston
 */
public class UnionFactory<T extends Nameable> implements ReflexiveSetFunctionFactory<T> {
	private BinaryValidator binaryValidator;

	public UnionFactory() {
		binaryValidator = new BinaryValidator(
				ReflexiveSetFunction.class,
				Collections.singletonList(Union.BINARY_SYMBOL),
				ReflexiveSetFunction.class);
	}

	@Override
	public Function<Set<T>, Set<T>> createElement(List<Token> tokens, List<Function<?, ?>> functions) throws FactoryException {
		ValidationResult result = binaryValidator.validate(tokens, functions);
		if (result.isValid()) {
			ReflexiveSetFunction<T> parameter1 = null;
			if (result.get(0).equals(TOKEN)) {
				parameter1 = new SetIdentityFunction<>(tokens.get(0).getValue());
			} else if (result.get(0).equals(FUNCTION)) {
				parameter1 = (ReflexiveSetFunction<T>) functions.get(0);
			}
			ReflexiveSetFunction<T> parameter2 = null;
			if (result.get(1).equals(TOKEN)) {
				parameter2 = new SetIdentityFunction<>(tokens.get(result.get(0).equals(TOKEN) ? 2 : 3).getValue());
			} else if (result.get(1).equals(FUNCTION)) {
				parameter2 = (ReflexiveSetFunction<T>) functions.get(1);
			}
			return new Union<>(null, Arrays.asList(parameter1, parameter2));
		} else {
			try {return constructMultaryUnion(tokens, functions);}
			catch (UnionFactoryException ignored) {
				throw new FactoryException("Could not create Union");
			}
		}
	}

	private Union<T> constructMultaryUnion(List<Token> tokens, List<Function<?, ?>> functions) throws UnionFactoryException {
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
				if (!token.getValue() .equals(Union.MULTARY_SYMBOL)
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

	@Override
	public Function<Set<T>, Set<T>> createElement(List<Token> tokens) throws FactoryException {
		return createElement(tokens, null);
	}

	private static class UnionFactoryException extends Exception {}
}
