package logic.function.evaluable.statements.quantified.restricted;

import logic.Nameable;
import logic.factory.FactoryException;
import logic.function.Function;
import logic.function.evaluable.Evaluable;
import logic.function.evaluable.EvaluableFactory;
import logic.function.evaluable.predicate.membership.MembershipPredicate;
import logic.function.evaluable.statements.quantified.standard.Quantifier;
import logic.function.evaluable.statements.quantified.standard.QuantifierFactory;
import logic.function.factory.validation.checking.CheckerWithNumber;
import logic.function.factory.validation.checking.checkers.FunctionOrVariableChecker;
import logic.function.factory.validation.checking.checkers.OperatorChecker;
import logic.function.factory.validation.checking.checkers.QuantifierChecker;
import logic.function.factory.validation.checking.checkers.VariableChecker;
import logic.function.set.SetFunction;
import logic.type.VariableAssignerFactory;
import logic.type.VariableAssignmentTypeException;
import logic.type.map.MapWithErrors;
import reading.lexing.Token;
import reading.parsing.ParseTreeNode;
import util.MapUtils;

import java.lang.reflect.Type;
import java.util.*;

import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.OPEN_BRACKET;

/**
 * @author Steven Weston
 */
public class RestrictedQuantifiedStatementFactory<T extends Nameable>
		extends EvaluableFactory<T, RestrictedQuantifiedStatement<T>>
		implements VariableAssignerFactory
{

	private final QuantifierFactory quantifierFactory;

	public RestrictedQuantifiedStatementFactory(Class<T> universeType) {
		super(getCheckers(), STANDARD_BRACKETS, universeType);
		this.quantifierFactory = new QuantifierFactory();
	}

	private static List<CheckerWithNumber> getCheckers() {
		return Arrays.asList(
				new QuantifierChecker(Quantifier.QUANTIFIER_SYMBOL_LIST),
				new VariableChecker(),
				new OperatorChecker(MembershipPredicate.MEMBERSHIP_SYMBOL),
				new FunctionOrVariableChecker(SetFunction.class),
				new FunctionOrVariableChecker(Evaluable.class)
		);
	}

	@Override
	public Map<String, Set<Type>> assignVariableTypes(List<ParseTreeNode> nodes, MapWithErrors<ParseTreeNode, Set<Type>> functionTypes, Map<String, Set<Type>> freeVariables) throws VariableAssignmentTypeException {
		String variable = nodes.get(2).getToken().getValue();
		return MapUtils.createMap(variable, Collections.singleton(getUniverseType()));
	}

	@Override
	public boolean shouldWalkDownAt(ParseTreeNode node, List<ParseTreeNode> nodes) {
		return nodes.indexOf(node) > 3;
	}

	@Override
	public List<String> getVariablesToAssign(List<ParseTreeNode> surroundedChildren) {

		return Collections.singletonList(surroundedChildren.get(2).getToken().getValue());
	}

	@Override
	public RestrictedQuantifiedStatement<T> construct(List<Token> tokens, List<Function<T, ?, ?>> functions, Map<String, Set<Type>> boundVariables) throws FactoryException {
		Quantifier quantifier = quantifierFactory.createElement(tokens.get(1).getValue());
		String variable = tokens.get(2).getValue();
		SetFunction<T, ?> setFunction = (SetFunction<T, ?>) functions.get(0);
		Evaluable<T, ?> evaluable = (Evaluable<T, ?>) functions.get(1);
		return new RestrictedQuantifiedStatement<>(quantifier, variable, setFunction, evaluable);
	}

	@Override
	public List<ParseTreeNode> getVariables(List<ParseTreeNode> nodes) {
		ParseTreeNode firstNode = nodes.get(4);
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

	@Override
	public Set<Type> guessTypes(ParseTreeNode variable, List<ParseTreeNode> nodes) {
		if (nodes.indexOf(variable) < 4) {
			return Collections.singleton(logic.set.Set.class);
		} else {
			return Collections.singleton(Boolean.class);
		}
	}
}
