package logic.function.identity;

import logic.Nameable;

import java.lang.reflect.Type;
import java.util.Set;

/**
 * @author Steven Weston
 */
public interface IdentityConstructorFromType<D extends Nameable> {

	AbstractIdentityFunction<D, ?, ?, ?> create(String parameter, Set<Type> type);
}
