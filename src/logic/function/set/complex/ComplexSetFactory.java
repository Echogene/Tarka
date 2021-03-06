package logic.function.set.complex;

import javafx.util.Pair;
import logic.Nameable;
import logic.factory.FactoryException;
import logic.function.Function;
import logic.function.evaluable.Evaluable;
import logic.function.evaluable.predicate.membership.MembershipPredicate;
import logic.function.factory.validation.checking.CheckerWithNumber;
import logic.function.factory.validation.checking.checkers.FunctionOrVariableChecker;
import logic.function.factory.validation.checking.checkers.OperatorChecker;
import logic.function.factory.validation.checking.checkers.VariableChecker;
import logic.function.set.SetFunction;
import logic.function.set.SetFunctionFactory;
import logic.oldtype.VariableAssignerFactory;
import logic.oldtype.VariableAssignmentTypeException;
import logic.oldtype.map.MapWithErrors;
import ophelia.util.MapUtils;
import reading.lexing.Token;
import reading.parsing.ParseTreeNode;

import java.lang.reflect.Type;
import java.util.*;

/**
 * @author Steven Weston
 */
public class ComplexSetFactory<T extends Nameable> extends SetFunctionFactory<T, ComplexSet<T>> implements VariableAssignerFactory {

	public ComplexSetFactory(Class<T> universeType) {
		super(getCheckers(), Arrays.asList(new Pair<>("{", "}")), universeType);
	}

	private static List<CheckerWithNumber> getCheckers() {
		return Arrays.asList(
				new VariableChecker(),
				new OperatorChecker(MembershipPredicate.MEMBERSHIP_SYMBOL),
				new FunctionOrVariableChecker(SetFunction.class),
				new OperatorChecker(ComplexSet.SUCH_THAT),
				new FunctionOrVariableChecker(Evaluable.class)
		);
	}

	@Override
	public List<ParseTreeNode> getVariables(List<ParseTreeNode> nodes) {
		return getAllVariablesExcept(nodes, 1);
	}

	@Override
	public Set<Type> guessTypes(ParseTreeNode variable, List<ParseTreeNode> nodes) {
		if (nodes.indexOf(variable) < 4) {
			return Collections.singleton(logic.set.Set.class);
		} else {
			return Collections.singleton(Boolean.class);
		}
	}

	@Override
	public ComplexSet<T> construct(List<Token> tokens, List<Function<T, ?, ?>> functions, Map<String, Set<Type>> boundVariables) throws FactoryException {
		String variable = tokens.get(1).getValue();
		return new ComplexSet<>(variable, (SetFunction<T, ?>) functions.get(0), (Evaluable<T, ?>) functions.get(1));
	}

	@Override
	public Map<String, Set<Type>> assignVariableTypes(List<ParseTreeNode> nodes, MapWithErrors<ParseTreeNode, Set<Type>> functionTypes, Map<String, Set<Type>> freeVariables) throws VariableAssignmentTypeException {
		String variable = nodes.get(1).getValue();
		return MapUtils.createMap(variable, Collections.singleton(getUniverseType()));
	}

	@Override
	public boolean shouldWalkDownAt(ParseTreeNode node, List<ParseTreeNode> nodes) {
		return nodes.indexOf(node) > 1;
	}

	@Override
	public List<String> getVariablesToAssign(List<ParseTreeNode> surroundedChildren) {

		return Collections.singletonList(surroundedChildren.get(1).getValue());
	}
}
