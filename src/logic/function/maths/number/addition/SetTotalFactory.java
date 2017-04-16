package logic.function.maths.number.addition;

import logic.factory.FactoryException;
import logic.function.Function;
import logic.function.factory.validation.checking.CheckerWithNumber;
import logic.function.factory.validation.checking.checkers.FunctionOrVariableChecker;
import logic.function.factory.validation.checking.checkers.OperatorChecker;
import logic.function.reflexive.ReflexiveFunctionFactory;
import logic.function.set.SetFunction;
import maths.number.Number;
import maths.number.Summor;
import reading.lexing.Token;
import reading.parsing.ParseTreeNode;

import java.lang.reflect.Type;
import java.util.*;

/**
 * @author Steven Weston
 */
public class SetTotalFactory<N extends Number> extends ReflexiveFunctionFactory<N, SetTotal<N>> {

	private final Summor<N> summor;

	public SetTotalFactory(Summor<N> summor, Class<N> universeType) {
		super(getCheckers(), STANDARD_BRACKETS, universeType);
		this.summor = summor;
	}

	private static List<CheckerWithNumber> getCheckers() {
		return Arrays.asList(
				new OperatorChecker(Addition.SUM_SYMBOL),
				new OperatorChecker("/"),
				new FunctionOrVariableChecker(SetFunction.class)
		);
	}

	@Override
	public SetTotal<N> construct(List<Token> tokens, List<Function<N, ?, ?>> functions, Map<String, Set<Type>> boundVariables) throws FactoryException {
		return new SetTotal<>((SetFunction<N, ?>) functions.get(0), summor);
	}

	@Override
	public List<ParseTreeNode> getVariables(List<ParseTreeNode> nodes) {
		return getAllVariables(nodes);
	}

	@Override
	public Set<Type> guessTypes(ParseTreeNode variable, List<ParseTreeNode> nodes) {
		return Collections.singleton(logic.set.Set.class);
	}
}
