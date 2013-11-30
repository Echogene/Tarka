package maths.number.integer.functions.multiplication;

import javafx.util.Pair;
import logic.factory.FactoryException;
import logic.function.Function;
import logic.function.factory.validation.checking.CheckerWithNumber;
import logic.function.reflexive.ReflexiveFunction;
import logic.function.reflexive.ReflexiveFunctionFactory;
import maths.number.Multiplior;
import maths.number.Number;
import reading.lexing.Token;
import reading.parsing.ParseTreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Steven Weston
 */
abstract class AbstractMultiplicationFactory<N extends Number> extends ReflexiveFunctionFactory<N, Multiplication<N>> {

	private final Multiplior<N> multiplior;

	AbstractMultiplicationFactory(List<CheckerWithNumber> checkers, Class<N> universeType, Multiplior<N> multiplior) {
		super(checkers, Arrays.asList(new Pair<>("(", ")")), universeType);
		this.multiplior = multiplior;
	}

	@Override
	public List<ParseTreeNode> getVariables(List<ParseTreeNode> nodes) {
		return getAllVariables(nodes);
	}

	@Override
	public Multiplication<N> construct(List<Token> tokens, List<Function<?, ?>> functions) throws FactoryException {
		List<ReflexiveFunction<N>> parameters = new ArrayList<>(functions.size());
		for (Function<?, ?> function : functions) {
			parameters.add((ReflexiveFunction<N>) function);
		}
		return new Multiplication<N>(parameters, multiplior);
	}
}
