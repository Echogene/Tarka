package logic.function.set.simple;

import logic.Nameable;
import logic.factory.FactoryException;
import logic.function.Function;
import logic.function.factory.ValidationResult;
import logic.function.factory.multary.MultaryConstructor;
import logic.function.factory.multary.MultaryValidator;
import logic.function.reflexive.ReflexiveFunction;
import logic.function.reflexive.identity.IdentityFunction;
import logic.function.reflexive.identity.IdentityFunctionConstructorFromString;
import logic.function.set.SetFunctionFactory;
import logic.set.Set;
import reading.lexing.Token;

import java.util.HashSet;
import java.util.List;

/**
 * @author Steven Weston
 */
public class SimpleSetFactory<T extends Nameable> extends SetFunctionFactory<T> {

	MultaryValidator validator;
	MultaryConstructor<SimpleSet<T>, ReflexiveFunction<T>> constructor;

	public SimpleSetFactory() {
		validator = new MultaryValidator(null, ReflexiveFunction.class);
		constructor = new MultaryConstructor<>(
				new SimpleSetConstructorFromParameterList<>(),
				new IdentityFunctionConstructorFromString<T>()
		);
		constructor.setStartIndex(0);
	}

	public static <T extends Nameable> SimpleSet<T> createElement(String... memberStrings) {
		java.util.Set<ReflexiveFunction<T>> members = new HashSet<>();
		for (String memberString : memberStrings) {
			members.add(new IdentityFunction<>(memberString));
		}
		return new SimpleSet<>(members);
	}

	@Override
	public Function<T, Set<T>> createElement(List<Token> tokens, List<Function<?, ?>> functions) throws FactoryException {
		tokens = validateAndStripCurlyBrackets(tokens);
		ValidationResult result = validator.validate(tokens, functions);
		if (result.isValid()) {
			return constructor.construct(result, tokens, functions);
		}
		throw new FactoryException("Could not create " + SimpleSet.class.getSimpleName());
	}
}
