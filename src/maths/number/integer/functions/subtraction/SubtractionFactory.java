package maths.number.integer.functions.subtraction;

import logic.function.Function;
import logic.function.factory.construction.ValidatorAndConstructor;
import logic.function.reflexive.ReflexiveFunctionFactory;
import maths.number.Number;
import maths.number.Subtractor;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

/**
 * @author Steven Weston
 */
public class SubtractionFactory<N extends Number> extends ReflexiveFunctionFactory<N> {
	private final Subtractor<N> subtractor;

	public SubtractionFactory(Subtractor<N> subtractor) {
		super(getConstructors());
		this.subtractor = subtractor;
	}

	private static <N extends Number> List<ValidatorAndConstructor<Function<N, N>>> getConstructors() {
		throw new NotImplementedException();
	}
}
