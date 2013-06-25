package logic.set;

import logic.Nameable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * A {@code NamedSet} is a {@code HashMap} that has a name.
 * @author Steven Weston
 */
public class NamedSet<T extends Nameable> extends AbstractSet<T> {
	protected HashMap<String, T> hashMap;
	protected String name;

	public NamedSet(String name) {
		this.name    = name;
		this.hashMap = new HashMap<>();
	}

	public NamedSet(NamedSet<T> toCopy, String name) {
		this.name    = name;
		this.hashMap = new HashMap<>(toCopy.hashMap);
	}

	@Override
	public T get(String string) {
		return hashMap.get(string);
	}

	@Override
	public void put(T thing) {
		if (thing != null) {
			hashMap.put(thing.getName(), thing);
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
	public Collection<T> values() {
		return hashMap.values();
	}

	@Override
	public void uniteWith(Set<T> s) {
		if (!(s instanceof NamedSet<?>)) {
			throw new IllegalArgumentException("Can only unite with NamedSet");
		}
		NamedSet<T> other = (NamedSet<T>) s;
		hashMap.putAll(other.hashMap);
	}

	@Override
	public Set<T> copy(String name) {
		return new NamedSet<>(this, name);
	}

	@Override
	public int size() {
		return hashMap.size();
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public T remove(String name) {
		return hashMap.remove(name);
	}

	@Override
	public String toString() {
		return name;
	}

	public String getName() {
		return name;
	}

	@Override
	public Iterator<T> iterator() {
		return hashMap.values().iterator();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof NamedSet<?>)) {
			return false;
		}
		NamedSet other = (NamedSet) o;
		return getName().equals(other.getName()) && hashMap.equals(other.hashMap);
	}

	public String toFullString() {
		return getName() + " " + hashMap.toString();
	}
}
