package logic.function.set.simple;

import javafx.util.Pair;
import logic.Nameable;
import logic.function.Function;
import logic.function.factory.validation.checking.CheckerWithNumber;
import logic.function.factory.validation.checking.checkers.FunctionOrVariableChecker;
import logic.function.factory.validation.checking.checkers.NumberedChecker;
import logic.function.identity.MemberIdentityFunction;
import logic.function.reflexive.ReflexiveFunction;
import logic.function.set.SetFunctionFactory;
import reading.lexing.Token;
import reading.parsing.ParseTreeNode;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static logic.function.factory.validation.checking.CheckerWithNumber.Number.MANY;

/**
 * @author Steven Weston
 */
public class SimpleSetFactory<T extends Nameable> extends SetFunctionFactory<T, SimpleSet<T>> {

	public SimpleSetFactory(Class<T> universeType) {
		super(getCheckers(), Arrays.asList(new Pair<>("{", "}")), universeType);
	}

	private static List<CheckerWithNumber> getCheckers() {
		return Arrays.<CheckerWithNumber>asList(
				new NumberedChecker(MANY, new FunctionOrVariableChecker(ReflexiveFunction.class))
		);
	}

	public static <T extends Nameable> SimpleSet<T> createElement(String... memberStrings) {
		java.util.Set<ReflexiveFunction<T>> members = new HashSet<>();
		for (String memberString : memberStrings) {
			members.add(new MemberIdentityFunction<>(memberString));
		}
		return new SimpleSet<>(members);
	}

	@SafeVarargs
	public static <T extends Nameable> SimpleSet<T> createElement(ReflexiveFunction<T>... members) {
		java.util.Set<ReflexiveFunction<T>> memberSet = new HashSet<>();
		for (ReflexiveFunction<T> member : members) {
			memberSet.add(member);
		}
		return new SimpleSet<>(memberSet);
	}

	@Override
	public SimpleSet<T> construct(List<Token> tokens, List<Function<?, ?>> functions) {
		java.util.Set<ReflexiveFunction<T>> members = new HashSet<>();
		for (Function<?, ?> function : functions) {
			members.add((ReflexiveFunction<T>) function);
		}
		return new SimpleSet<>(members);
	}

	@Override
	public List<ParseTreeNode> getVariables(List<ParseTreeNode> nodes) {
		return getAllVariables(nodes);
	}

}
