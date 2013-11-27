package logic.factory;

import logic.Nameable;
import logic.function.Function;
import logic.function.factory.FunctionFactory;
import logic.function.identity.IdentityFunctionFactory;
import logic.model.universe.Universe;
import reading.reading.Reader;

import java.util.Arrays;

/**
 * @author Steven Weston
 */
public abstract class FactoryTest<T extends Nameable, U extends Universe<T>, F extends FunctionFactory<T, ?, ?>> {

	protected final U universe;
	protected final Reader<Function<?, ?>> reader;

	protected FactoryTest(F factory, U universe) {
		this.universe = universe;
		this.reader = new SimpleLogicReader<>(
				Arrays.<FunctionFactory<T, ?, ?>>asList(
						new IdentityFunctionFactory<>(universe.getTypeOfUniverse()),
						factory
				),
				universe
		);
	}
}
