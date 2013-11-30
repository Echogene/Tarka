package logic.function.evaluable.predicate.equality;

import logic.Nameable;
import logic.factory.FactoryException;
import logic.function.Function;
import logic.function.evaluable.predicate.PredicateFactory;
import logic.function.factory.validation.checking.CheckerWithNumber;
import logic.function.factory.validation.checking.checkers.FunctionOrVariableChecker;
import logic.function.factory.validation.checking.checkers.OperatorChecker;
import logic.function.identity.MemberIdentityFunction;
import reading.lexing.Token;
import reading.parsing.ParseTreeNode;

import java.util.Arrays;
import java.util.List;

/**
 * A {@code Factory} for creating {@code EqualityPredicate}s.
 * @author Steven Weston
 */
public class EqualityPredicateFactory<T extends Nameable> extends PredicateFactory<T, EqualityPredicate<T>> {
	
	/**
	 * @param equorString The string representing the equor of the equals.
	 * @param equandString The string representing the equand of the equals.
	 * @param <T> The class of the universe in which to evaluate the equals.
	 * @return An equality predicate of the form 'equorString = equandString'.
	 */
	public static <T extends Nameable> EqualityPredicate<T> createElement(String equorString, String equandString) {
		MemberIdentityFunction<T> equorFunction = new MemberIdentityFunction<>(equorString);
		MemberIdentityFunction<T> equandFunction = new MemberIdentityFunction<>(equandString);
		return new EqualityPredicate<>(equorFunction, equandFunction);
	}

	public EqualityPredicateFactory(Class<T> universeType) {
		super(getCheckers(), STANDARD_BRACKETS, universeType);
	}

	private static List<CheckerWithNumber> getCheckers() {
		return Arrays.asList(
				new FunctionOrVariableChecker(NON_VOID_FUNCTIONS),
				new OperatorChecker(EqualityPredicate.EQUALITY_SYMBOL),
				new FunctionOrVariableChecker(NON_VOID_FUNCTIONS)
		);
	}

	@Override
	public EqualityPredicate<T> construct(List<Token> tokens, List<Function<?, ?>> functions) throws FactoryException {
		Function<T, ?> equor  = (Function<T, ?>) functions.get(0);
		Function<T, ?> equand = (Function<T, ?>) functions.get(1);
		return new EqualityPredicate<>(equor, equand);
	}

	@Override
	public List<ParseTreeNode> getVariables(List<ParseTreeNode> nodes) {
		return getAllVariables(nodes);
	}
}
