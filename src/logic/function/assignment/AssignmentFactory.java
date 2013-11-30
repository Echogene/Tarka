package logic.function.assignment;

import logic.Nameable;
import logic.factory.FactoryException;
import logic.function.Function;
import logic.function.evaluable.Evaluable;
import logic.function.factory.FunctionFactory;
import logic.function.factory.validation.checking.CheckerWithNumber;
import logic.function.factory.validation.checking.checkers.FunctionOrVariableChecker;
import logic.function.factory.validation.checking.checkers.StringChecker;
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
import java.util.*;

import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.OPEN_BRACKET;

/**
 * @author Steven Weston
 */
public class AssignmentFactory<T extends Nameable> extends FunctionFactory<T, Object, Assignment<T, ?>> implements VariableAssignerFactory {

	public AssignmentFactory(Class<T> universeType) {
		super(getCheckers(), STANDARD_BRACKETS, universeType);
	}

	private static List<CheckerWithNumber> getCheckers() {
		return Arrays.asList(
				new FunctionOrVariableChecker(),
				new StringChecker(Assignment.WHERE),
				new VariableChecker(),
				new StringChecker(Assignment.IS),
				new FunctionOrVariableChecker(
						ReflexiveFunction.class,
						Evaluable.class,
						SetFunction.class
				)
		);
	}

	@Override
	public Assignment<T, ?> construct(List<Token> tokens, List<Function<?, ?>> functions) throws FactoryException {
		if (functions.size() != 2) {
			throw new FactoryException("There were not the correct number of functions.");
		}
		Function<?, ?> evaluee = functions.get(0);
		String assignee;
		if (tokens.get(1).isOfType(OPEN_BRACKET)) {
			assignee = tokens.get(4).getValue();
		} else {
			assignee = tokens.get(3).getValue();
		}
		Function<T, ?> assignment = (Function<T, ?>) functions.get(1);
		if (evaluee instanceof ReflexiveFunction) {
			return new ReflexiveAssignment<>((ReflexiveFunction<T>) evaluee, assignee, assignment);
		} else if (evaluee instanceof Evaluable) {
			return new EvaluableAssignment<>((Evaluable<T>) evaluee, assignee, assignment);
		} else if (evaluee instanceof SetFunction) {
			return new SetAssignment<>((SetFunction<T>) evaluee, assignee, assignment);
		} else if (evaluee instanceof VoidFunction) {
			return new VoidAssignment<>((VoidFunction<T>) evaluee, assignee, assignment);
		} else {
			throw new FactoryException("Unknown function type.");
		}
	}

	@Override
	public Type getType(List<ParseTreeNode> nodes, MapWithErrors<ParseTreeNode, Type> types) throws TypeInferrorException {
		return types.getPassedValues().get(nodes.get(1));
	}

	@Override
	public Map<String, Type> assignVariableTypes(List<ParseTreeNode> nodes, MapWithErrors<ParseTreeNode, Type> functionTypes) throws VariableAssignmentTypeException {
		String variable;
		Type type;
		if (nodes.get(1).getToken().isOfType(OPEN_BRACKET)) {
			variable = nodes.get(4).getToken().getValue();
			type = functionTypes.getPassedValues().get(nodes.get(6));
		} else {
			variable = nodes.get(3).getToken().getValue();
			type = functionTypes.getPassedValues().get(nodes.get(5));
		}
		return Collections.singletonMap(variable, type);
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
	public List<ParseTreeNode> getVariables(List<ParseTreeNode> nodes) {
		ParseTreeNode firstNode = nodes.get(1);
		if (firstNode.getToken().isOfType(OPEN_BRACKET)) {
			ParseTreeNode secondNode = nodes.get(6);
			if (secondNode.getToken().isOfType(OPEN_BRACKET)) {
				return new ArrayList<>();
			} else {
				return Arrays.asList(secondNode);
			}
		} else {
			ParseTreeNode secondNode = nodes.get(5);
			if (secondNode.getToken().isOfType(OPEN_BRACKET)) {
				return Arrays.asList(firstNode);
			} else {
				return Arrays.asList(firstNode, secondNode);
			}
		}
	}
}
