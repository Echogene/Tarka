package logic.function.voidfunction.definition.function;

import logic.Nameable;
import logic.function.Function;
import logic.function.evaluable.Evaluable;
import logic.function.factory.validation.checking.CheckerWithNumber;
import logic.function.factory.validation.checking.checkers.NonVoidFunctionOrVariableChecker;
import logic.function.factory.validation.checking.checkers.StringChecker;
import logic.function.reflexive.ReflexiveFunction;
import logic.function.set.SetFunction;
import logic.function.voidfunction.VoidFunction;
import logic.function.voidfunction.definition.function.definedfunction.DefinedFunctionFactoryFactory;
import logic.model.Model;
import ophelia.exceptions.NotImplementedYetException;

import java.lang.reflect.Type;
import java.util.*;

/**
 * @author Steven Weston
 */
public abstract class FunctionDefinition<
		D extends Nameable,
		C,
		F extends FunctionDefinition<D, C, F, G>,
		G extends Function<D, C, ?>
>
		implements VoidFunction<D, F> {

	public static final String DEFINITION_SYMBOL = "≝";

	final String functionSymbol;
	final LinkedHashMap<String, Set<Type>> parameters;
	final G definition;

	FunctionDefinition(String functionSymbol, LinkedHashMap<String, Set<Type>> parameters, G definition) {
		this.functionSymbol = functionSymbol;
		this.parameters = parameters;
		this.definition = definition;
	}

	@Override
	public Void evaluate(Model<D, ?, ?> model) throws Exception {
		List<CheckerWithNumber> checkers = new ArrayList<>();
		checkers.add(new StringChecker(functionSymbol));
		for (Map.Entry<String, Set<Type>> parameter : parameters.entrySet()) {
			checkers.add(
					new NonVoidFunctionOrVariableChecker(
							convertTypesToFunctionTypes(parameter.getValue(), model)
					)
			);
		}
		model.addFactory(
				DefinedFunctionFactoryFactory.create(
						functionSymbol,
						definition,
						parameters,
						checkers,
						model.getUniverse().getTypeOfUniverse()
				)
		);
		return null;
	}

	private List<Class> convertTypesToFunctionTypes(Set<Type> types, Model<D, ?, ?> model) {
		List<Class> output = new ArrayList<>();
		for (Type type : types) {
			output.add(convertTypeToFunctionType(type, model));
		}
		return output;
	}

	private Class convertTypeToFunctionType(Type type, Model<D, ?, ?> model) {
		if (model.getUniverse().getTypeOfUniverse().equals(type)) {
			return ReflexiveFunction.class;
		} else if (Boolean.class.equals(type)) {
			return Evaluable.class;
		} else if (logic.set.Set.class.equals(type)) {
			return SetFunction.class;
		} else {
			throw new NotImplementedYetException();
		}
	}

	@Override
	public void reduce(Map<String, Function<D, ?, ?>> reductions) {
		definition.reduce(reductions);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		sb.append(functionSymbol);
		for (String parameter : parameters.keySet()) {
			sb.append(" ");
			sb.append(parameter);
		}
		sb.append(" ");
		sb.append(DEFINITION_SYMBOL);
		sb.append(" ");
		sb.append(definition);
		sb.append(")");
		return sb.toString();
	}
}
