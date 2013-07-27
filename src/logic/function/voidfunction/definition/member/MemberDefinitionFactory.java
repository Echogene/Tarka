package logic.function.voidfunction.definition.member;

import logic.Nameable;
import logic.factory.FactoryException;
import logic.function.Function;
import logic.function.factory.FunctionFactory;
import logic.function.reflexive.ReflexiveFunction;
import logic.function.reflexive.identity.IdentityFunction;
import reading.lexing.Token;

import java.util.List;

import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.*;

/**
 * @author Steven Weston
 */
public class MemberDefinitionFactory<T extends Nameable> extends FunctionFactory<T, Void> {

	@Override
	public Function<T, Void> createElement(List<Token> tokens, List<Function<?, ?>> functions) throws FactoryException {
		if (matchesName(tokens, functions)) {
			return new MemberDefinition<>(tokens.get(0).getValue(), new IdentityFunction<>(tokens.get(2).getValue()));
		} else if (matchesFunction(tokens, functions)) {
			return new MemberDefinition<>(tokens.get(0).getValue(), (ReflexiveFunction<T>) functions.get(1));
		}
		throw new FactoryException("Could not create MembershipDefinition");
	}

	private boolean matchesName(List<Token> tokens, List<Function<?, ?>> functions) {
		return tokens != null
				&& tokens.size() == 3
				&& tokens.get(0).isOfType(NAME)
				&& tokens.get(1).isOfType(OPERATOR)
				&& tokens.get(1).getValue().equals(MemberDefinition.DEFINITION_SYMBOL)
				&& tokens.get(2).isOfType(NAME)
				&& (functions == null
					|| functions.isEmpty()
					|| (functions.size() == 2
						&& functions.get(0) == null
						&& functions.get(1) == null));
	}

	private boolean matchesFunction(List<Token> tokens, List<Function<?, ?>> functions) {
		return tokens != null
				&& tokens.size() == 4
				&& tokens.get(0).isOfType(NAME)
				&& tokens.get(1).isOfType(OPERATOR)
				&& tokens.get(1).getValue().equals(MemberDefinition.DEFINITION_SYMBOL)
				&& tokens.get(2).isOfType(OPEN_PAREN)
				&& tokens.get(3).isOfType(CLOSE_PAREN)
				&& functions != null
				&& functions.size() == 2
				&& functions.get(0) == null
				&& functions.get(1) != null
				&& functions.get(1) instanceof ReflexiveFunction<?>;
	}
}
