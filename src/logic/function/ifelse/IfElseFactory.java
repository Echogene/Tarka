package logic.function.ifelse;

import logic.Nameable;
import logic.function.factory.FunctionFactory;
import logic.function.factory.construction.ValidatorAndConstructor;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

/**
 * @author Steven Weston
 */
public class IfElseFactory<T extends Nameable> extends FunctionFactory<T, Object, IfElse<T, Object>> {

	protected IfElseFactory() {
		super(getValidatorAndConstructors());
	}

	private static <T extends Nameable> List<ValidatorAndConstructor<IfElse<T, Object>>> getValidatorAndConstructors() {
		throw new NotImplementedException();
	}
}
