package logic.function.reflexive.assignment;

import logic.Nameable;
import logic.factory.FactoryException;
import logic.function.factory.construction.Constructor;
import logic.function.factory.construction.FunctionConvertor;
import logic.function.factory.construction.ValidatorAndConstructor;
import logic.function.factory.validation.Validator;
import logic.function.factory.validation.VariableAtom;
import logic.function.factory.validation.WordAtom;
import logic.function.factory.validation.group.validators.FunctionOrVariableValidator;
import logic.function.factory.validation.results.StringResult;
import logic.function.factory.validation.results.ValidationResult;
import logic.function.reflexive.ReflexiveFunction;
import logic.function.reflexive.ReflexiveFunctionFactory;
import logic.function.reflexive.identity.IdentityFunctionFactory;

import java.util.Arrays;
import java.util.List;

import static logic.function.factory.validation.GroupValidatorWithNumber.Number.ONE;

/**
 * @author Steven Weston
 */
public class ReflexiveAssignmentFactory<T extends Nameable> extends ReflexiveFunctionFactory<T, ReflexiveAssignment<T>> {
	public static final String WHERE = "where";
	public static final String IS = "is";

	public ReflexiveAssignmentFactory() {
		super(getConstructors());
	}

	private static <T extends Nameable> List<ValidatorAndConstructor<ReflexiveAssignment<T>>> getConstructors() {
		Validator validator = new Validator();
		validator.addValidator(ONE, new FunctionOrVariableValidator(ReflexiveFunction.class));
		validator.addValidator(ONE, new WordAtom(WHERE));
		validator.addValidator(ONE, new VariableAtom());
		validator.addValidator(ONE, new WordAtom(IS));
		validator.addValidator(ONE, new FunctionOrVariableValidator(ReflexiveFunction.class));
		return Arrays.asList(
				new ValidatorAndConstructor<>(
						validator,
						new ReflexiveAssignmentConstructor<T>()
				)
		);
	}

	private static class ReflexiveAssignmentConstructor<T extends Nameable> implements Constructor<ReflexiveAssignment<T>> {

		private final FunctionConvertor<ReflexiveFunction<T>, T> convertor;

		private ReflexiveAssignmentConstructor() {
			this.convertor = new FunctionConvertor<ReflexiveFunction<T>, T>(new IdentityFunctionFactory<T>());
		}

		@Override
		public ReflexiveAssignment<T> construct(List<ValidationResult> results) throws FactoryException {
			ReflexiveFunction<T> evaluee = convertor.convert(results.get(1));
			String asignee = ((StringResult) results.get(3)).getString();
			ReflexiveFunction<T> assignment = convertor.convert(results.get(5));
			return new ReflexiveAssignment<>(evaluee, asignee, assignment);
		}
	}
}
