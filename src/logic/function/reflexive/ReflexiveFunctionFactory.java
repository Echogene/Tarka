package logic.function.reflexive;

import javafx.util.Pair;
import logic.Nameable;
import logic.function.factory.FunctionFactory;
import logic.function.factory.validation.checking.CheckerWithNumber;
import logic.oldtype.TypeInferrorException;
import logic.oldtype.map.MapWithErrors;
import reading.parsing.ParseTreeNode;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author Steven Weston
 */
public abstract class ReflexiveFunctionFactory<T extends Nameable, F extends ReflexiveFunction<T, F>>
		extends FunctionFactory<T, T, F> {

	protected ReflexiveFunctionFactory(List<CheckerWithNumber> checkers, List<Pair<String, String>> acceptedBracketPairs, Class<T> universeType) {
		super(checkers, acceptedBracketPairs, universeType);
	}

	@Override
	public Set<Type> getTypes(List<ParseTreeNode> nodes, MapWithErrors<ParseTreeNode, Set<Type>> types) throws TypeInferrorException {
		return Collections.singleton(getUniverseType());
	}

	@Override
	public Set<Type> getPotentialReturnTypes(List<ParseTreeNode> surroundedChildren) {
		return Collections.singleton(getUniverseType());
	}
}
