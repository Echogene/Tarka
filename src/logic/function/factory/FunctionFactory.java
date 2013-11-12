package logic.function.factory;

import logic.Nameable;
import logic.factory.Factory;
import logic.factory.FactoryException;
import logic.function.Function;
import logic.function.factory.construction.ValidatorAndConstructor;
import logic.function.factory.validation.token.TokenValidator;
import logic.type.TypeMatcher;
import reading.lexing.Token;

import java.util.ArrayList;
import java.util.List;

import static util.StringUtils.join;

/**
 * @author Steven Weston
 */
public abstract class FunctionFactory<D extends Nameable, C, F extends Function<D, ?>>
		implements
				Factory<F>,
				TokenValidator,
				TypeMatcher
{

	protected final List<ValidatorAndConstructor<F>> constructors;

	protected FunctionFactory(List<ValidatorAndConstructor<F>> constructors) {
		this.constructors = constructors;
	}

	@Override
	public final F createElement(List<Token> tokens) throws FactoryException {
		return createElement(tokens, null);
	}

	public final F createElement(List<Token> tokens, List<Function<?, ?>> functions) throws FactoryException {
		List<String> failureMessages = new ArrayList<>();
		for (ValidatorAndConstructor<F> constructor : constructors) {
			try {
				return constructor.construct(tokens, functions);
			} catch (ValidationException e) {
				failureMessages.add(e.getMessage());
			}
		}
		throw new FactoryException(join(failureMessages, "\n"));
	}
}
