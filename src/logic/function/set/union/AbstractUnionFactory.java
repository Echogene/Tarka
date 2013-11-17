package logic.function.set.union;

import javafx.util.Pair;
import logic.Nameable;
import logic.function.Function;
import logic.function.factory.validation.checking.CheckerWithNumber;
import logic.function.set.SetFunction;
import logic.function.set.SetFunctionFactory;
import reading.lexing.Token;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * @author Steven Weston
 */
abstract class AbstractUnionFactory<T extends Nameable> extends SetFunctionFactory<T, Union<T>> {

	protected AbstractUnionFactory(List<CheckerWithNumber> checkers) {
		super(checkers, Arrays.asList(new Pair<>("(", ")")));
	}

	@Override
	public Union<T> construct(List<Token> tokens, List<Function<?, ?>> functions) {
		java.util.Set<SetFunction<T>> sets = new HashSet<>();
		for (Function<?, ?> function : functions) {
			sets.add((SetFunction<T>) function);
		}
		return new Union<>(sets);
	}
}
