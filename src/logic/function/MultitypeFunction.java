package logic.function;

import logic.Nameable;

import java.lang.reflect.Type;

/**
 * @author Steven Weston
 */
public interface MultitypeFunction<T extends Nameable, F extends MultitypeFunction<T, F>>
		extends Function<T, Object, F> {

	@Override
	default Type getCodomain(Class<T> universeType) {
		return Object.class;
	}
}
