package logic.function.evaluable.statements.binary;

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

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.OPEN_BRACKET;

/**
 * @author Steven Weston
 */
public class BinaryStatementFactory<T extends Nameable> extends EvaluableFactory<T, BinaryStatement<T>> {

	private final BinaryConnectiveFactory binaryConnectiveFactory;

	public BinaryStatementFactory(Class<T> universeType) {
		super(getCheckers(), STANDARD_BRACKETS, universeType);
		this.binaryConnectiveFactory = new BinaryConnectiveFactory();
	}

	private static List<CheckerWithNumber> getCheckers() {
		return Arrays.asList(
				new FunctionOrVariableChecker(Evaluable.class),
				new OperatorChecker(BinaryConnective.BINARY_CONNECTIVE_SYMBOL_LIST),
				new FunctionOrVariableChecker(Evaluable.class)
		);
	}

	@Override
	public BinaryStatement<T> construct(List<Token> tokens, List<Function<T, ?>> functions) throws FactoryException {
		Boolean firstBracket = tokens.get(1).isOfType(OPEN_BRACKET);
		String connectieString = tokens.get(firstBracket ? 3 : 2).getValue();
		BinaryConnective connective = binaryConnectiveFactory.createElement(connectieString);
		return new BinaryStatement<>((Evaluable<T>) functions.get(0), connective, (Evaluable<T>) functions.get(1));
	}

	@Override
	public List<ParseTreeNode> getVariables(List<ParseTreeNode> nodes) {
		return getAllVariables(nodes);
	}

	@Override
	public Set<Type> guessTypes(ParseTreeNode variable, List<ParseTreeNode> nodes) {
		return Collections.singleton(Boolean.class);
	}
}
