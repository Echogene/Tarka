package logic.function.voidfunction.definition.function.definedfunction;

import logic.Nameable;
import logic.factory.FactoryException;
import logic.function.Function;
import logic.function.factory.FunctionFactory;
import logic.function.factory.validation.checking.CheckerWithNumber;
import ophelia.exceptions.NotImplementedYetException;
import reading.lexing.Token;
import reading.parsing.ParseTreeNode;

import java.lang.reflect.Type;
import java.util.*;

/**
 * @author Steven Weston
 */
public abstract class AbstractDefinedFunctionFactory<
		D extends Nameable,
		C,
		F extends AbstractDefinedFunction<D, C, ? extends F, G>,
		G extends Function<D, C, ? extends G>
>
		extends FunctionFactory<D, C, F> {

	private final LinkedHashMap<String, Set<Type>> parameters;
	protected final String functionSymbol;
	protected final G definition;

	protected AbstractDefinedFunctionFactory(
			String functionSymbol,
			G definition,
			LinkedHashMap<String, Set<Type>> parameters,
			List<CheckerWithNumber> checkers,
			Class<D> universeType
	) {
		super(checkers, STANDARD_BRACKETS, universeType);
		this.functionSymbol = functionSymbol;
		this.definition = definition;
		this.parameters = parameters;
	}

	@Override
	public List<ParseTreeNode> getVariables(List<ParseTreeNode> nodes) {
		return getAllVariablesExcept(nodes, 1);
	}

	@Override
	public F construct(List<Token> tokens, List<Function<D, ?, ?>> functions, Map<String, Set<Type>> boundVariables) throws FactoryException {
		int i = 0;
		LinkedHashMap<String, Function<D, ?, ?>> parameterMap = new LinkedHashMap<>();
		for (Map.Entry<String, Set<Type>> parameter : parameters.entrySet()) {
			parameterMap.put(parameter.getKey(), functions.get(i));
			i++;
		}
		return construct(parameterMap);
	}

	protected abstract F construct(LinkedHashMap<String, Function<D, ?, ?>> parameters);

	public F construct(List<Function<D, ?, ?>> parameters) throws FactoryException {
		return construct(null, parameters, null);
	}

	@Override
	public Set<Type> guessTypes(ParseTreeNode variable, List<ParseTreeNode> nodes) {
		throw new NotImplementedYetException();
	}

	public String getFunctionSymbol() {
		return functionSymbol;
	}

	@Override
	public Set<Type> getPotentialReturnTypes(List<ParseTreeNode> surroundedChildren) {
		return Collections.singleton(definition.getCodomain(getUniverseType()));
	}
}
