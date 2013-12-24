package maths.number.integer.functions.multiplication;

import logic.factory.FactoryException;
import logic.function.Function;
import logic.function.factory.validation.checking.CheckerWithNumber;
import logic.function.reflexive.ReflexiveFunction;
import logic.function.reflexive.ReflexiveFunctionFactory;
import maths.number.Multiplior;
import maths.number.Number;
import reading.lexing.Token;
import reading.parsing.ParseTreeNode;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author Steven Weston
 */
abstract class AbstractMultiplicationFactory<N extends Number> extends ReflexiveFunctionFactory<N, Multiplication<N>> {

	private final Multiplior<N> multiplior;

	AbstractMultiplicationFactory(List<CheckerWithNumber> checkers, Class<N> universeType, Multiplior<N> multiplior) {
		super(checkers, STANDARD_BRACKETS, universeType);
		this.multiplior = multiplior;
	}

	@Override
	public List<ParseTreeNode> getVariables(List<ParseTreeNode> nodes) {
		return getAllVariables(nodes);
	}

	@Override
	public Multiplication<N> construct(List<Token> tokens, List<Function<N, ?>> functions) throws FactoryException {
		List<ReflexiveFunction<N>> parameters = new ArrayList<>(functions.size());
		for (Function<?, ?> function : functions) {
			parameters.add((ReflexiveFunction<N>) function);
		}
		return new Multiplication<N>(parameters, multiplior);
	}

	@Override
	public Set<Type> guessTypes(ParseTreeNode variable, List<ParseTreeNode> nodes) {
		return Collections.singleton(getUniverseType());
	}
}
