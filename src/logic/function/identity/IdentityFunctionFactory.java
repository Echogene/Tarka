package logic.function.identity;

import javafx.util.Pair;
import logic.Nameable;
import logic.factory.FactoryException;
import logic.function.Function;
import logic.function.evaluable.Evaluable;
import logic.function.factory.ConstructorFromString;
import logic.function.factory.FunctionFactory;
import logic.function.factory.validation.checking.CheckerWithNumber;
import logic.function.factory.validation.checking.checkers.FunctionOrVariableChecker;
import logic.function.reflexive.ReflexiveFunction;
import logic.function.set.SetFunction;
import logic.set.Set;
import logic.type.TypeInferrorException;
import logic.type.map.MapWithErrors;
import reading.lexing.Token;
import reading.parsing.ParseTreeNode;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.OPEN_BRACKET;

/**
 * @author Steven Weston
 */
public class IdentityFunctionFactory<T extends Nameable>
		extends FunctionFactory<T, Object, IdentityFunction<T, ?>>
		implements
				ConstructorFromString<MemberIdentityFunction<T>>,
				IdentityConstructorFromType<T>  {

	public IdentityFunctionFactory(Class<T> universeType) {
		super(getCheckers(), Arrays.asList(new Pair<>("(", ")")), universeType);
	}

	private static List<CheckerWithNumber> getCheckers() {
		return Arrays.<CheckerWithNumber>asList(
				new FunctionOrVariableChecker(
						ReflexiveFunction.class,
						Evaluable.class,
						SetFunction.class
				)
		);
	}

	@Override
	public MemberIdentityFunction<T> construct(String parameterName) {
		return new MemberIdentityFunction<>(parameterName);
	}

	@Override
	public Type getType(List<ParseTreeNode> nodes, MapWithErrors<ParseTreeNode, Type> types) throws TypeInferrorException {
		return types.getPassedValues().get(nodes.get(1));
	}

	@Override
	public IdentityFunction<T, ?> construct(List<Token> tokens, List<Function<?, ?>> functions) throws FactoryException {
		Function<?, ?> function = functions.get(0);
		if (tokens.get(1).isOfType(OPEN_BRACKET)) {
			if (function instanceof ReflexiveFunction<?>) {
				return new MemberIdentityFunction<>((ReflexiveFunction<T>) function);
			} else if (function instanceof Evaluable<?>) {
				return new EvaluableIdentityFunction<>((Evaluable<T>) function);
			} else {
				return new SetIdentityFunction<>((SetFunction<T>) function);
			}
		} else {
			return (IdentityFunction<T, ?>) function;
		}
	}

	@Override
	public IdentityFunction<T, ?> create(String parameter, Type type) {
		if (type == Boolean.class) {
			return new EvaluableIdentityFunction<>(parameter);
		} else if (type == Set.class) {
			return new SetIdentityFunction<>(parameter);
		} else {
			return new MemberIdentityFunction<>(parameter);
		}
	}

	@Override
	public List<ParseTreeNode> getVariables(List<ParseTreeNode> nodes) {
		return getSingleVariableWithIndex(nodes, 1);
	}
}
