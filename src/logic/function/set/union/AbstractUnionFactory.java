package logic.function.set.union;

import javafx.util.Pair;
import logic.Nameable;
import logic.function.Function;
import logic.function.factory.validation.checking.CheckerWithNumber;
import logic.function.identity.SetIdentityFunction;
import logic.function.set.SetFunction;
import logic.function.set.SetFunctionFactory;
import reading.lexing.Token;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * @author Steven Weston
 */
public abstract class AbstractUnionFactory<T extends Nameable> extends SetFunctionFactory<T, Union<T>> {

	AbstractUnionFactory(List<CheckerWithNumber> checkers, Class<T> universeType) {
		super(checkers, Arrays.asList(new Pair<>("(", ")")), universeType);
	}

	@Override
	public Union<T> construct(List<Token> tokens, List<Function<?, ?>> functions) {
		java.util.Set<SetFunction<T>> sets = new HashSet<>();
		for (Function<?, ?> function : functions) {
			sets.add((SetFunction<T>) function);
		}
		return new Union<>(sets);
	}

	public static <T extends Nameable> Union<T> createElement(String... setNames) {
		java.util.Set<SetFunction<T>> sets = new HashSet<>();
		for (String setName : setNames) {
			sets.add(new SetIdentityFunction<>(setName));
		}
		return new Union<>(sets);
	}

	@SafeVarargs
	public static <T extends Nameable> Union<T> createElement(SetFunction<T>... setFunctions) {
		java.util.Set<SetFunction<T>> sets = new HashSet<>();
		for (SetFunction<T> setFunction : setFunctions) {
			sets.add(setFunction);
		}
		return new Union<>(sets);
	}
}
