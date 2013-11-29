package maths.number.integer.functions.addition;

import javafx.util.Pair;
import logic.factory.FactoryException;
import logic.function.Function;
import logic.function.factory.validation.checking.CheckerWithNumber;
import logic.function.reflexive.ReflexiveFunction;
import logic.function.reflexive.ReflexiveFunctionFactory;
import logic.type.TypeInferrorException;
import logic.type.map.MapWithErrors;
import maths.number.Number;
import maths.number.Summor;
import reading.lexing.Token;
import reading.parsing.ParseTreeNode;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Steven Weston
 */
public abstract class AbstractAdditionFactory<N extends Number> extends ReflexiveFunctionFactory<N, Addition<N>> {

	protected final Summor<N> summor;

	protected AbstractAdditionFactory(List<CheckerWithNumber> checkers, Class<N> universeType, Summor<N> summor) {
		super(checkers, Arrays.asList(new Pair<>("(", ")")), universeType);
		this.summor = summor;
	}

	@Override
	public List<ParseTreeNode> getVariables(List<ParseTreeNode> nodes) {
		return getAllVariables(nodes);
	}

	@Override
	public Type getType(List<ParseTreeNode> nodes, MapWithErrors<ParseTreeNode, Type> types) throws TypeInferrorException {
		return getUniverseType();
	}

	@Override
	public Addition<N> construct(List<Token> tokens, List<Function<?, ?>> functions) throws FactoryException {
		List<ReflexiveFunction<N>> parameters = new ArrayList<>(functions.size());
		for (Function<?, ?> function : functions) {
			parameters.add((ReflexiveFunction<N>) function);
		}
		return new Addition<>(parameters, summor);
	}
}
