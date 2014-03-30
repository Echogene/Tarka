package logic.function.set;

import javafx.util.Pair;
import logic.Nameable;
import logic.function.factory.FunctionFactory;
import logic.function.factory.validation.checking.CheckerWithNumber;
import logic.set.Set;
import logic.type.TypeInferrorException;
import logic.type.map.MapWithErrors;
import reading.parsing.ParseTreeNode;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

/**
 * @author Steven Weston
 */
public abstract class SetFunctionFactory<T extends Nameable, F extends SetFunction<T, ?>>
		extends FunctionFactory<T, Set<T>, F> {

	protected SetFunctionFactory(List<CheckerWithNumber> checkers, List<Pair<String, String>> acceptedBracketPairs, Class<T> universeType) {
		super(checkers, acceptedBracketPairs, universeType);
	}

	@Override
	public java.util.Set<Type> getTypes(List<ParseTreeNode> nodes, MapWithErrors<ParseTreeNode, java.util.Set<Type>> types) throws TypeInferrorException {
		return Collections.singleton(Set.class);
	}

	@Override
	public java.util.Set<Type> getPotentialReturnTypes(List<ParseTreeNode> surroundedChildren) {
		return Collections.singleton(Set.class);
	}
}
