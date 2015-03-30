package logic.type.match;

import superposition.Superposition;
import util.function.ExceptionalConsumer;

import java.lang.reflect.Type;
import java.util.Set;

/**
 * @author Steven Weston
 */
public abstract class Typed {

	protected final Superposition<Type, TypeNotPossibleException> types;

	protected Typed(Set<Type> allTypes) {
		this.types = new Superposition<>(allTypes);
	}

	/**
	 * Restrict the types of this object to the given types.  It intersects its current types with the given ones.
	 * @throws NoPossibleTypeException when there are no more possible types.
	 * @param observer
	 */
	public void restrictTypes(ExceptionalConsumer<Type, TypeNotPossibleException> observer) throws NoPossibleTypeException {
		types.observe(observer);
		
		if (types.isEmpty()) {
			throw new NoPossibleTypeException(types.getCollapsion());
		}
	}
}
