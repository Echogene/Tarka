package logic.evaluable.statements.unary;

import logic.Nameable;
import logic.evaluable.Evaluable;
import logic.evaluable.EvaluableFactory;
import logic.evaluable.constants.LogicalConstantFactory;
import logic.factory.FactoryException;
import logic.function.Function;
import logic.function.factory.construction.FunctionConvertor;
import logic.function.factory.construction.ValidatorAndConstructor;
import logic.function.factory.validation.Validator;
import logic.function.factory.validation.group.validators.FunctionOrVariableValidator;
import logic.function.factory.validation.group.validators.OperatorAtom;
import logic.function.factory.validation.results.StringResult;
import logic.function.factory.validation.results.ValidationResult;

import java.util.Arrays;
import java.util.List;

import static logic.evaluable.statements.unary.UnaryConnective.UNARY_CONNECTIVE_SYMBOL_LIST;
import static logic.function.factory.validation.GroupValidatorWithNumber.Number.ONE;

/**
 * @author Steven Weston
 */
public class UnaryStatementFactory<T extends Nameable> extends EvaluableFactory<T> {

	public UnaryStatementFactory() {
		super(getConstructors());
	}

	private static <T extends Nameable> List<ValidatorAndConstructor<Function<T, Boolean>>> getConstructors() {
		Validator validatorWithoutId = new Validator();
		validatorWithoutId.addValidator(new FunctionOrVariableValidator(Evaluable.class), ONE);
		ValidatorAndConstructor<Function<T, Boolean>> constructorWithoutSymbol = new ValidatorAndConstructor<>(
				validatorWithoutId,
				new Constructor<T>(1)
		);
		Validator validatorWithId = new Validator();
		validatorWithId.addValidator(new OperatorAtom(UNARY_CONNECTIVE_SYMBOL_LIST), ONE);
		validatorWithId.addValidator(new FunctionOrVariableValidator(Evaluable.class), ONE);
		ValidatorAndConstructor<Function<T, Boolean>> constructorWithSymbol = new ValidatorAndConstructor<>(
				validatorWithId,
				new Constructor<T>(2)
		);
		return Arrays.asList(constructorWithoutSymbol, constructorWithSymbol);
	}

	private static class Constructor<T extends Nameable> implements logic.function.factory.construction.Constructor<Function<T, Boolean>> {

		private final int resultIndex;
		private final FunctionConvertor<Evaluable<T>, T> convertor;

		private Constructor(int resultIndex) {
			this.resultIndex = resultIndex;
			this.convertor = new FunctionConvertor<Evaluable<T>, T>(new LogicalConstantFactory<T>());
		}

		@Override
		public Function<T, Boolean> construct(List<ValidationResult> results) throws FactoryException {
			UnaryConnectiveFactory factory = new UnaryConnectiveFactory();
			if (resultIndex == 2) {
				UnaryConnective connective = factory.createElement(((StringResult)results.get(1)).getString());
				return new UnaryStatement<>(connective, convertor.convert(results.get(resultIndex)));
			} else {
				return new UnaryStatement<>(convertor.convert(results.get(resultIndex)));
			}
		}
	}

}
