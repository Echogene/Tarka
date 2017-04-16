package logic.factory;

import logic.Nameable;
import logic.function.factory.FunctionFactory;
import logic.function.identity.IdentityFunctionFactory;
import logic.model.AbstractModel;
import logic.model.universe.Universe;
import reading.evaluating.EvaluatorException;

import java.util.Arrays;

/**
 * @author Steven Weston
 */
public abstract class FactoryTest<T extends Nameable, U extends Universe<T, ?, ?>, F extends FunctionFactory<T, ?, ?>> {

	protected final U universe;
	protected final SimpleLogicReader<T> reader;
	protected final AbstractModel<T, U> model;

	protected FactoryTest(F factory, U universe) {

		this.universe = universe;
		SimpleLogicReader<T> reader;
		try {
			reader = new SimpleLogicReader<>(
					Arrays.<FunctionFactory<T, ?, ?>>asList(
							new IdentityFunctionFactory<>(universe.getTypeOfUniverse()),
							factory
					),
					universe
			);
		} catch (EvaluatorException e) {
			// Should not happen, we've included the identity function.
			reader = null;
		}
		this.reader = reader;
		this.model = new AbstractModel<T, U>(universe) {};
		model.setReader(this.reader);
	}
}
