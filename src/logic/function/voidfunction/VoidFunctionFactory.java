package logic.function.voidfunction;

import javafx.util.Pair;
import logic.Nameable;
import logic.function.factory.FunctionFactory;
import logic.function.factory.validation.checking.CheckerWithNumber;
import logic.type.TypeInferrorException;
import logic.type.map.MapWithErrors;
import reading.parsing.ParseTreeNode;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author Steven Weston
 */
public abstract class VoidFunctionFactory<T extends Nameable, F extends VoidFunction<T, ?>> extends FunctionFactory<T, Void, F> {

	protected VoidFunctionFactory(
			List<CheckerWithNumber> checkers,
			List<Pair<String, String>> acceptedBracketPairs,
			Class<T> universeType
	) {
		super(checkers, acceptedBracketPairs, universeType);
	}


	@Override
	public Set<Type> getPotentialReturnTypes(List<ParseTreeNode> surroundedChildren) {
		return Collections.singleton(Void.class);
	}

	@Override
	public Set<Type> getTypes(List<ParseTreeNode> nodes, MapWithErrors<ParseTreeNode, Set<Type>> types) throws TypeInferrorException {
		return Collections.singleton(Void.class);
	}
}
