package logic.set.infinite;

import logic.Nameable;
import logic.set.ImpoundSet;
import logic.set.Set;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * @author Steven Weston
 */
public class InfiniteImpoundSet<T extends Nameable> extends InfiniteSet<T> implements ImpoundSet<T> {

	public InfiniteImpoundSet(String name) {
		super(name);
	}

	@Override
	public void intersectWith(Set<T> s) {
		throw new NotImplementedException();
	}

	@Override
	public boolean containsValue(T thing) {
		throw new NotImplementedException();
	}
}
