package maths.number.integer.functions.addition;

import logic.factory.FactoryException;
import logic.function.Function;
import logic.function.factory.validation.checking.CheckerWithNumber;
import logic.function.reflexive.ReflexiveFunction;
import logic.function.reflexive.ReflexiveFunctionFactory;
import maths.number.Number;
import maths.number.Summor;
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
public abstract class AbstractAdditionFactory<N extends Number> extends ReflexiveFunctionFactory<N, Addition<N>> {

	protected final Summor<N> summor;

	protected AbstractAdditionFactory(List<CheckerWithNumber> checkers, Class<N> universeType, Summor<N> summor) {
		super(checkers, STANDARD_BRACKETS, universeType);
		this.summor = summor;
	}

	@Override
	public List<ParseTreeNode> getVariables(List<ParseTreeNode> nodes) {
		return getAllVariables(nodes);
	}

	@Override
	public Addition<N> construct(List<Token> tokens, List<Function<N, ?>> functions) throws FactoryException {
		List<ReflexiveFunction<N>> parameters = new ArrayList<>(functions.size());
		for (Function<?, ?> function : functions) {
			parameters.add((ReflexiveFunction<N>) function);
		}
		return new Addition<>(parameters, summor);
	}

	@Override
	public Set<Type> guessTypes(ParseTreeNode variable, List<ParseTreeNode> nodes) {
		return Collections.singleton(getUniverseType());
	}
}
