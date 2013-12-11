package logic.function.voidfunction.definition.function;

import logic.Nameable;
import logic.function.Function;
import logic.function.factory.validation.checking.CheckerWithNumber;
import logic.function.set.SetFunction;
import logic.set.Set;
import logic.type.TypeInferrorException;
import logic.type.map.MapWithErrors;
import reading.parsing.ParseTreeNode;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * @author Steven Weston
 */
public class DefinedSetFunctionFactory<T extends Nameable> extends AbstractDefinedFunctionFactory<T, Set<T>, SetFunction<T>> {

	protected DefinedSetFunctionFactory(
			SetFunction<T> definition,
			List<String> parameters,
			List<CheckerWithNumber> checkers,
			Class<T> universeType
	) {
		super(definition, parameters, checkers, universeType);
	}

	@Override
	protected SetFunction<T> construct(Map<String, Function<T, ?>> parameters) {
		return new DefinedSetFunction<>(definition, parameters);
	}

	@Override
	public Type getType(List<ParseTreeNode> nodes, MapWithErrors<ParseTreeNode, Type> types) throws TypeInferrorException {
		return Set.class;
	}
}
