package logic.function.voidfunction.definition.member;

import logic.Nameable;
import logic.function.factory.FunctionFactory;
import logic.function.factory.construction.Constructor;
import logic.function.factory.construction.ValidatorAndConstructor;
import logic.function.factory.oldvalidation.SimpleLogicValidator;
import logic.function.factory.oldvalidation.VariableAtom;
import logic.function.factory.oldvalidation.group.validators.FunctionOrVariableValidator;
import logic.function.factory.oldvalidation.group.validators.OperatorAtom;
import logic.function.factory.oldvalidation.results.FunctionResult;
import logic.function.factory.oldvalidation.results.StringResult;
import logic.function.factory.oldvalidation.results.ValidationResult;
import logic.function.reflexive.ReflexiveFunction;
import logic.function.reflexive.identity.IdentityFunction;

import java.util.Arrays;
import java.util.List;

import static logic.function.factory.oldvalidation.GroupValidatorWithNumber.Number.ONE;
import static logic.function.voidfunction.definition.member.MemberDefinition.DEFINITION_SYMBOL;

/**
 * @author Steven Weston
 */
public class MemberDefinitionFactory<T extends Nameable> extends FunctionFactory<T, Void, MemberDefinition<T>> {

	public MemberDefinitionFactory() {
		super(getConstructors());
	}

	private static <T extends Nameable> List<ValidatorAndConstructor<MemberDefinition<T>>> getConstructors() {
		SimpleLogicValidator validator = new SimpleLogicValidator();
		validator.addValidator(ONE, new VariableAtom());
		validator.addValidator(ONE, new OperatorAtom(DEFINITION_SYMBOL));
		validator.addValidator(ONE, new FunctionOrVariableValidator(ReflexiveFunction.class));
		ValidatorAndConstructor<MemberDefinition<T>> constructor = new ValidatorAndConstructor<>(
				validator,
				new MemberDefinitionConstructor<T>()
		);
		return Arrays.asList(
				constructor
		);
	}

	private static class MemberDefinitionConstructor<T extends Nameable> implements Constructor<MemberDefinition<T>> {
		@Override
		public MemberDefinition<T> construct(List<ValidationResult> results) {
			StringResult variable = (StringResult) results.get(1);
			ValidationResult result = results.get(3);
			ReflexiveFunction<T> function;
			if (result instanceof StringResult) {
				function = new IdentityFunction<>(((StringResult) result).getString());
			} else {
				function = (ReflexiveFunction<T>) ((FunctionResult) result).getFunction();
			}
			return new MemberDefinition<>(variable.getString(), function);
		}
	}
}
