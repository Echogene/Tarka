package logic.function.voidfunction.definition.function.definedfunction;

import logic.Nameable;
import logic.function.Function;
import logic.function.evaluable.Evaluable;
import logic.function.factory.validation.checking.CheckerWithNumber;
import logic.type.TypeInferrorException;
import logic.type.map.MapWithErrors;
import reading.parsing.ParseTreeNode;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * @author Steven Weston
 */
public class DefinedEvaluableFunctionFactory<T extends Nameable> extends AbstractDefinedFunctionFactory<T, Boolean, Evaluable<T>> {

	protected DefinedEvaluableFunctionFactory(
			Evaluable<T> definition,
			List<String> parameters,
			List<CheckerWithNumber> checkers,
			Class<T> universeType
	) {
		super(definition, parameters, checkers, universeType);
	}

	@Override
	protected Evaluable<T> construct(Map<String, Function<T, ?>> parameters) {
		return new DefinedEvaluableFunction<>(definition, parameters);
	}

	@Override
	public Type getType(List<ParseTreeNode> nodes, MapWithErrors<ParseTreeNode, Type> types) throws TypeInferrorException {
		return Boolean.class;
	}
}
