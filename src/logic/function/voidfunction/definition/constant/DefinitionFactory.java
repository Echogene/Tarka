package logic.function.voidfunction.definition.constant;

import logic.Nameable;
import logic.function.Function;
import logic.function.evaluable.Evaluable;
import logic.function.factory.validation.checking.CheckerWithNumber;
import logic.function.factory.validation.checking.checkers.NonVoidFunctionOrVariableChecker;
import logic.function.factory.validation.checking.checkers.OperatorChecker;
import logic.function.factory.validation.checking.checkers.VariableChecker;
import logic.function.reflexive.ReflexiveFunction;
import logic.function.set.SetFunction;
import logic.function.voidfunction.VoidFunctionFactory;
import logic.oldtype.VariableAssignerFactory;
import logic.oldtype.VariableAssignmentTypeException;
import logic.oldtype.map.MapWithErrors;
import reading.lexing.Token;
import reading.parsing.ParseTreeNode;
import util.MapUtils;

import java.lang.reflect.Type;
import java.util.*;

import static logic.function.voidfunction.definition.constant.MemberDefinition.DEFINITION_SYMBOL;

/**
 * @author Steven Weston
 */
public class DefinitionFactory<T extends Nameable>
		extends VoidFunctionFactory<T, AbstractDefinition<T, ?, ?, ?>>
		implements VariableAssignerFactory {

	public DefinitionFactory(Class<T> universeType) {
		super(getCheckers(), STANDARD_BRACKETS, universeType);
	}

	private static List<CheckerWithNumber> getCheckers() {
		return Arrays.asList(
				new VariableChecker(),
				new OperatorChecker(DEFINITION_SYMBOL),
				new NonVoidFunctionOrVariableChecker()
		);
	}

	@Override
	public Map<String, Set<Type>> assignVariableTypes(List<ParseTreeNode> nodes, MapWithErrors<ParseTreeNode, Set<Type>> functionTypes, Map<String, Set<Type>> freeVariables) throws VariableAssignmentTypeException {
		String variableName = nodes.get(1).getValue();
		Set<Type> types = functionTypes.getPassedValues().get(nodes.get(3));
		return MapUtils.createMap(variableName, types);
	}

	@Override
	public boolean shouldWalkDownAt(ParseTreeNode node, List<ParseTreeNode> nodes) {
		return nodes.indexOf(node) > 1;
	}

	@Override
	public List<String> getVariablesToAssign(List<ParseTreeNode> surroundedChildren) {

		return Collections.singletonList(surroundedChildren.get(1).getValue());
	}

	@Override
	public AbstractDefinition<T, ?, ?, ?> construct(List<Token> tokens, List<Function<T, ?, ?>> functions, Map<String, Set<Type>> boundVariables) {
		Function<T, ?, ?> definition = functions.get(0);
		String variableName = tokens.get(1).getValue();
		if (definition instanceof ReflexiveFunction<?, ?>) {
			return new MemberDefinition<>(variableName, (ReflexiveFunction<T, ?>) definition);
		} else if (definition instanceof Evaluable<?, ?>) {
			return new BooleanDefinition<>(variableName, (Evaluable<T, ?>) definition);
		} else if (definition instanceof SetFunction<?, ?>) {
			return new SetDefinition<>(variableName, (SetFunction<T, ?>) definition);
		} else {
			return new MultitypeDefinition<>(variableName, (Function<T, Object, ?>) definition);
		}
	}

	@Override
	public List<ParseTreeNode> getVariables(List<ParseTreeNode> nodes) {
		return getSingleVariableWithIndex(nodes, 3);
	}

	@Override
	public Set<Type> guessTypes(ParseTreeNode variable, List<ParseTreeNode> nodes) {
		return nonVoidTypes;
	}
}
