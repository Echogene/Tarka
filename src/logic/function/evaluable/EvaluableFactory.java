package logic.function.evaluable;

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
public abstract class EvaluableFactory<T extends Nameable, F extends Evaluable<T, F>> extends FunctionFactory<T, Boolean, F> {

	protected EvaluableFactory(List<CheckerWithNumber> checkers, List<Pair<String, String>> acceptedBracketPairs, Class<T> universeType) {
		super(checkers, acceptedBracketPairs, universeType);
	}

	@Override
	public Set<Type> getTypes(List<ParseTreeNode> nodes, MapWithErrors<ParseTreeNode, Set<Type>> types) throws TypeInferrorException {
		return Collections.singleton(Boolean.class);
	}

	@Override
	public Set<Type> getPotentialReturnTypes(ParseTreeNode parent, List<ParseTreeNode> children) {
		return Collections.singleton(Boolean.class);
	}
}
