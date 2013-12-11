package logic.function.voidfunction.definition.function;

import logic.Nameable;
import logic.function.Function;
import logic.function.factory.validation.checking.CheckerWithNumber;
import logic.function.voidfunction.VoidFunction;
import logic.type.TypeInferrorException;
import logic.type.map.MapWithErrors;
import reading.parsing.ParseTreeNode;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * @author Steven Weston
 */
public class DefinedVoidFunctionFactory<T extends Nameable> extends AbstractDefinedFunctionFactory<T, Void, VoidFunction<T>> {

	protected DefinedVoidFunctionFactory(
			VoidFunction<T> definition,
			List<String> parameters,
			List<CheckerWithNumber> checkers,
			Class<T> universeType
	) {
		super(definition, parameters, checkers, universeType);
	}

	@Override
	protected VoidFunction<T> construct(Map<String, Function<T, ?>> parameters) {
		return new DefinedVoidFunction<>(definition, parameters);
	}

	@Override
	public Type getType(List<ParseTreeNode> nodes, MapWithErrors<ParseTreeNode, Type> types) throws TypeInferrorException {
		return Void.class;
	}
}
