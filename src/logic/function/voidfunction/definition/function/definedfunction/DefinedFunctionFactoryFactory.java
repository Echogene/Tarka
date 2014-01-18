package logic.function.voidfunction.definition.function.definedfunction;

import logic.Nameable;
import logic.function.Function;
import logic.function.evaluable.Evaluable;
import logic.function.factory.validation.checking.CheckerWithNumber;
import logic.function.reflexive.ReflexiveFunction;
import logic.function.set.SetFunction;
import logic.function.voidfunction.VoidFunction;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Steven Weston
 */
public class DefinedFunctionFactoryFactory {

	public static <T extends Nameable> AbstractDefinedFunctionFactory<T, ?, ?> create(
			String functionSymbol,
			Function<T, ?, ?> definition,
			Map<String, Set<Type>> parameters,
			List<CheckerWithNumber> checkers,
			Class<T> universeType
	) {
		if (definition instanceof ReflexiveFunction) {
			return new DefinedReflexiveFunctionFactory<T>(functionSymbol, (ReflexiveFunction<T, ?>) definition, parameters, checkers, universeType);
		} else if (definition instanceof Evaluable) {
			return new DefinedEvaluableFunctionFactory<T>(functionSymbol, (Evaluable<T, ?>) definition, parameters, checkers, universeType);
		} else if (definition instanceof SetFunction) {
			return new DefinedSetFunctionFactory<T>(functionSymbol, (SetFunction<T, ?>) definition, parameters, checkers, universeType);
		} else if (definition instanceof VoidFunction) {
			return new DefinedVoidFunctionFactory<T>(functionSymbol, (VoidFunction<T, ?>) definition, parameters, checkers, universeType);
		} else {
			throw new NotImplementedException();
		}
	}
}
