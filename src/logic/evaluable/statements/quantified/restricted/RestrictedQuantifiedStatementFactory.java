package logic.evaluable.statements.quantified.restricted;

import logic.Nameable;
import logic.evaluable.Evaluable;
import logic.evaluable.predicate.membership.MembershipPredicate;
import logic.evaluable.statements.quantified.standard.QuantifierFactory;
import logic.factory.FactoryException;
import logic.function.Function;
import logic.function.factory.FunctionFactory;
import logic.function.reflexiveset.ReflexiveSetFunction;
import logic.function.reflexiveset.identity.SetIdentityFunction;
import reading.lexing.Token;

import java.util.List;

import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.*;

/**
 * @author Steven Weston
 */
public class RestrictedQuantifiedStatementFactory<T extends Nameable> extends FunctionFactory<T, Boolean> {
	private QuantifierFactory quantifierFactory;

	public RestrictedQuantifiedStatementFactory() {
		quantifierFactory = new QuantifierFactory();
	}

	@Override
	public Function<T, Boolean> createElement(List<Token> tokens, List<Function<?, ?>> functions) throws FactoryException {
		if (matchesName(tokens, functions)) {
			return new RestrictedQuantifiedStatement<>(
					quantifierFactory.createElement(tokens.get(0).getValue()),
					tokens.get(1).getValue(),
					new SetIdentityFunction<>(tokens.get(3).getValue()),
					(Evaluable<T>) functions.get(2)
			);
		} else if (matchesSetFunction(tokens, functions)) {
			return new RestrictedQuantifiedStatement<>(
					quantifierFactory.createElement(tokens.get(0).getValue()),
					tokens.get(1).getValue(),
					(ReflexiveSetFunction<T>) functions.get(1),
					(Evaluable<T>) functions.get(2)
			);
		}
		throw new FactoryException("Could not create " + RestrictedQuantifiedStatement.class.getName());
	}

	boolean matchesName(List<Token> tokens, List<Function<?, ?>> functions) {
		return matchesTokensName(tokens) && matchesFunctionsName(functions);
	}

	boolean matchesTokensName(List<Token> tokens) {
		return tokens != null
				&& tokens.size() == 6
				&& tokens.get(0).isOfType(QUANTIFIER)
				&& tokens.get(1).isOfType(NAME)
				&& tokens.get(2).isOfType(OPERATOR)
				&& tokens.get(2).getValue().equals(MembershipPredicate.MEMBERSHIP_STRING)
				&& tokens.get(3).isOfType(NAME)
				&& tokens.get(4).isOfType(OPEN_PAREN)
				&& tokens.get(5).isOfType(CLOSE_PAREN);
	}

	boolean matchesFunctionsName(List<Function<?, ?>> functions) {
		return functions != null
				&& functions.size() == 3
				&& functions.get(0) == null
				&& functions.get(1) == null
				&& functions.get(2) != null
				&& functions.get(2) instanceof Evaluable<?>;
	}

	boolean matchesSetFunction(List<Token> tokens, List<Function<?, ?>> functions) {
		return matchesTokensSetFunction(tokens) && matchesFunctionsSetFunction(functions);
	}

	boolean matchesTokensSetFunction(List<Token> tokens) {
		return tokens != null
				&& tokens.size() == 7
				&& tokens.get(0).isOfType(QUANTIFIER)
				&& tokens.get(1).isOfType(NAME)
				&& tokens.get(2).isOfType(OPERATOR)
				&& tokens.get(2).getValue().equals(MembershipPredicate.MEMBERSHIP_STRING)
				&& tokens.get(3).isOfType(OPEN_PAREN)
				&& tokens.get(4).isOfType(CLOSE_PAREN)
				&& tokens.get(5).isOfType(OPEN_PAREN)
				&& tokens.get(6).isOfType(CLOSE_PAREN);
	}

	boolean matchesFunctionsSetFunction(List<Function<?, ?>> functions) {
		return functions != null
				&& functions.size() == 3
				&& functions.get(0) == null
				&& functions.get(1) != null
				&& functions.get(1) instanceof ReflexiveSetFunction<?>
				&& functions.get(2) != null
				&& functions.get(2) instanceof Evaluable<?>;
	}
}