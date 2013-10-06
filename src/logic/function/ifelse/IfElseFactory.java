package logic.function.ifelse;

import logic.Nameable;
import logic.factory.FactoryException;
import logic.function.Function;
import logic.function.evaluable.Evaluable;
import logic.function.evaluable.constants.LogicalConstantFactory;
import logic.function.factory.FunctionFactory;
import logic.function.factory.construction.Constructor;
import logic.function.factory.construction.FunctionConvertor;
import logic.function.factory.construction.ValidatorAndConstructor;
import logic.function.factory.validation.Validator;
import logic.function.factory.validation.WordAtom;
import logic.function.factory.validation.group.validators.FunctionOrVariableValidator;
import logic.function.factory.validation.results.FunctionResult;
import logic.function.factory.validation.results.StringResult;
import logic.function.factory.validation.results.ValidationResult;
import logic.function.reflexive.ReflexiveFunction;
import logic.function.reflexive.identity.IdentityFunctionFactory;
import logic.function.set.SetFunction;
import logic.function.voidfunction.VoidFunction;

import java.util.Arrays;
import java.util.List;

import static logic.function.factory.validation.GroupValidatorWithNumber.Number.ONE;

/**
 * @author Steven Weston
 */
public class IfElseFactory<T extends Nameable> extends FunctionFactory<T, Object, IfElse<T, ?>> {

	public static final String IF = "if";
	public static final String OTHERWISE = "otherwise";

	public IfElseFactory() {
		super(getValidatorAndConstructors());
	}

	private static <T extends Nameable> List<ValidatorAndConstructor<IfElse<T, ?>>> getValidatorAndConstructors() {
		Validator validator = new Validator();
		validator.addValidator(ONE, new FunctionOrVariableValidator(Function.class));
		validator.addValidator(ONE, new WordAtom(IF));
		validator.addValidator(ONE, new FunctionOrVariableValidator(Evaluable.class));
		validator.addValidator(ONE, new WordAtom(OTHERWISE));
		validator.addValidator(ONE, new FunctionOrVariableValidator(Function.class));
		//todo: add validator that validates that the functions are the same

		Constructor<IfElse<T, ?>> constructor = new IfElseConstructor<>();
		return Arrays.asList(new ValidatorAndConstructor<>(validator, constructor));
	}

	private static class IfElseConstructor<T extends Nameable> implements Constructor<IfElse<T, ?>> {

		private final FunctionConvertor<Evaluable<T>, T> evaluableConvertor;
		private final FunctionConvertor<ReflexiveFunction<T>, T> reflexiveFunctionConvertor;

		private IfElseConstructor() {
			evaluableConvertor = new FunctionConvertor<Evaluable<T>, T>(new LogicalConstantFactory<T>());
			reflexiveFunctionConvertor = new FunctionConvertor<ReflexiveFunction<T>, T>(new IdentityFunctionFactory<T>());
		}

		@Override
		public IfElse<T, ?> construct(List<ValidationResult> results) throws FactoryException {
			ValidationResult validationResult1 = results.get(1);
			Function<T, ?> ifTrue;
			if (validationResult1 instanceof StringResult) {
				// todo: this needs to be changed when typing is introduced
				ifTrue = reflexiveFunctionConvertor.convert(validationResult1);
			} else {
				ifTrue = (Function<T, Object>) ((FunctionResult) validationResult1).getFunction();
			}

			Evaluable<T> condition = evaluableConvertor.convert(results.get(3));

			ValidationResult validationResult2 = results.get(5);
			Function<T, ?> ifFalse;
			if (validationResult1 instanceof StringResult) {
				// todo: this needs to be changed when typing is introduced
				ifFalse = reflexiveFunctionConvertor.convert(validationResult2);
			} else {
				ifFalse = (Function<T, Object>) ((FunctionResult) validationResult2).getFunction();
			}

			if (ifTrue instanceof ReflexiveFunction) {
				return new ReflexiveIfElse<>(condition, (ReflexiveFunction<T>) ifTrue, (ReflexiveFunction<T>) ifFalse);
			} else if (ifTrue instanceof SetFunction) {
				return new SetIfElse<>(condition, (SetFunction<T>) ifTrue, (SetFunction<T>) ifFalse);
			} else if (ifTrue instanceof Evaluable) {
				return new EvaluableIfElse<>(condition, (Evaluable<T>) ifTrue, (Evaluable<T>) ifFalse);
			} else if (ifTrue instanceof VoidFunction) {
				return new VoidIfElse<>(condition, (VoidFunction<T>) ifTrue, (VoidFunction<T>) ifFalse);
			} else {
				throw new FactoryException("Unknown function type");
			}
		}
	}
}
