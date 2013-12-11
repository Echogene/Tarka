package logic.function.voidfunction.definition.function;

import logic.Nameable;
import logic.factory.FactoryException;
import logic.function.Function;
import logic.function.factory.FunctionFactory;
import logic.function.factory.validation.checking.CheckerWithNumber;
import reading.lexing.Token;
import reading.parsing.ParseTreeNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Steven Weston
 */
public abstract class AbstractDefinedFunctionFactory<D extends Nameable, C, F extends Function<D, C>> extends FunctionFactory<D, C, F> {

	private final List<String> parameters;
	protected final F definition;

	protected AbstractDefinedFunctionFactory(
			F definition,
			List<String> parameters,
			List<CheckerWithNumber> checkers,
			Class<D> universeType
	) {
		super(checkers, STANDARD_BRACKETS, universeType);
		this.definition = definition;
		this.parameters = parameters;
	}

	@Override
	public List<ParseTreeNode> getVariables(List<ParseTreeNode> nodes) {
		return getAllVariablesExcept(nodes, 1);
	}

	@Override
	public F construct(List<Token> tokens, List<Function<D, ?>> functions) throws FactoryException {
		int i = 0;
		Map<String, Function<D, ?>> parameterMap = new HashMap<>();
		for (String parameter : parameters) {
			parameterMap.put(parameter, functions.get(i));
			i++;
		}
		return construct(parameterMap);
	}

	protected abstract F construct(Map<String, Function<D, ?>> parameters);
}
