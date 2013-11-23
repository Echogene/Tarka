package logic.function.evaluable.statements.unary;

import javafx.util.Pair;
import logic.Nameable;
import logic.factory.FactoryException;
import logic.function.Function;
import logic.function.evaluable.Evaluable;
import logic.function.evaluable.EvaluableFactory;
import logic.function.factory.validation.checking.CheckerWithNumber;
import logic.function.factory.validation.checking.checkers.FunctionOrVariableChecker;
import logic.function.factory.validation.checking.checkers.OperatorChecker;
import reading.lexing.Token;
import reading.parsing.ParseTreeNode;

import java.util.Arrays;
import java.util.List;

import static logic.function.evaluable.statements.unary.UnaryConnective.UNARY_CONNECTIVE_SYMBOL_LIST;

/**
 * @author Steven Weston
 */
public class UnaryStatementFactory<T extends Nameable> extends EvaluableFactory<T, UnaryStatement<T>> {

	private final UnaryConnectiveFactory unaryConnectiveFactory;

	public UnaryStatementFactory(Class<T> universeType) {
		super(getCheckers(), Arrays.asList(new Pair<>("(", ")")), universeType);
		this.unaryConnectiveFactory = new UnaryConnectiveFactory();
	}

	private static List<CheckerWithNumber> getCheckers() {
		return Arrays.asList(
				new OperatorChecker(UNARY_CONNECTIVE_SYMBOL_LIST),
				new FunctionOrVariableChecker(Evaluable.class)
		);
	}

	@Override
	public UnaryStatement<T> construct(List<Token> tokens, List<Function<?, ?>> functions) throws FactoryException {
		UnaryConnective connective = unaryConnectiveFactory.createElement(tokens.get(1).getValue());
		Evaluable<T> function = (Evaluable<T>) functions.get(0);
		return new UnaryStatement<>(connective, function);
	}

	@Override
	public List<ParseTreeNode> getVariables(List<ParseTreeNode> nodes) {
		return getSingleVariableWithIndex(nodes, 2);
	}
}
