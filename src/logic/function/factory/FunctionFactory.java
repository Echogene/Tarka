package logic.function.factory;

import logic.Nameable;
import logic.factory.Factory;
import logic.factory.FactoryException;
import logic.function.Function;
import logic.function.factory.construction.ValidatorAndConstructor;
import reading.lexing.Token;

import java.util.ArrayList;
import java.util.List;

import static util.CollectionUtils.join;

/**
 * @author Steven Weston
 */
public abstract class FunctionFactory<D extends Nameable, C> implements Factory<Function<D, C>> {

	protected final List<ValidatorAndConstructor<Function<D, C>>> constructors;

	protected FunctionFactory(List<ValidatorAndConstructor<Function<D, C>>> constructors) {
		this.constructors = constructors;
	}

	@Override
	public final Function<D, C> createElement(List<Token> tokens) throws FactoryException {
		return createElement(tokens, null);
	}

	public final Function<D, C> createElement(List<Token> tokens, List<Function<?, ?>> functions) throws FactoryException {
		List<String> failureMessages = new ArrayList<>();
		for (ValidatorAndConstructor<Function<D, C>> constructor : constructors) {
			try {
				return constructor.construct(tokens, functions);
			} catch (ValidationException e) {
				failureMessages.add(e.getMessage());
			}
		}
		throw new FactoryException(join(failureMessages, "\n"));
	}
}
