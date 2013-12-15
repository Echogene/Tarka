package logic.function.voidfunction.definition.function;

import logic.Nameable;
import logic.function.Function;
import logic.function.factory.validation.checking.CheckerWithNumber;
import logic.function.factory.validation.checking.checkers.FunctionOrVariableChecker;
import logic.function.factory.validation.checking.checkers.StringChecker;
import logic.function.voidfunction.VoidFunction;
import logic.function.voidfunction.definition.function.definedfunction.DefinedFunctionFactoryFactory;
import logic.model.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Steven Weston
 */
abstract class FunctionDefinition<D extends Nameable, C> implements VoidFunction<D> {

	public static final String DEFINITION_SYMBOL = "≝";

	private final String functionName;
	private final List<String> parameters;
	private final Function<D, C> definition;

	FunctionDefinition(String functionName, List<String> parameters, Function<D, C> definition) {
		this.functionName = functionName;
		this.parameters = parameters;
		this.definition = definition;
	}

	@Override
	public Void evaluate(Model<D, ?, ?> model) throws Exception {
		List<CheckerWithNumber> checkers = new ArrayList<>();
		checkers.add(new StringChecker(functionName));
		for (String parameter : parameters) {
			checkers.add(new FunctionOrVariableChecker());//todo: need to know types of the parameters
		}
		model.addFactory(
				DefinedFunctionFactoryFactory.create(
						definition,
						parameters,
						checkers,
						model.getUniverse().getTypeOfUniverse()
				)
		);
		return null;
	}
}
