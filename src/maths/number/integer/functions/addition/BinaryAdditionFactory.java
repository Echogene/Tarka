package maths.number.integer.functions.addition;

import javafx.util.Pair;
import logic.factory.FactoryException;
import logic.function.Function;
import logic.function.factory.validation.checking.CheckerWithNumber;
import logic.function.factory.validation.checking.checkers.FunctionOrVariableChecker;
import logic.function.factory.validation.checking.checkers.OperatorChecker;
import logic.function.reflexive.ReflexiveFunction;
import logic.function.reflexive.ReflexiveFunctionFactory;
import logic.type.TypeInferrorException;
import logic.type.map.MapWithErrors;
import maths.number.Number;
import maths.number.Summor;
import reading.lexing.Token;
import reading.parsing.ParseTreeNode;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Steven Weston
 */
public class BinaryAdditionFactory<N extends Number> extends ReflexiveFunctionFactory<N, Addition<N>> {

	private final Summor<N> summor;

	public BinaryAdditionFactory(Summor<N> summor) {
		super(getCheckers(), Arrays.asList(new Pair<>("(", ")")));
		this.summor = summor;
	}

	private static List<CheckerWithNumber> getCheckers() {
		return Arrays.asList(
				new FunctionOrVariableChecker(ReflexiveFunction.class),
				new OperatorChecker(Addition.PLUS_SYMBOL),
				new FunctionOrVariableChecker(ReflexiveFunction.class)
		);
	}

	@Override
	public Addition<N> construct(List<Token> tokens, List<Function<?, ?>> functions) throws FactoryException {
		List<ReflexiveFunction<N>> parameters = new ArrayList<>(2);
		parameters.add((ReflexiveFunction<N>) functions.get(0));
		parameters.add((ReflexiveFunction<N>) functions.get(1));
		return new Addition<>(parameters, summor);
	}

	@Override
	public Type getType(List<ParseTreeNode> nodes, MapWithErrors<ParseTreeNode, Type> types) throws TypeInferrorException {
		return types.getPassedValues().get(nodes.get(1));
	}
}
