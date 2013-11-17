package logic.function.ifelse;

import javafx.util.Pair;
import logic.Nameable;
import logic.factory.FactoryException;
import logic.function.Function;
import logic.function.evaluable.Evaluable;
import logic.function.factory.FunctionFactory;
import logic.function.factory.validation.checking.CheckerWithNumber;
import logic.function.factory.validation.checking.checkers.FunctionOrVariableChecker;
import logic.function.factory.validation.checking.checkers.StringChecker;
import logic.function.reflexive.ReflexiveFunction;
import logic.function.set.SetFunction;
import logic.function.voidfunction.VoidFunction;
import logic.type.TypeInferrorException;
import logic.type.map.MapWithErrors;
import reading.lexing.Token;
import reading.parsing.ParseTreeNode;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

/**
 * @author Steven Weston
 */
public class IfElseFactory<T extends Nameable> extends FunctionFactory<T, Object, IfElse<T, ?>> {

	public static final String IF = "if";
	public static final String OTHERWISE = "otherwise";

	public IfElseFactory() {
		super(getCheckers(), Arrays.asList(new Pair<>("(", ")")));
	}

	private static List<CheckerWithNumber> getCheckers() {
		return Arrays.asList(
				new FunctionOrVariableChecker(Function.class),
				new StringChecker(IF),
				new FunctionOrVariableChecker(Evaluable.class),
				new StringChecker(OTHERWISE),
				new FunctionOrVariableChecker(Function.class)
				// todo: somehow validate that the first and third functions are the same type
		);
	}

	@Override
	public Type getType(List<ParseTreeNode> nodes, MapWithErrors<ParseTreeNode, Type> types) throws TypeInferrorException {
		return types.getPassedValues().get(nodes.get(1));
	}

	@Override
	public IfElse<T, ?> construct(List<Token> tokens, List<Function<?, ?>> functions) throws FactoryException {
		Function<?, ?> ifTrue = functions.get(0);
		Function<?, ?> condition = functions.get(1);
		Function<?, ?> ifFalse = functions.get(2);
		if (ifTrue instanceof ReflexiveFunction<?>) {
			if (!(ifFalse instanceof ReflexiveFunction<?>)) {
				throw new FactoryException("The true and false cases were different types.");
			}
			return new ReflexiveIfElse<>((Evaluable<T>) condition, (ReflexiveFunction<T>) ifTrue, (ReflexiveFunction<T>) ifFalse);
		} else if (ifTrue instanceof Evaluable<?>) {
			if (!(ifFalse instanceof Evaluable<?>)) {
				throw new FactoryException("The true and false cases were different types.");
			}
			return new EvaluableIfElse<>((Evaluable<T>) condition, (Evaluable<T>) ifTrue, (Evaluable<T>) ifFalse);
		} else if (ifTrue instanceof VoidFunction<?>) {
			if (!(ifFalse instanceof VoidFunction<?>)) {
				throw new FactoryException("The true and false cases were different types.");
			}
			return new VoidIfElse<>((Evaluable<T>) condition, (VoidFunction<T>) ifTrue, (VoidFunction<T>) ifFalse);
		} else if (ifTrue instanceof SetFunction<?>) {
			if (!(ifFalse instanceof SetFunction<?>)) {
				throw new FactoryException("The true and false cases were different types.");
			}
			return new SetIfElse<>((Evaluable<T>) condition, (SetFunction<T>) ifTrue, (SetFunction<T>) ifFalse);
		} else {
			throw new FactoryException("Unknown function type.");
		}
	}
}
