package logic.function.voidfunction.definition.function.definedfunction;

import logic.Nameable;
import logic.function.Function;
import logic.function.factory.validation.checking.CheckerWithNumber;
import logic.function.voidfunction.VoidFunction;
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
public class DefinedVoidFunctionFactory<T extends Nameable> extends AbstractDefinedFunctionFactory<T, Void, VoidFunction<T>> {

	protected DefinedVoidFunctionFactory(
			String functionSymbol,
			VoidFunction<T> definition,
			Map<String,Set<Type>> parameters,
			List<CheckerWithNumber> checkers,
			Class<T> universeType
	) {
		super(functionSymbol, definition, parameters, checkers, universeType);
	}

	@Override
	protected VoidFunction<T> construct(Map<String, Function<T, ?>> parameters) {
		return new DefinedVoidFunction<>(functionSymbol, definition, parameters);
	}

	@Override
	public Set<Type> getTypes(List<ParseTreeNode> nodes, MapWithErrors<ParseTreeNode, Set<Type>> types) throws TypeInferrorException {
		return Collections.singleton(Void.class);
	}
}
