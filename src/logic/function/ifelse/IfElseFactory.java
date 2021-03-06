package logic.function.ifelse;

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
import logic.oldtype.TypeInferrorException;
import logic.oldtype.map.MapWithErrors;
import reading.lexing.Token;
import reading.parsing.ParseTreeNode;

import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.util.*;

import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.OPEN_BRACKET;

/**
 * @author Steven Weston
 */
public class IfElseFactory<T extends Nameable> extends FunctionFactory<T, Object, AbstractIfElse<T, ?, ?, ?>> {

	public IfElseFactory(Class<T> universeType) {
		super(getCheckers(), STANDARD_BRACKETS, universeType);
	}

	private static List<CheckerWithNumber> getCheckers() {
		return Arrays.asList(
				new FunctionOrVariableChecker(Function.class),
				new StringChecker(AbstractIfElse.IF),
				new FunctionOrVariableChecker(Evaluable.class),
				new StringChecker(AbstractIfElse.OTHERWISE),
				new FunctionOrVariableChecker(Function.class)
				// todo: somehow validate that the first and third functions are the same type
		);
	}

	@Override
	public Set<Type> getTypes(List<ParseTreeNode> nodes, MapWithErrors<ParseTreeNode, Set<Type>> types) throws TypeInferrorException {
		return types.getPassedValues().get(nodes.get(1));
	}

	@Override
	public AbstractIfElse<T, ?, ?, ?> construct(
			List<Token> tokens,
			List<Function<T, ?, ?>> functions,
			Map<String, Set<Type>> boundVariables
	) throws FactoryException {

		Function<?, ?, ?> ifTrue = functions.get(0);
		Function<?, ?, ?> condition = functions.get(1);
		Function<?, ?, ?> ifFalse = functions.get(2);
		// todo: this might be able to be neater
		if (ifTrue instanceof ReflexiveFunction<?, ?>) {
			if (!(ifFalse instanceof ReflexiveFunction<?, ?>)) {
				throw new FactoryException("The true and false cases were different types.");
			}
			return new ReflexiveIfElse<>((ReflexiveFunction<T, ?>) ifTrue, (Evaluable<T, ?>) condition, (ReflexiveFunction<T, ?>) ifFalse);
		} else if (ifTrue instanceof Evaluable<?, ?>) {
			if (!(ifFalse instanceof Evaluable<?, ?>)) {
				throw new FactoryException("The true and false cases were different types.");
			}
			return new EvaluableIfElse<>((Evaluable<T, ?>) ifTrue, (Evaluable<T, ?>) condition, (Evaluable<T, ?>) ifFalse);
		} else if (ifTrue instanceof VoidFunction<?, ?>) {
			if (!(ifFalse instanceof VoidFunction<?, ?>)) {
				throw new FactoryException("The true and false cases were different types.");
			}
			return new VoidIfElse<>((VoidFunction<T, ?>) ifTrue, (Evaluable<T, ?>) condition, (VoidFunction<T, ?>) ifFalse);
		} else if (ifTrue instanceof SetFunction<?, ?>) {
			if (!(ifFalse instanceof SetFunction<?, ?>)) {
				throw new FactoryException("The true and false cases were different types.");
			}
			return new SetIfElse<>((SetFunction<T, ?>) ifTrue, (Evaluable<T, ?>) condition, (SetFunction<T, ?>) ifFalse);
		} else {
			throw new FactoryException(
					MessageFormat.format(
							"The if-true case {0} had the unknown/unimplemented class {1}.\nAre you trying to obtain multiple return types?",
							ifTrue,
							ifFalse.getClass().getSimpleName()
					)
			);
		}
	}

	@Override
	public List<ParseTreeNode> getVariables(List<ParseTreeNode> nodes) {
		ParseTreeNode firstNode = nodes.get(1);
		ParseTreeNode secondNode;
		ParseTreeNode thirdNode;
		if (firstNode.getToken().isOfType(OPEN_BRACKET)) {
			secondNode = nodes.get(4);
			if (secondNode.getToken().isOfType(OPEN_BRACKET)) {
				thirdNode = nodes.get(7);
			} else {
				thirdNode = nodes.get(6);
			}
		} else {
			secondNode = nodes.get(3);
			if (secondNode.getToken().isOfType(OPEN_BRACKET)) {
				thirdNode = nodes.get(6);
			} else {
				thirdNode = nodes.get(5);
			}
		}
		return Arrays.asList(firstNode, secondNode, thirdNode);
	}

	@Override
	public Set<Type> guessTypes(ParseTreeNode variable, List<ParseTreeNode> nodes) {
		// The type of a variable cannot be void.
		return nonVoidTypes;
	}

	@Override
	public Set<Type> getPotentialReturnTypes(List<ParseTreeNode> surroundedChildren) {
		return allTypes;
	}
}
