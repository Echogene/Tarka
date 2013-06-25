package logic.evaluable.predicate;

import logic.Nameable;
import logic.factory.FactoryException;
import logic.function.Function;
import logic.function.reflexive.ReflexiveFunction;
import logic.function.reflexiveset.ReflexiveSetFunction;
import reading.lexing.Token;

import java.util.List;

import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.*;

/**
 * @author Steven Weston
 */
public class MembershipPredicateFactory<T extends Nameable> implements PredicateFactory<T> {
	@Override
	public Function<T, Boolean> createElement(List<Token> tokens) throws FactoryException {
		return createElement(tokens, null);
	}

	@Override
	public Function<T, Boolean> createElement(List<Token> tokens, List<Function<?, ?>> functions) throws FactoryException {
		if (matchesTwoNameTokens(tokens, functions)) {
			return new MembershipPredicate<>(tokens.get(0).getValue(), tokens.get(2).getValue());
		} else if (matchesFirstFunctionSecondNameToken(tokens, functions)) {
			return new MembershipPredicate<>((ReflexiveFunction<T>) functions.get(0), tokens.get(3).getValue());
		} else if (matchesFirstNameTokenSecondFunction(tokens, functions)) {
			return new MembershipPredicate<>(tokens.get(0).getValue(), (ReflexiveSetFunction<T>) functions.get(1));
		} else if (matchesTwoFunctions(tokens, functions)) {
			return new MembershipPredicate<>(
					(ReflexiveFunction<T>) functions.get(0),
					(ReflexiveSetFunction<T>) functions.get(1));
		}
		throw new FactoryException("Could not create MembershipPredicate");
	}

	public boolean matchesTwoNameTokens(List<Token> tokens, List<Function<?, ?>> functions) {
		return tokens != null
				&& tokens.size() == 3
				&& tokens.get(0).isOfType(NAME)
				&& tokens.get(1).isOfType(OPERATOR)
				&& tokens.get(1).getValue().equals(MembershipPredicate.MEMBERSHIP_STRING)
				&& tokens.get(2).isOfType(NAME)
				&& (functions == null
					|| (functions.size() == 2
						&& functions.get(0) == null
						&& functions.get(1) == null));
	}

	public boolean matchesFirstFunctionSecondNameToken(List<Token> tokens, List<Function<?, ?>> functions) {
		return tokens != null
				&& tokens.size() == 4
				&& tokens.get(0).isOfType(OPEN_PAREN)
				&& tokens.get(1).isOfType(CLOSE_PAREN)
				&& tokens.get(2).isOfType(OPERATOR)
				&& tokens.get(2).getValue().equals(MembershipPredicate.MEMBERSHIP_STRING)
				&& tokens.get(3).isOfType(NAME)
				&& functions != null
				&& functions.size() == 2
				&& functions.get(0) != null
				&& functions.get(0) instanceof ReflexiveFunction<?>
				&& functions.get(1) == null;
	}

	boolean matchesFirstNameTokenSecondFunction(List<Token> tokens, List<Function<?, ?>> functions) {
		return tokens != null
				&& tokens.size() == 4
				&& tokens.get(0).isOfType(NAME)
				&& tokens.get(1).isOfType(OPERATOR)
				&& tokens.get(1).getValue().equals(MembershipPredicate.MEMBERSHIP_STRING)
				&& tokens.get(2).isOfType(OPEN_PAREN)
				&& tokens.get(3).isOfType(CLOSE_PAREN)
				&& functions != null
				&& functions.size() == 2
				&& functions.get(0) == null
				&& functions.get(1) != null
				&& functions.get(1) instanceof ReflexiveSetFunction<?>;
	}

	public boolean matchesTwoFunctions(List<Token> tokens, List<Function<?, ?>> functions) {
		return tokens != null
				&& tokens.size() == 5
				&& tokens.get(0).isOfType(OPEN_PAREN)
				&& tokens.get(1).isOfType(CLOSE_PAREN)
				&& tokens.get(2).isOfType(OPERATOR)
				&& tokens.get(2).getValue().equals(MembershipPredicate.MEMBERSHIP_STRING)
				&& tokens.get(3).isOfType(OPEN_PAREN)
				&& tokens.get(4).isOfType(CLOSE_PAREN)
				&& functions != null
				&& functions.size() == 2
				&& functions.get(0) != null
				&& functions.get(0) instanceof ReflexiveFunction<?>
				&& functions.get(1) != null
				&& functions.get(1) instanceof ReflexiveSetFunction<?>;
	}
}
