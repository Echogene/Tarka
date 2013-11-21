package logic.function.evaluable.statements.quantified.restricted;

import javafx.util.Pair;
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

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Steven Weston
 */
public class RestrictedQuantifiedStatementFactory<T extends Nameable>
		extends EvaluableFactory<T, RestrictedQuantifiedStatement<T>>
		implements VariableAssignerFactory
{

	private final QuantifierFactory quantifierFactory;

	public RestrictedQuantifiedStatementFactory(Class<T> universeType) {
		super(getCheckers(), Arrays.asList(new Pair<>("(", ")")), universeType);
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
	public Map<String, Type> assignVariableTypes(List<ParseTreeNode> nodes, MapWithErrors<ParseTreeNode, Type> functionTypes) throws VariableAssignmentTypeException {
		String variable = nodes.get(2).getToken().getValue();
		Type UNIVERSE_TYPE = maths.number.integer.Integer.class; //todo
		return Collections.singletonMap(variable, UNIVERSE_TYPE);
	}

	@Override
	public boolean shouldWalkDownAt(ParseTreeNode node, List<ParseTreeNode> nodes) {
		return nodes.indexOf(node) > 3;
	}

	@Override
	public RestrictedQuantifiedStatement<T> construct(List<Token> tokens, List<Function<?, ?>> functions) throws FactoryException {
		Quantifier quantifier = quantifierFactory.createElement(tokens.get(1).getValue());
		String variable = tokens.get(2).getValue();
		SetFunction<T> setFunction = (SetFunction<T>) functions.get(1);
		Evaluable<T> evaluable = (Evaluable<T>) functions.get(2);
		return new RestrictedQuantifiedStatement<>(quantifier, variable, setFunction, evaluable);
	}
}
