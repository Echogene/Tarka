package logic.function.evaluable.statements.unary;

import logic.Nameable;
import logic.factory.FactoryException;
import logic.function.evaluable.Evaluable;
import logic.function.evaluable.EvaluableFactory;
import logic.function.evaluable.constants.LogicalConstantFactory;
import logic.function.factory.construction.FunctionConvertor;
import logic.function.factory.construction.ValidatorAndConstructor;
import logic.function.factory.oldvalidation.SimpleLogicValidator;
import logic.function.factory.oldvalidation.group.validators.FunctionOrVariableValidator;
import logic.function.factory.oldvalidation.group.validators.OperatorAtom;
import logic.function.factory.oldvalidation.results.StringResult;
import logic.function.factory.oldvalidation.results.ValidationResult;

import java.util.Arrays;
import java.util.List;

import static logic.function.evaluable.statements.unary.UnaryConnective.UNARY_CONNECTIVE_SYMBOL_LIST;
import static logic.function.factory.oldvalidation.GroupValidatorWithNumber.Number.ONE;

/**
 * @author Steven Weston
 */
public class UnaryStatementFactory<T extends Nameable> extends EvaluableFactory<T, UnaryStatement<T>> {

	public UnaryStatementFactory() {
		super(getConstructors());
	}

	private static <T extends Nameable> List<ValidatorAndConstructor<UnaryStatement<T>>> getConstructors() {
		SimpleLogicValidator validatorWithoutId = new SimpleLogicValidator();
		validatorWithoutId.addValidator(ONE, new FunctionOrVariableValidator(Evaluable.class));
		ValidatorAndConstructor<UnaryStatement<T>> constructorWithoutSymbol = new ValidatorAndConstructor<>(
				validatorWithoutId,
				new Constructor<>(1)
		);
		SimpleLogicValidator validatorWithId = new SimpleLogicValidator();
		validatorWithId.addValidator(ONE, new OperatorAtom(UNARY_CONNECTIVE_SYMBOL_LIST));
		validatorWithId.addValidator(ONE, new FunctionOrVariableValidator(Evaluable.class));
		ValidatorAndConstructor<UnaryStatement<T>> constructorWithSymbol = new ValidatorAndConstructor<>(
				validatorWithId,
				new Constructor<>(2)
		);
		return Arrays.asList(constructorWithoutSymbol, constructorWithSymbol);
	}

	private static class Constructor<T extends Nameable> implements logic.function.factory.construction.Constructor<UnaryStatement<T>> {

		private final int resultIndex;
		private final FunctionConvertor<Evaluable<T>, T> convertor;

		private Constructor(int resultIndex) {
			this.resultIndex = resultIndex;
			this.convertor = new FunctionConvertor<Evaluable<T>, T>(new LogicalConstantFactory<T>());
		}

		@Override
		public UnaryStatement<T> construct(List<ValidationResult> results) throws FactoryException {
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
