package logic.function.factory.construction;

import logic.Nameable;
import logic.function.Function;

import java.lang.reflect.Type;

/**
 * @author Steven Weston
 */
public interface IdentityConstructorFromType<D extends Nameable> {

	Function<D, ?> create(Type type);
}
