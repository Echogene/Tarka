package logic.function.reflexive.identity;

import logic.Nameable;
import logic.factory.FactoryException;
import logic.function.Function;
import logic.function.reflexive.ReflexiveFunction;
import logic.function.reflexive.ReflexiveFunctionFactory;
import reading.lexing.Token;

import java.util.List;

import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.*;

/**
 * @author Steven Weston
 */
public class IdentityFunctionFactory<T extends Nameable> implements ReflexiveFunctionFactory<T> {
	@Override
	public Function<T, T> createElement(List<Token> tokens, List<Function<?, ?>> functions) throws FactoryException {
		if (functions != null && functions.size() == 2) {
			functions = functions.subList(1, 2);
		}
		if (matchesStandardForm(tokens, functions)) {
			return new IdentityFunction<>(tokens.get(1).getValue());
		} else if (matchesSingleNameToken(tokens, functions)) {
			return new IdentityFunction<>(tokens.get(0).getValue());
		} else if (matchesStandardFormWithFunction(tokens, functions)
				|| matchesSingleFunction(tokens, functions)) {
			return new IdentityFunction<>((ReflexiveFunction<T>) (Object) functions.get(0));
		}
		throw new FactoryException("Could not create IdentityFunction");
	}

	@Override
	public Function<T, T> createElement(List<Token> tokens) throws FactoryException {
		return createElement(tokens, null);
	}

	boolean matchesStandardForm(List<Token> tokens, List<Function<?, ?>> functions) {
		return tokens.size() == 2
				&& tokens.get(0).isOfType(NAME)
				&& tokens.get(0).getValue().equals(IdentityFunction.IDENTITY_NAME)
				&& tokens.get(1).isOfType(NAME)
				&& noFunctions(functions);
	}

	boolean matchesSingleNameToken(List<Token> tokens, List<Function<?, ?>> functions) {
		return tokens.size() == 1
				&& tokens.get(0).isOfType(NAME)
				&& noFunctions(functions);
	}

	boolean noFunctions(List<Function<?, ?>> functions) {
		return functions == null
				|| (functions.size() == 1
					&& functions.get(0) == null);
	}

	boolean matchesStandardFormWithFunction(List<Token> tokens, List<Function<?, ?>> functions) {
		return tokens.size() == 3
				&& tokens.get(0).isOfType(NAME)
				&& tokens.get(0).getValue().equals(IdentityFunction.IDENTITY_NAME)
				&& tokens.get(1).isOfType(OPEN_PAREN)
				&& tokens.get(2).isOfType(CLOSE_PAREN)
				&& oneFunction(functions);
	}

	boolean matchesSingleFunction(List<Token> tokens, List<Function<?, ?>> functions) {
		return tokens.size() == 2
				&& tokens.get(0).isOfType(OPEN_PAREN)
				&& tokens.get(1).isOfType(CLOSE_PAREN)
				&& oneFunction(functions);
	}

	boolean oneFunction(List<Function<?, ?>> functions) {
		return functions != null
				&& functions.size() == 1
				&& functions.get(0) != null
				&& functions.get(0) instanceof ReflexiveFunction<?>;
	}
}
