package logic.function.evaluable.statements.quantified.restricted;

import logic.Nameable;
import logic.factory.FactoryException;
import logic.function.evaluable.Evaluable;
import logic.function.evaluable.EvaluableFactory;
import logic.function.evaluable.constants.LogicalConstantFactory;
import logic.function.evaluable.statements.quantified.standard.Quantifier;
import logic.function.evaluable.statements.quantified.standard.QuantifierFactory;
import logic.function.factory.construction.Constructor;
import logic.function.factory.construction.FunctionConvertor;
import logic.function.factory.construction.ValidatorAndConstructor;
import logic.function.factory.oldvalidation.SimpleLogicValidator;
import logic.function.factory.oldvalidation.VariableAtom;
import logic.function.factory.oldvalidation.group.validators.FunctionOrVariableValidator;
import logic.function.factory.oldvalidation.group.validators.OperatorAtom;
import logic.function.factory.oldvalidation.group.validators.QuantifierAtom;
import logic.function.factory.oldvalidation.results.StringResult;
import logic.function.factory.oldvalidation.results.ValidationResult;
import logic.function.set.SetFunction;

import java.util.Arrays;
import java.util.List;

import static logic.function.evaluable.predicate.membership.MembershipPredicate.MEMBERSHIP_SYMBOL;
import static logic.function.evaluable.statements.quantified.standard.Quantifier.QUANTIFIER_SYMBOL_LIST;
import static logic.function.factory.oldvalidation.GroupValidatorWithNumber.Number.ONE;

/**
 * @author Steven Weston
 */
public class RestrictedQuantifiedStatementFactory<T extends Nameable> extends EvaluableFactory<T, RestrictedQuantifiedStatement<T>> {

	public RestrictedQuantifiedStatementFactory() {
		super(getConstructors());
	}

	private static <T extends Nameable> List<ValidatorAndConstructor<RestrictedQuantifiedStatement<T>>> getConstructors() {
		SimpleLogicValidator validator = new SimpleLogicValidator();
		validator.addValidator(ONE, new QuantifierAtom(QUANTIFIER_SYMBOL_LIST));
		validator.addValidator(ONE, new VariableAtom());
		validator.addValidator(ONE, new OperatorAtom(Arrays.asList(MEMBERSHIP_SYMBOL)));
		validator.addValidator(ONE, new FunctionOrVariableValidator(SetFunction.class));
		validator.addValidator(ONE, new FunctionOrVariableValidator(Evaluable.class));
		return Arrays.asList(
				new ValidatorAndConstructor<>(
						validator,
						new RestrictedQuantifiedStatementConstructor<T>()
				)
		);
	}

	private static class RestrictedQuantifiedStatementConstructor<T extends Nameable> implements Constructor<RestrictedQuantifiedStatement<T>> {

		private final FunctionConvertor<Evaluable<T>, T> constructor;
		private final FunctionConvertor<SetFunction<T>, T> setConstructor;

		private RestrictedQuantifiedStatementConstructor() {
			this.constructor = new FunctionConvertor<Evaluable<T>, T>(new LogicalConstantFactory<T>());
			this.setConstructor = new FunctionConvertor<SetFunction<T>, T>(new SetIdentityFunctionFactory<T>());
		}

		@Override
		public RestrictedQuantifiedStatement<T> construct(List<ValidationResult> results) throws FactoryException {
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
