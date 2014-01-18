package logic.function.voidfunction.definition.function.definedfunction;

import logic.Nameable;
import logic.function.Function;
import logic.function.evaluable.Evaluable;
import logic.function.factory.validation.checking.CheckerWithNumber;
import logic.type.TypeInferrorException;
import logic.type.map.MapWithErrors;
import reading.parsing.ParseTreeNode;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Steven Weston
 */
public class DefinedEvaluableFunctionFactory<T extends Nameable> extends AbstractDefinedFunctionFactory<T, Boolean, Evaluable<T, ?>> {

	protected DefinedEvaluableFunctionFactory(
			String functionSymbol,
			Evaluable<T, ?> definition,
			Map<String,Set<Type>> parameters,
			List<CheckerWithNumber> checkers,
			Class<T> universeType
	) {
		super(functionSymbol, definition, parameters, checkers, universeType);
	}

	@Override
	protected Evaluable<T, ?> construct(Map<String, Function<T, ?, ?>> parameters) {
		return new DefinedEvaluable<>(functionSymbol, definition, parameters);
	}

	@Override
	public Set<Type> getTypes(List<ParseTreeNode> nodes, MapWithErrors<ParseTreeNode, Set<Type>> types) throws TypeInferrorException {
		return Collections.singleton(Boolean.class);
	}
}
