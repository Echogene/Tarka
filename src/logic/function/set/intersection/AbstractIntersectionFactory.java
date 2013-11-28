package logic.function.set.intersection;

import javafx.util.Pair;
import logic.Nameable;
import logic.function.Function;
import logic.function.factory.validation.checking.CheckerWithNumber;
import logic.function.set.SetFunction;
import logic.function.set.SetFunctionFactory;
import reading.lexing.Token;
import reading.parsing.ParseTreeNode;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * @author Steven Weston
 */
public abstract class AbstractIntersectionFactory<T extends Nameable> extends SetFunctionFactory<T, Intersection<T>> {

	AbstractIntersectionFactory(List<CheckerWithNumber> checkers, Class<T> universeType) {
		super(checkers, Arrays.asList(new Pair<>("(", ")")), universeType);
	}

	@Override
	public Intersection<T> construct(List<Token> tokens, List<Function<?, ?>> functions) {
		java.util.Set<SetFunction<T>> sets = new HashSet<>();
		for (Function<?, ?> function : functions) {
			sets.add((SetFunction<T>) function);
		}
		return new Intersection<>(sets);
	}

	@Override
	public List<ParseTreeNode> getVariables(List<ParseTreeNode> nodes) {
		return getAllVariables(nodes);
	}
}
