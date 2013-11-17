package logic.function.set.simple;

import javafx.util.Pair;
import logic.Nameable;
import logic.function.Function;
import logic.function.factory.validation.checking.CheckerWithNumber;
import logic.function.factory.validation.checking.checkers.FunctionOrVariableChecker;
import logic.function.factory.validation.checking.checkers.NumberedChecker;
import logic.function.reflexive.ReflexiveFunction;
import logic.function.reflexive.identity.IdentityFunction;
import logic.function.set.SetFunctionFactory;
import logic.set.Set;
import logic.type.TypeInferrorException;
import logic.type.map.MapWithErrors;
import reading.lexing.Token;
import reading.parsing.ParseTreeNode;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static logic.function.factory.validation.checking.CheckerWithNumber.Number.MANY;

/**
 * @author Steven Weston
 */
public class SimpleSetFactory<T extends Nameable> extends SetFunctionFactory<T, SimpleSet<T>> {

	public SimpleSetFactory() {
		super(getCheckers(), Arrays.asList(new Pair<>("{", "}")));
	}

	private static List<CheckerWithNumber> getCheckers() {
		return Arrays.<CheckerWithNumber>asList(
				new NumberedChecker(MANY, new FunctionOrVariableChecker(Arrays.<Class>asList(ReflexiveFunction.class)))
		);
	}

	public static <T extends Nameable> SimpleSet<T> createElement(String... memberStrings) {
		java.util.Set<ReflexiveFunction<T>> members = new HashSet<>();
		for (String memberString : memberStrings) {
			members.add(new IdentityFunction<>(memberString));
		}
		return new SimpleSet<>(members);
	}

	@Override
	public Type getType(List<ParseTreeNode> nodes, MapWithErrors<ParseTreeNode, Type> types) throws TypeInferrorException {
		return Set.class;
	}

	@Override
	public SimpleSet<T> construct(List<Token> tokens, List<Function<?, ?>> functions) {
		java.util.Set<ReflexiveFunction<T>> members = new HashSet<>();
		for (Function<?, ?> function : functions) {
			members.add((ReflexiveFunction<T>) function);
		}
		return new SimpleSet<>(members);
	}
}
