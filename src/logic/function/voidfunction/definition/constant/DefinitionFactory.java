package logic.function.voidfunction.definition.constant;

import logic.Nameable;
import logic.function.Function;
import logic.function.evaluable.Evaluable;
import logic.function.factory.FunctionFactory;
import logic.function.factory.validation.checking.CheckerWithNumber;
import logic.function.factory.validation.checking.checkers.FunctionOrVariableChecker;
import logic.function.factory.validation.checking.checkers.OperatorChecker;
import logic.function.factory.validation.checking.checkers.VariableChecker;
import logic.function.reflexive.ReflexiveFunction;
import logic.function.set.SetFunction;
import logic.type.TypeInferrorException;
import logic.type.VariableAssignerFactory;
import logic.type.VariableAssignmentTypeException;
import logic.type.map.MapWithErrors;
import reading.lexing.Token;
import reading.parsing.ParseTreeNode;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static logic.function.voidfunction.definition.constant.MemberDefinition.DEFINITION_SYMBOL;

/**
 * @author Steven Weston
 */
public class DefinitionFactory<T extends Nameable> extends FunctionFactory<T, Void, Definition<T, ?>> implements VariableAssignerFactory {

	public DefinitionFactory(Class<T> universeType) {
		super(getCheckers(), STANDARD_BRACKETS, universeType);
	}

	private static List<CheckerWithNumber> getCheckers() {
		return Arrays.asList(
				new VariableChecker(),
				new OperatorChecker(DEFINITION_SYMBOL),
				new FunctionOrVariableChecker(
						Arrays.<Class>asList(
								ReflexiveFunction.class,
								Evaluable.class,
								SetFunction.class
						)
				)
		);
	}

	@Override
	public Map<String, Type> assignVariableTypes(List<ParseTreeNode> nodes, MapWithErrors<ParseTreeNode, Type> functionTypes) throws VariableAssignmentTypeException {
		String variableName = nodes.get(1).getToken().getValue();
		Type type = functionTypes.getPassedValues().get(nodes.get(3));
		return Collections.singletonMap(variableName, type);
	}

	@Override
	public boolean shouldWalkDownAt(ParseTreeNode node, List<ParseTreeNode> nodes) {
		return nodes.indexOf(node) > 1;
	}

	@Override
	public Type getType(List<ParseTreeNode> nodes, MapWithErrors<ParseTreeNode, Type> types) throws TypeInferrorException {
		return Void.class;
	}

	@Override
	public Definition<T, ?> construct(List<Token> tokens, List<Function<?, ?>> functions) {
		Function<?, ?> definition = functions.get(0);
		String variableName = tokens.get(1).getValue();
		if (definition instanceof ReflexiveFunction<?>) {
			return new MemberDefinition<>(variableName, (ReflexiveFunction<T>) definition);
		} else if (definition instanceof Evaluable<?>) {
			return new EvaluableDefinition<>(variableName, (Evaluable<T>) definition);
		} else {
			return new SetDefinition<>(variableName, (SetFunction<T>) definition);
		}
	}

	@Override
	public List<ParseTreeNode> getVariables(List<ParseTreeNode> nodes) {
		return getSingleVariableWithIndex(nodes, 3);
	}
}
