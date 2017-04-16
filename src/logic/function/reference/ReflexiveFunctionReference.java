package logic.function.reference;

import logic.Nameable;
import logic.function.Function;
import logic.function.reflexive.ReflexiveFunction;
import logic.function.voidfunction.definition.function.definedfunction.AbstractDefinedFunctionFactory;
import logic.function.voidfunction.definition.function.definedfunction.DefinedReflexiveFunction;
import util.FunctionUtils;

import java.util.List;

/**
 * @author Steven Weston
 */
public class ReflexiveFunctionReference<T extends Nameable>
		extends AbstractFunctionReference<
				T,
				T,
				ReflexiveFunctionReference<T>,
				DefinedReflexiveFunction<T>,
				ReflexiveFunction<T, ?>
		>
		implements ReflexiveFunction<T, ReflexiveFunctionReference<T>> {


	public ReflexiveFunctionReference(String functionSymbol, List<Function<T, ?, ?>> parameters) {
		super(functionSymbol, parameters);
	}

	ReflexiveFunctionReference(
			String functionSymbol,
			List<Function<T, ?, ?>> copy,
			AbstractDefinedFunctionFactory<T, T, DefinedReflexiveFunction<T>, ReflexiveFunction<T, ?>> factory
	) {
		super(functionSymbol, copy, factory);
	}

	@Override
	public ReflexiveFunctionReference<T> copy() {
		return new ReflexiveFunctionReference<>(functionSymbol, FunctionUtils.copy(parameters), factory);
	}
}
