package logic.function.evaluable.predicate.membership;

import javafx.util.Pair;
import logic.Nameable;
import logic.factory.FactoryException;
import logic.function.Function;
import logic.function.evaluable.predicate.PredicateFactory;
import logic.function.factory.validation.checking.CheckerWithNumber;
import logic.function.factory.validation.checking.checkers.FunctionOrVariableChecker;
import logic.function.factory.validation.checking.checkers.OperatorChecker;
import logic.function.reflexive.ReflexiveFunction;
import logic.function.set.SetFunction;
import logic.identity.MemberIdentityFunction;
import logic.identity.SetIdentityFunction;
import reading.lexing.Token;

import java.util.Arrays;
import java.util.List;

/**
 * @author Steven Weston
 */
public class MembershipPredicateFactory<T extends Nameable> extends PredicateFactory<T, MembershipPredicate<T>> {

	public MembershipPredicateFactory() {
		super(getCheckers(), Arrays.asList(new Pair<>("(", ")")));
	}

	private static List<CheckerWithNumber> getCheckers() {
		return Arrays.asList(
				new FunctionOrVariableChecker(ReflexiveFunction.class),
				new OperatorChecker(MembershipPredicate.MEMBERSHIP_SYMBOL),
				new FunctionOrVariableChecker(SetFunction.class)
		);
	}

	public static <T extends Nameable> MembershipPredicate<T> createElement(String member, String set) {
		MemberIdentityFunction<T> memberFunction = new MemberIdentityFunction<>(member);
		SetIdentityFunction<T> setFunction = new SetIdentityFunction<>(set);
		return new MembershipPredicate<>(memberFunction, setFunction);
	}

	@Override
	public MembershipPredicate<T> construct(List<Token> tokens, List<Function<?, ?>> functions) throws FactoryException {
		ReflexiveFunction<T> equor = (ReflexiveFunction<T>) functions.get(0);
		SetFunction<T> equand = (SetFunction<T>) functions.get(1);
		return new MembershipPredicate<>(equor, equand);
	}
}
