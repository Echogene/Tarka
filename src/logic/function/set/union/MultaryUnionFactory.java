package logic.function.set.union;

import logic.Nameable;
import logic.function.factory.validation.checking.CheckerWithNumber;
import logic.function.factory.validation.checking.checkers.FunctionOrVariableChecker;
import logic.function.factory.validation.checking.checkers.NumberedChecker;
import logic.function.factory.validation.checking.checkers.OperatorChecker;
import logic.function.identity.SetIdentityFunction;
import logic.function.set.SetFunction;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static logic.function.factory.validation.checking.CheckerWithNumber.Number.MANY;

/**
 * @author Steven Weston
 */
public class MultaryUnionFactory<T extends Nameable> extends AbstractUnionFactory<T> {

	public MultaryUnionFactory(Class<T> universeType) {
		super(getCheckers(), universeType);
	}

	private static List<CheckerWithNumber> getCheckers() {
		return Arrays.asList(
				new OperatorChecker(Union.MULTARY_SYMBOL),
				new NumberedChecker(MANY, new FunctionOrVariableChecker(SetFunction.class))
		);
	}

	public static <T extends Nameable> Union<T> createElement(String... parameters) {
		Set<SetFunction<T>> sets = new HashSet<>();
		for (String parameter : parameters) {
			sets.add(new SetIdentityFunction<>(parameter));
		}
		return new Union<>(sets);
	}
}
