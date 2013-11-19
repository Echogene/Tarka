package logic.function.identity;

import logic.Nameable;

import java.lang.reflect.Type;

/**
 * @author Steven Weston
 */
public interface IdentityConstructorFromType<D extends Nameable> {

	IdentityFunction<D, ?> create(String parameter, Type type);
}
