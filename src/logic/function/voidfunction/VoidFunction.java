package logic.function.voidfunction;

import logic.Nameable;
import logic.function.Function;

import java.lang.reflect.Type;

/**
 * @author Steven Weston
 */
public interface VoidFunction<T extends Nameable, F extends VoidFunction<T, F>> extends Function<T, Void, F> {

	@Override
	default Type getCodomain(Class<T> universeType) {
		return Void.class;
	}
}
