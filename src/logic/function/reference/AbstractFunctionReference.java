package logic.function.reference;

import logic.Nameable;
import logic.factory.FactoryException;
import logic.function.Function;
import logic.function.voidfunction.definition.function.definedfunction.AbstractDefinedFunction;
import logic.function.voidfunction.definition.function.definedfunction.AbstractDefinedFunctionFactory;
import logic.model.Model;

import java.util.List;
import java.util.Map;

/**
 * @author Steven Weston
 */
public abstract class AbstractFunctionReference<
		D extends Nameable,
		C,
		F extends AbstractFunctionReference<D, C, F, G, H>,
		G extends AbstractDefinedFunction<D, C, ? extends G, H>,
		H extends Function<D, C, ? extends H>
>
		implements Function<D, C, F> {

	AbstractDefinedFunctionFactory<D, C, G, H> factory;
	final String functionSymbol;
	final List<Function<D, ?, ?>> parameters;
	private G reference;

	AbstractFunctionReference(String functionSymbol, List<Function<D, ?, ?>> parameters, AbstractDefinedFunctionFactory<D, C, G, H> factory) {
		this(functionSymbol, parameters);
		this.factory = factory;
	}

	AbstractFunctionReference(String functionSymbol, List<Function<D, ?, ?>> parameters) {
		this.functionSymbol = functionSymbol;
		this.parameters = parameters;
	}

	@Override
	public C evaluate(Model<D, ?, ?> model) throws Exception {
		return getReference().evaluate(model);
	}

	@Override
	public void reduce(Map<String, Function<D, ?, ?>> reductions) {
		getReference().reduce(reductions);
		for (Function<D, ?, ?> parameter : parameters) {
			parameter.reduce(reductions);
		}
	}

	private G getReference() {
		if (reference == null) {
			if (factory == null) {
				throw new NullPointerException("Factory has not yet been defined");
			}
			try {
				reference = factory.construct(parameters);
			} catch (FactoryException e) {
				e.printStackTrace();
			}
		}
		return reference;
	}

	public void setFactory(AbstractDefinedFunctionFactory<D, C, G, H> factory) {
		this.factory = factory;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		sb.append(functionSymbol);
		for (Function<D, ?, ?> parameter : parameters) {
			sb.append(" ");
			sb.append(parameter);
		}
		sb.append(")");
		return sb.toString();
	}
}
