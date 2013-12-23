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

import static logic.function.factory.validation.checking.CheckerWithNumber.Number.MANY;
import static logic.function.voidfunction.definition.function.FunctionDefinition.DEFINITION_SYMBOL;

/**
 * @author Steven Weston
 */
public class FunctionDefinitionFactory<T extends Nameable>
		extends FunctionFactory<T, Void, FunctionDefinition<T, ?>>
		implements VariableAssignerFactory {

	protected FunctionDefinitionFactory(Class<T> universeType) {
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
		return new ArrayList<>();
	}

	@Override
	public FunctionDefinition<T, ?> construct(List<Token> tokens, List<Function<T, ?>> functions) throws FactoryException {
		String functionName = tokens.get(1).getValue();
		List<String> parameters = new ArrayList<>();
		for (int i = 2; i < tokens.size(); i++) {
			Token token = tokens.get(i);
			if (DEFINITION_SYMBOL.equals(token.getValue())) {
				break;
			}
			parameters.add(token.getValue());// todo need to know the types of the parameters
		}
		Function<T, ?> definition = functions.get(0);
		if (definition instanceof ReflexiveFunction) {
			return new ReflexiveFunctionDefinition<>(functionName, parameters, (ReflexiveFunction<T>) definition);
		} else if (definition instanceof Evaluable) {
			return new EvaluableDefinition<>(functionName, parameters, (Evaluable<T>) definition);
		} else if (definition instanceof SetFunction) {
			return new SetFunctionDefinition<>(functionName, parameters, (SetFunction<T>) definition);
		} else if (definition instanceof VoidFunction) {
			return new VoidFunctionDefinition<>(functionName, parameters, (VoidFunction<T>) definition);
		} else {
			throw new FactoryException(
					MessageFormat.format(
							"{0} is not supported by this factory.",
							definition.getClass().getSimpleName()
					)
			);
		}
	}

	@Override
	public Type getType(List<ParseTreeNode> nodes, MapWithErrors<ParseTreeNode, Type> types) throws TypeInferrorException {
		return Void.class;
	}

	@Override
	public Map<String, Type> assignVariableTypes(
			List<ParseTreeNode> nodes,
			MapWithErrors<ParseTreeNode, Type> functionTypes,
			Map<String, Set<Type>> freeVariables
	) throws VariableAssignmentTypeException {
		Map<String, Type> output = new HashMap<>();
		for (int i = 2; i < nodes.size(); i++) {
			ParseTreeNode node = nodes.get(i);
			String nodeValue = node.getToken().getValue();
			if (DEFINITION_SYMBOL.equals(nodeValue)) {
				break;
			}
			output.put(nodeValue, freeVariables.get(nodeValue).iterator().next()); //todo: don't user iterator
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
