package maths.number.integer.functions.addition;

import logic.function.Function;
import logic.function.factory.construction.ValidatorAndConstructor;
import logic.function.reflexive.ReflexiveFunctionFactory;
import maths.number.Number;
import maths.number.Summor;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

/**
 * @author Steven Weston
 */
public class AdditionFactory<N extends Number> extends ReflexiveFunctionFactory<N> {
	private final Summor<N> summor;

	public AdditionFactory(Summor<N> summor) {
		super(getConstructors());
		this.summor = summor;
	}

	private static <N extends Number> List<ValidatorAndConstructor<Function<N, N>>> getConstructors() {
		throw new NotImplementedException();
	}
}
