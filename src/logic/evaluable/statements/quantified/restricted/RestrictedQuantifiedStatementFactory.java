package logic.evaluable.statements.quantified.restricted;

import logic.Nameable;
import logic.evaluable.Evaluable;
import logic.evaluable.EvaluableFactory;
import logic.evaluable.constants.LogicalConstantFactory;
import logic.evaluable.statements.quantified.standard.Quantifier;
import logic.evaluable.statements.quantified.standard.QuantifierFactory;
import logic.factory.FactoryException;
import logic.function.Function;
import logic.function.factory.construction.Constructor;
import logic.function.factory.construction.FunctionConvertor;
import logic.function.factory.construction.ValidatorAndConstructor;
import logic.function.factory.validation.Validator;
import logic.function.factory.validation.VariableAtom;
import logic.function.factory.validation.group.validators.FunctionOrVariableValidator;
import logic.function.factory.validation.group.validators.OperatorAtom;
import logic.function.factory.validation.group.validators.QuantifierAtom;
import logic.function.factory.validation.results.StringResult;
import logic.function.factory.validation.results.ValidationResult;
import logic.function.set.SetFunction;
import logic.function.set.identity.SetIdentityFunctionFactory;

import java.util.Arrays;
import java.util.List;

import static logic.evaluable.predicate.membership.MembershipPredicate.MEMBERSHIP_SYMBOL;
import static logic.evaluable.statements.quantified.standard.Quantifier.QUANTIFIER_SYMBOL_LIST;
import static logic.function.factory.validation.GroupValidatorWithNumber.Number.ONE;

/**
 * @author Steven Weston
 */
public class RestrictedQuantifiedStatementFactory<T extends Nameable> extends EvaluableFactory<T> {

	public RestrictedQuantifiedStatementFactory() {
		super(getConstructors());
	}

	private static <T extends Nameable> List<ValidatorAndConstructor<Function<T, Boolean>>> getConstructors() {
		Validator validator = new Validator();
		validator.addValidator(new QuantifierAtom(QUANTIFIER_SYMBOL_LIST), ONE);
		validator.addValidator(new VariableAtom(), ONE);
		validator.addValidator(new OperatorAtom(Arrays.asList(MEMBERSHIP_SYMBOL)), ONE);
		validator.addValidator(new FunctionOrVariableValidator(SetFunction.class), ONE);
		validator.addValidator(new FunctionOrVariableValidator(Evaluable.class), ONE);
		return Arrays.asList(
				new ValidatorAndConstructor<>(
						validator,
						new RestrictedQuantifiedStatementConstructor<T>()
				)
		);
	}

	private static class RestrictedQuantifiedStatementConstructor<T extends Nameable> implements Constructor<Function<T, Boolean>> {

		private final FunctionConvertor<Evaluable<T>, T> constructor;
		private final FunctionConvertor<SetFunction<T>, T> setConstructor;

		private RestrictedQuantifiedStatementConstructor() {
			this.constructor = new FunctionConvertor<Evaluable<T>, T>(new LogicalConstantFactory<T>());
			this.setConstructor = new FunctionConvertor<SetFunction<T>, T>(new SetIdentityFunctionFactory<T>());
		}

		@Override
		public Function<T, Boolean> construct(List<ValidationResult> results) throws FactoryException {
			StringResult firstResult = (StringResult) results.get(1);
			QuantifierFactory quantifierFactory = new QuantifierFactory();
			Quantifier quantifier = quantifierFactory.createElement(firstResult.getString());

			StringResult secondResult = (StringResult) results.get(2);

			SetFunction<T> setFunction = setConstructor.convert(results.get(4));

			Evaluable <T> evaluable = constructor.convert(results.get(5));

			return new RestrictedQuantifiedStatement<>(quantifier, secondResult.getString(), setFunction, evaluable);

		}
	}
}
