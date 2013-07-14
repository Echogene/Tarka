package logic.function.reflexive.assignment;

import logic.Nameable;
import logic.factory.FactoryException;
import logic.function.Function;
import logic.function.factory.FunctionFactory;
import logic.function.factory.ReflexiveFunctionConstructorFromString;
import logic.function.reflexive.ReflexiveFunction;
import logic.function.reflexive.identity.IdentityFunction;
import logic.function.reflexive.identity.IdentityFunctionConstructorFromString;
import reading.lexing.Token;

import java.util.List;

import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.*;

/**
 * @author Steven Weston
 */
public class ReflexiveAssignmentFactory<T extends Nameable> extends FunctionFactory<T, T> {
	public static final String WHERE = "where";
	public static final String IS = "is";
	private final ReflexiveFunctionConstructorFromString<IdentityFunction<T>> constructor = new IdentityFunctionConstructorFromString();

	@Override
	public Function<T, T> createElement(List<Token> tokens, List<Function<?, ?>> functions) throws FactoryException {
		int tokenIndex = 0;
		ReflexiveFunction<T> evaluee;
		if (tokens.get(tokenIndex).isOfType(OPEN_PAREN)) {
			if (!tokens.get(tokenIndex + 1).isOfType(CLOSE_PAREN)) {
				throw new FactoryException("Could not create Assignment.  First function must have closed parenthesis.");
			}
			if (functions == null || functions.size() < 5) {
				throw new FactoryException("Could not create Assignment.  The first function was missing.");
			}
			evaluee = (ReflexiveFunction<T>) functions.get(0);
			tokenIndex += 2;
		} else if (tokens.get(tokenIndex).isOfType(NAME)) {
			evaluee = new IdentityFunction<>(tokens.get(tokenIndex++).getValue());
		} else {
			throw new FactoryException("Could not create Assignment.  The first token was not recognised.");
		}

		if (!tokens.get(tokenIndex).isOfType(NAME) || !tokens.get(tokenIndex).getValue().equals(WHERE)) {
			throw new FactoryException("Could not create Assignment.  The second token must be \"" + WHERE + "\".");
		}
		tokenIndex++;

		if (!tokens.get(tokenIndex).isOfType(NAME)) {
			throw new FactoryException("Could not create Assignment.  The third token must be a name.");
		}
		String assignee = tokens.get(tokenIndex++).getValue();

		if (!tokens.get(tokenIndex).isOfType(NAME) || !tokens.get(tokenIndex).getValue().equals(IS)) {
			throw new FactoryException("Could not create Assignment.  The fourth token must be \"" + IS + "\".");
		}
		tokenIndex++;

		ReflexiveFunction<T> assignment;
		if (tokens.get(tokenIndex).isOfType(OPEN_PAREN)) {
			if (!tokens.get(tokenIndex + 1).isOfType(CLOSE_PAREN)) {
				throw new FactoryException("Could not create Assignment.  Second function must have closed parenthesis.");
			}
			if (functions == null || functions.size() < 5) {
				throw new FactoryException("Could not create Assignment.  The second function was missing.");
			}
			assignment = (ReflexiveFunction<T>) functions.get(4);
			tokenIndex += 2;
		} else if (tokens.get(tokenIndex).isOfType(NAME)) {
			assignment = constructor.construct(tokens.get(tokenIndex++).getValue());
		} else {
			throw new FactoryException("Could not create Assignment.  The first token was not recognised.");
		}

		if (tokens.size() > tokenIndex) {
			throw new FactoryException("Could not create Assignment.  Too many tokens.");
		}
		return new ReflexiveAssignment<>(evaluee, assignee, assignment);
	}
}
