package logic.function.evaluable.statements.quantified.standard;

import logic.Nameable;
import logic.factory.FactoryException;
import logic.function.Function;
import logic.function.evaluable.Evaluable;
import logic.function.evaluable.EvaluableFactory;
import logic.function.factory.validation.checking.CheckerWithNumber;
import logic.function.factory.validation.checking.checkers.FunctionOrVariableChecker;
import logic.function.factory.validation.checking.checkers.QuantifierChecker;
import logic.function.factory.validation.checking.checkers.VariableChecker;
import logic.type.VariableAssignerFactory;
import logic.type.VariableAssignmentTypeException;
import logic.type.map.MapWithErrors;
import reading.lexing.Token;
import reading.parsing.ParseTreeNode;

import java.lang.reflect.Type;
import java.util.*;

import static logic.function.evaluable.statements.quantified.standard.Quantifier.QUANTIFIER_SYMBOL_LIST;

/**
 * @author Steven Weston
 */
public class QuantifiedStatementFactory<T extends Nameable> extends EvaluableFactory<T, QuantifiedStatement<T>> implements VariableAssignerFactory {

	private final QuantifierFactory quantifierFactory;

	public QuantifiedStatementFactory(Class<T> universeType) {
		super(getCheckers(), STANDARD_BRACKETS, universeType);
		this.quantifierFactory = new QuantifierFactory();
	}

	private static List<CheckerWithNumber> getCheckers() {
		return Arrays.asList(
				new QuantifierChecker(QUANTIFIER_SYMBOL_LIST),
				new VariableChecker(),
				new FunctionOrVariableChecker(Evaluable.class)
		);
	}

	@Override
	public Map<String, Type> assignVariableTypes(
			List<ParseTreeNode> nodes,
			MapWithErrors<ParseTreeNode, Type> functionTypes,
			Map<String, Set<Type>> freeVariables) throws VariableAssignmentTypeException {
		String variable = nodes.get(2).getToken().getValue();
		return Collections.<String, Type>singletonMap(variable, getUniverseType());
	}

	@Override
	public boolean shouldWalkDownAt(ParseTreeNode node, List<ParseTreeNode> nodes) {
		return nodes.indexOf(node) > 2;
	}

	@Override
	public QuantifiedStatement<T> construct(List<Token> tokens, List<Function<T, ?>> functions) throws FactoryException {
		Quantifier quantifier = quantifierFactory.createElement(tokens.get(1).getValue());
		String variable = tokens.get(2).getValue();
		Evaluable<T> evaluable = (Evaluable<T>) functions.get(0);
		return new QuantifiedStatement<>(quantifier, variable, evaluable);
	}

	@Override
	public List<ParseTreeNode> getVariables(List<ParseTreeNode> nodes) {
		return getSingleVariableWithIndex(nodes, 3);
	}

	@Override
	public Set<Type> guessTypes(ParseTreeNode variable, List<ParseTreeNode> nodes) {
		return Collections.singleton(Boolean.class);
	}
}
