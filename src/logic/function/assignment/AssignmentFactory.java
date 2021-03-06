package logic.function.assignment;

import logic.Nameable;
import logic.factory.FactoryException;
import logic.function.Function;
import logic.function.evaluable.Evaluable;
import logic.function.factory.FunctionFactory;
import logic.function.factory.validation.checking.CheckerWithNumber;
import logic.function.factory.validation.checking.checkers.FunctionOrVariableChecker;
import logic.function.factory.validation.checking.checkers.NonVoidFunctionOrVariableChecker;
import logic.function.factory.validation.checking.checkers.StringChecker;
import logic.function.factory.validation.checking.checkers.VariableChecker;
import logic.function.reflexive.ReflexiveFunction;
import logic.function.set.SetFunction;
import logic.function.voidfunction.VoidFunction;
import logic.oldtype.TypeInferrorException;
import logic.oldtype.VariableAssignerFactory;
import logic.oldtype.VariableAssignmentTypeException;
import logic.oldtype.map.MapWithErrors;
import ophelia.util.MapUtils;
import reading.lexing.Token;
import reading.parsing.ParseTreeNode;

import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.util.*;

import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.OPEN_BRACKET;

/**
 * @author Steven Weston
 */
public class AssignmentFactory<T extends Nameable>
		extends FunctionFactory<T, Object, AbstractAssignment<T, ?, ?, ?>>
		implements VariableAssignerFactory {

	public AssignmentFactory(Class<T> universeType) {
		super(getCheckers(), STANDARD_BRACKETS, universeType);
	}

	private static List<CheckerWithNumber> getCheckers() {
		return Arrays.asList(
				new FunctionOrVariableChecker(),
				new StringChecker(AbstractAssignment.WHERE),
				new VariableChecker(),
				new StringChecker(AbstractAssignment.IS),
				new NonVoidFunctionOrVariableChecker()
		);
	}

	@Override
	public AbstractAssignment<T, ?, ?, ?> construct(List<Token> tokens, List<Function<T, ?, ?>> functions, Map<String, Set<Type>> boundVariables) throws FactoryException {
		if (functions.size() != 2) {
			throw new FactoryException("There were not the correct number of functions.");
		}
		Function<T, ?, ?> evaluee = functions.get(0);
		String assignee;
		if (tokens.get(1).isOfType(OPEN_BRACKET)) {
			assignee = tokens.get(4).getValue();
		} else {
			assignee = tokens.get(3).getValue();
		}
		Function<T, ?, ?> assignment = functions.get(1);
		if (evaluee instanceof ReflexiveFunction) {
			return new ReflexiveAssignment<>((ReflexiveFunction<T, ?>) evaluee, assignee, assignment);
		} else if (evaluee instanceof Evaluable) {
			return new EvaluableAssignment<>((Evaluable<T, ?>) evaluee, assignee, assignment);
		} else if (evaluee instanceof SetFunction) {
			return new SetAssignment<>((SetFunction<T, ?>) evaluee, assignee, assignment);
		} else if (evaluee instanceof VoidFunction) {
			return new VoidAssignment<>((VoidFunction<T, ?>) evaluee, assignee, assignment);
		} else {
			throw new FactoryException(
					MessageFormat.format(
							"The evaluee {0} had the unknown/unimplemented class {1}.\nAre you trying to obtain multiple return types?",
							evaluee,
							evaluee.getClass().getSimpleName()
					)
			);
		}
	}

	@Override
	public Set<Type> getTypes(List<ParseTreeNode> nodes, MapWithErrors<ParseTreeNode, Set<Type>> types) throws TypeInferrorException {
		return types.getPassedValues().get(nodes.get(1));
	}

	@Override
	public Map<String, Set<Type>> assignVariableTypes(List<ParseTreeNode> nodes, MapWithErrors<ParseTreeNode, Set<Type>> functionTypes, Map<String, Set<Type>> freeVariables) throws VariableAssignmentTypeException {
		String variable;
		Set<Type> types;
		if (nodes.get(1).getToken().isOfType(OPEN_BRACKET)) {
			variable = nodes.get(4).getValue();
			types = functionTypes.getPassedValues().get(nodes.get(6));
		} else {
			variable = nodes.get(3).getValue();
			types = functionTypes.getPassedValues().get(nodes.get(5));
		}
		return MapUtils.createMap(variable, types);
	}

	@Override
	public boolean shouldWalkDownAt(ParseTreeNode node, List<ParseTreeNode> nodes) {
		int index = nodes.indexOf(node);
		if (nodes.get(1).getToken().isOfType(OPEN_BRACKET)) {
			return index < 3 || index > 5;
		} else {
			return index < 2 || index > 4;
		}
	}

	@Override
	public List<String> getVariablesToAssign(List<ParseTreeNode> surroundedChildren) {

		if (surroundedChildren.get(1).getToken().isOfType(OPEN_BRACKET)) {
			return Collections.singletonList(surroundedChildren.get(4).getValue());
		} else {
			return Collections.singletonList(surroundedChildren.get(3).getValue());
		}
	}

	@Override
	public List<ParseTreeNode> getVariables(List<ParseTreeNode> nodes) {
		ParseTreeNode firstNode = nodes.get(1);
		ParseTreeNode secondNode;
		if (firstNode.getToken().isOfType(OPEN_BRACKET)) {
			secondNode = nodes.get(6);
		} else {
			secondNode = nodes.get(5);
		}
		return Arrays.asList(firstNode, secondNode);
	}

	@Override
	public Set<Type> guessTypes(ParseTreeNode variable, List<ParseTreeNode> nodes) {
		return nonVoidTypes;
	}

	@Override
	public Set<Type> getPotentialReturnTypes(List<ParseTreeNode> surroundedChildren) {
		return allTypes;
	}
}
