package logic.function.voidfunction.definition.function;

import logic.Nameable;
import logic.function.Function;
import logic.function.factory.validation.checking.CheckerWithNumber;
import logic.function.reflexive.ReflexiveFunction;
import logic.type.TypeInferrorException;
import logic.type.map.MapWithErrors;
import reading.parsing.ParseTreeNode;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * @author Steven Weston
 */
public class DefinedReflexiveFunctionFactory<T extends Nameable> extends AbstractDefinedFunctionFactory<T, T, ReflexiveFunction<T>> {

	protected DefinedReflexiveFunctionFactory(
			ReflexiveFunction<T> definition,
			List<String> parameters,
			List<CheckerWithNumber> checkers,
			Class<T> universeType
	) {
		super(definition, parameters, checkers, universeType);
	}

	@Override
	public Type getType(List<ParseTreeNode> nodes, MapWithErrors<ParseTreeNode, Type> types) throws TypeInferrorException {
		return getUniverseType();
	}

	@Override
	protected ReflexiveFunction<T> construct(Map<String, Function<T, ?>> parameters) {
		return new DefinedReflexiveFunction<>(definition, parameters);
	}
}
