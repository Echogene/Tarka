package logic.function.reflexiveset.identity;

import logic.Nameable;
import logic.factory.FactoryException;
import logic.function.Function;
import logic.function.reflexiveset.ReflexiveSetFunction;
import logic.function.reflexiveset.ReflexiveSetFunctionFactory;
import logic.set.Set;
import reading.lexing.Token;

import java.util.List;

import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.*;

/**
 * @author Steven Weston
 */
public class SetIdentityFunctionFactory<T extends Nameable> extends ReflexiveSetFunctionFactory<T> {
	@Override
	public Function<T, Set<T>> createElement
			(List<Token> tokens,
			 List<Function<?, ?>> functions)
			throws FactoryException {

		tokens = validateAndStripParentheses(tokens);
		if(functions != null && functions.size() == 2) {
			functions = functions.subList(1, 2);
		}
		if (matchesStandardForm(tokens, functions)) {
			return new SetIdentityFunction<>(tokens.get(1).getValue());
		} else if (matchesStandardFormWithFunction(tokens, functions)) {
			return new SetIdentityFunction<>((ReflexiveSetFunction<T>) functions.get(0));
		}
		throw new FactoryException("Could not create SetIdentityFunction");
	}

	boolean matchesStandardForm(List<Token> tokens, List<Function<?, ?>> functions) {
		return tokens.size() == 2
				&& tokens.get(0).isOfType(NAME)
				&& tokens.get(0).getValue().equals(SetIdentityFunction.SET_IDENTITY_NAME)
				&& tokens.get(1).isOfType(NAME)
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
				&& tokens.get(0).getValue().equals(SetIdentityFunction.SET_IDENTITY_NAME)
				&& tokens.get(1).isOfType(OPEN_BRACKET)
				&& tokens.get(2).isOfType(CLOSE_BRACKET)
				&& oneFunction(functions);
	}

	boolean oneFunction(List<Function<?, ?>> functions) {
		return functions != null
				&& functions.size() == 1
				&& functions.get(0) != null
				&& functions.get(0) instanceof ReflexiveSetFunction<?>;
	}
}