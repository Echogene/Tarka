package logic.evaluable.statements.quantified.standard;

import logic.Nameable;
import logic.evaluable.Evaluable;
import logic.evaluable.EvaluableFactory;
import logic.evaluable.constants.LogicalConstantFactory;
import logic.factory.FactoryException;
import logic.function.Function;
import logic.function.factory.construction.Constructor;
import logic.function.factory.construction.FunctionConvertor;
import logic.function.factory.construction.ValidatorAndConstructor;
import logic.function.factory.validation.Validator;
import logic.function.factory.validation.VariableAtom;
import logic.function.factory.validation.group.validators.FunctionOrVariableValidator;
import logic.function.factory.validation.group.validators.QuantifierAtom;
import logic.function.factory.validation.results.StringResult;
import logic.function.factory.validation.results.ValidationResult;

import java.util.Arrays;
import java.util.List;

import static logic.evaluable.statements.quantified.standard.Quantifier.QUANTIFIER_SYMBOL_LIST;
import static logic.function.factory.validation.GroupValidatorWithNumber.Number.ONE;

/**
 * @author Steven Weston
 */
public class QuantifiedStatementFactory<T extends Nameable> extends EvaluableFactory<T> {

	public QuantifiedStatementFactory() {
		super(getConstructors());
	}

	private static <T extends Nameable> List<ValidatorAndConstructor<Function<T, Boolean>>> getConstructors() {
		Validator validator = new Validator();
		validator.addValidator(new QuantifierAtom(QUANTIFIER_SYMBOL_LIST), ONE);
		validator.addValidator(new VariableAtom(), ONE);
		validator.addValidator(new FunctionOrVariableValidator(Evaluable.class), ONE);

		return Arrays.asList(
				new ValidatorAndConstructor<>(
						validator,
						new QuantifiedStatementConstructor<T>()
				)
		);
	}

	private static class QuantifiedStatementConstructor<T extends Nameable> implements Constructor<Function<T, Boolean>> {

		private final FunctionConvertor<Evaluable<T>, T> constructor;

		private QuantifiedStatementConstructor() {
			this.constructor = new FunctionConvertor<Evaluable<T>, T>(new LogicalConstantFactory<T>());
		}

		@Override
		public Function<T, Boolean> construct(List<ValidationResult> results) throws FactoryException {
			StringResult firstResult = (StringResult) results.get(1);
			QuantifierFactory quantifierFactory = new QuantifierFactory();
			Quantifier quantifier = quantifierFactory.createElement(firstResult.getString());

			StringResult secondResult = (StringResult) results.get(2);

			Evaluable<T> evaluable = constructor.convert(results.get(3));

			return new QuantifiedStatement<T>(quantifier, secondResult.getString(), evaluable);
		}
	}
}
