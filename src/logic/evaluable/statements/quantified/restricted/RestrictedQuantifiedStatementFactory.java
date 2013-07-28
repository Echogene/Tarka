package logic.evaluable.statements.quantified.restricted;

import logic.Nameable;
import logic.evaluable.Evaluable;
import logic.evaluable.EvaluableFactory;
import logic.evaluable.statements.quantified.standard.QuantifierFactory;
import logic.factory.FactoryException;
import logic.function.Function;
import logic.function.reflexiveset.ReflexiveSetFunction;
import logic.function.reflexiveset.identity.SetIdentityFunction;
import reading.lexing.Token;

import java.util.List;

import static logic.evaluable.predicate.membership.MembershipPredicate.MEMBERSHIP_STRING;
import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.*;

/**
 * @author Steven Weston
 */
public class RestrictedQuantifiedStatementFactory<T extends Nameable> extends EvaluableFactory<T> {
	private QuantifierFactory quantifierFactory;

	public RestrictedQuantifiedStatementFactory() {
		quantifierFactory = new QuantifierFactory();
	}

	@Override
	public Function<T, Boolean> createElement(List<Token> tokens, List<Function<?, ?>> functions) throws FactoryException {
		tokens = validateAndStripParentheses(tokens);
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
		throw new FactoryException("Could not create " + RestrictedQuantifiedStatement.class.getSimpleName());
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
				&& tokens.get(2).getValue().equals(MEMBERSHIP_STRING)
				&& tokens.get(3).isOfType(NAME)
				&& isTokenOpenParenthesis(tokens.get(4))
				&& isTokenCloseParenthesis(tokens.get(5));
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
				&& tokens.get(2).getValue().equals(MEMBERSHIP_STRING)
				&& tokens.get(3).isOfType(OPEN_BRACKET)
				&& tokens.get(4).isOfType(CLOSE_BRACKET)
				&& isTokenOpenParenthesis(tokens.get(5))
				&& isTokenCloseParenthesis(tokens.get(6));
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
