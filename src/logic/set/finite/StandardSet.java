package logic.set.finite;

import logic.Nameable;
import logic.set.Dictionary;
import logic.set.ModifiableSet;
import logic.set.NamedSet;
import maths.number.integer.Integer;

import java.util.HashMap;
import java.util.Iterator;

/**
 * @author Steven Weston
 */
public class StandardSet<T> extends NamedSet<T> implements ModifiableSet<T>, Dictionary<T>, FiniteSet<T> {

	protected final HashMap<String, T> hashMap;

	public StandardSet(String name) {
		super(name);
		this.hashMap = new HashMap<>();
	}

	@Override
	public Integer size() {
		return new Integer(hashMap.size());
	}

	public StandardSet(StandardSet<T> toCopy, String name) {
		super(name);
		this.hashMap = new HashMap<>(toCopy.hashMap);
	}

	@Override
	public T get(String string) {
		return hashMap.get(string);
	}

	@Override
	public void put(T thing) {
		if (thing != null) {
			if (thing instanceof Nameable) {
				hashMap.put(((Nameable) thing).getName(), thing);
			} else {
				hashMap.put(thing.toString(), thing);
			}
		} else {
			hashMap.put("null", thing);
		}
	}

	@Override
	public T put(String string, T thing) {
		return hashMap.put(string, thing);
	}

	@Override
	public boolean contains(String string) {
		return hashMap.containsKey(string);
	}

	@Override
	public boolean containsValue(T thing) {
		return hashMap.containsValue(thing);
	}

	@Override
	public T remove(String name) {
		return hashMap.remove(name);
	}

	@Override
	public Iterator<T> iterator() {
		return hashMap.values().iterator();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof StandardSet<?>)) {
			return false;
		}
		StandardSet other = (StandardSet) o;
		return getName().equals(other.getName()) && hashMap.equals(other.hashMap);
	}

	public String toFullString() {
		return getName() + " " + hashMap.toString();
	}
}
