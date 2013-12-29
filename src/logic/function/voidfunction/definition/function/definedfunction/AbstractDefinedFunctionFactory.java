package logic.function.voidfunction.definition.function.definedfunction;

import logic.Nameable;
import logic.factory.FactoryException;
import logic.function.Function;
import logic.function.factory.FunctionFactory;
import logic.function.factory.validation.checking.CheckerWithNumber;
import reading.lexing.Token;
import reading.parsing.ParseTreeNode;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Steven Weston
 */
public abstract class AbstractDefinedFunctionFactory<D extends Nameable, C, F extends Function<D, C>> extends FunctionFactory<D, C, F> {

	private final Map<String, Set<Type>> parameters;
	protected final F definition;

	protected AbstractDefinedFunctionFactory(
			F definition,
			Map<String, Set<Type>> parameters,
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
	public F construct(List<Token> tokens, List<Function<D, ?>> functions, Map<String, Set<Type>> boundVariables) throws FactoryException {
		int i = 0;
		Map<String, Function<D, ?>> parameterMap = new HashMap<>();
		for (Map.Entry<String, Set<Type>> parameter : parameters.entrySet()) {
			parameterMap.put(parameter.getKey(), functions.get(i));
			i++;
		}
		return construct(parameterMap);
	}

	protected abstract F construct(Map<String, Function<D, ?>> parameters);

	@Override
	public Set<Type> guessTypes(ParseTreeNode variable, List<ParseTreeNode> nodes) {
		throw new NotImplementedException();
	}
}
