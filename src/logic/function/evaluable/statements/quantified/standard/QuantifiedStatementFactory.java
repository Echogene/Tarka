package logic.function.evaluable.statements.quantified.standard;

import javafx.util.Pair;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static logic.function.evaluable.statements.quantified.standard.Quantifier.QUANTIFIER_SYMBOL_LIST;

/**
 * @author Steven Weston
 */
public class QuantifiedStatementFactory<T extends Nameable> extends EvaluableFactory<T, QuantifiedStatement<T>> implements VariableAssignerFactory {

	private final QuantifierFactory quantifierFactory;

	public QuantifiedStatementFactory() {
		super(getCheckers(), Arrays.asList(new Pair<>("(", ")")));
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
	public Map<String, Type> assignVariableTypes(List<ParseTreeNode> nodes, MapWithErrors<ParseTreeNode, Type> functionTypes) throws VariableAssignmentTypeException {
		String variable = nodes.get(2).getToken().getValue();
		Type UNIVERSE_TYPE = maths.number.integer.Integer.class; //todo
		return Collections.singletonMap(variable, UNIVERSE_TYPE);
	}

	@Override
	public QuantifiedStatement<T> construct(List<Token> tokens, List<Function<?, ?>> functions) throws FactoryException {
		Quantifier quantifier = quantifierFactory.createElement(tokens.get(1).getValue());
		String variable = tokens.get(2).getValue();
		Evaluable<T> evaluable = (Evaluable<T>) functions.get(1);
		return new QuantifiedStatement<>(quantifier, variable, evaluable);
	}
}
