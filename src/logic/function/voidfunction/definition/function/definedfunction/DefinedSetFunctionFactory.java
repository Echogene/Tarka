package logic.function.voidfunction.definition.function.definedfunction;

import logic.Nameable;
import logic.function.Function;
import logic.function.factory.validation.checking.CheckerWithNumber;
import logic.function.set.SetFunction;
import logic.set.Set;
import logic.type.TypeInferrorException;
import logic.type.map.MapWithErrors;
import reading.parsing.ParseTreeNode;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Steven Weston
 */
public class DefinedSetFunctionFactory<T extends Nameable> extends AbstractDefinedFunctionFactory<T, Set<T>, SetFunction<T>> {

	protected DefinedSetFunctionFactory(
			String functionSymbol,
			SetFunction<T> definition,
			Map<String, java.util.Set<Type>> parameters,
			List<CheckerWithNumber> checkers,
			Class<T> universeType
	) {
		super(functionSymbol, definition, parameters, checkers, universeType);
	}

	@Override
	protected SetFunction<T> construct(Map<String, Function<T, ?>> parameters) {
		return new DefinedSetFunction<>(functionSymbol, definition, parameters);
	}

	@Override
	public java.util.Set<Type> getTypes(List<ParseTreeNode> nodes, MapWithErrors<ParseTreeNode, java.util.Set<Type>> types) throws TypeInferrorException {
		return Collections.singleton(Set.class);
	}
}
