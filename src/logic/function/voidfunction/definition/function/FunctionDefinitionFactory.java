package logic.function.voidfunction.definition.function;

import logic.Nameable;
import logic.factory.FactoryException;
import logic.function.Function;
import logic.function.evaluable.Evaluable;
import logic.function.factory.FunctionFactory;
import logic.function.factory.validation.checking.CheckerWithNumber;
import logic.function.factory.validation.checking.checkers.FunctionOrVariableChecker;
import logic.function.factory.validation.checking.checkers.NumberedChecker;
import logic.function.factory.validation.checking.checkers.OperatorChecker;
import logic.function.factory.validation.checking.checkers.VariableChecker;
import logic.function.reflexive.ReflexiveFunction;
import logic.function.set.SetFunction;
import logic.function.voidfunction.VoidFunction;
import logic.type.TypeInferrorException;
import logic.type.VariableAssignerFactory;
import logic.type.VariableAssignmentTypeException;
import logic.type.map.MapWithErrors;
import reading.lexing.Token;
import reading.parsing.ParseTreeNode;

import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.util.*;

import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.NAME;
import static logic.function.factory.validation.checking.CheckerWithNumber.Number.MANY;
import static logic.function.voidfunction.definition.function.FunctionDefinition.DEFINITION_SYMBOL;

/**
 * @author Steven Weston
 */
public class FunctionDefinitionFactory<T extends Nameable>
		extends FunctionFactory<T, Void, FunctionDefinition<T, ?, ?, ?>>
		implements VariableAssignerFactory {

	public FunctionDefinitionFactory(Class<T> universeType) {
		super(getCheckers(), STANDARD_BRACKETS, universeType);
	}

	private static List<CheckerWithNumber> getCheckers() {
		return Arrays.asList(
				new VariableChecker(),
				new NumberedChecker(MANY, new VariableChecker()),
				new OperatorChecker(DEFINITION_SYMBOL),
				new FunctionOrVariableChecker()
		);
	}

	@Override
	public List<ParseTreeNode> getVariables(List<ParseTreeNode> nodes) {
		List<ParseTreeNode> output = new ArrayList<>();
		boolean definitionSymbolReached = false;
		for (ParseTreeNode node : nodes) {
			if (node.getToken().isOfType(NAME) && definitionSymbolReached) {
				output.add(node);
			}
			if (DEFINITION_SYMBOL.equals(node.getToken().getValue())) {
				definitionSymbolReached = true;
			}
		}
		return output;
	}

	@Override
	public Set<Type> guessTypes(ParseTreeNode variable, List<ParseTreeNode> nodes) {
		return nonVoidTypes;
	}

	@Override
	public FunctionDefinition<T, ?, ?, ?> construct(
			List<Token> tokens,
			List<Function<T, ?, ?>> functions,
			Map<String, Set<Type>> boundVariables
	) throws FactoryException {
		String functionName = tokens.get(1).getValue();
		LinkedHashMap<String, Set<Type>> parameters = new LinkedHashMap<>();
		for (int i = 2; i < tokens.size(); i++) {
			Token token = tokens.get(i);
			String tokenValue = token.getValue();
			if (DEFINITION_SYMBOL.equals(tokenValue)) {
				break;
			}
			if (boundVariables.containsKey(tokenValue)) {
				parameters.put(tokenValue, boundVariables.get(tokenValue));
			} else {
				parameters.put(tokenValue, nonVoidTypes);
			}
		}
		Function<T, ?, ?> definition = functions.get(0);
		if (definition instanceof ReflexiveFunction) {
			return new ReflexiveFunctionDefinition<>(functionName, parameters, (ReflexiveFunction<T, ?>) definition);
		} else if (definition instanceof Evaluable) {
			return new EvaluableDefinition<>(functionName, parameters, (Evaluable<T, ?>) definition);
		} else if (definition instanceof SetFunction) {
			return new SetFunctionDefinition<>(functionName, parameters, (SetFunction<T, ?>) definition);
		} else if (definition instanceof VoidFunction) {
			return new VoidFunctionDefinition<>(functionName, parameters, (VoidFunction<T, ?>) definition);
		} else {
			throw new FactoryException(
					MessageFormat.format(
							"The definition {0} had the unknown/unimplemented class {1}.\nAre you trying to obtain multiple return types?",
							definition,
							definition.getClass().getSimpleName()
					)
			);
		}
	}

	@Override
	public Set<Type> getTypes(List<ParseTreeNode> nodes, MapWithErrors<ParseTreeNode, Set<Type>> types) throws TypeInferrorException {
		return Collections.singleton(Void.class);
	}

	@Override
	public Map<String, Set<Type>> assignVariableTypes(
			List<ParseTreeNode> nodes,
			MapWithErrors<ParseTreeNode, Set<Type>> functionTypes,
			Map<String, Set<Type>> freeVariables
	) throws VariableAssignmentTypeException {
		Map<String, Set<Type>> output = new HashMap<>();
		for (int i = 2; i < nodes.size(); i++) {
			ParseTreeNode node = nodes.get(i);
			String nodeValue = node.getToken().getValue();
			if (DEFINITION_SYMBOL.equals(nodeValue)) {
				break;
			} else if (freeVariables.containsKey(nodeValue)) {
				output.put(nodeValue, freeVariables.get(nodeValue));
			}
		}
		return output;
	}

	@Override
	public boolean shouldWalkDownAt(ParseTreeNode node, List<ParseTreeNode> nodes) {
		boolean definitionSymbolReached = false;
		for (ParseTreeNode n : nodes) {
			if (node.equals(n)) {
				return definitionSymbolReached;
			}
			if (DEFINITION_SYMBOL.equals(n.getToken().getValue())) {
				definitionSymbolReached = true;
			}
		}
		return false;
	}
}
