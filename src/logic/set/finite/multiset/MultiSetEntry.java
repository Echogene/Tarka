package logic.set.finite.multiset;

import logic.Nameable;

/**
 * @author Steven Weston
 */
public class MultiSetEntry<T extends Nameable> implements Nameable {

	private final T entry;

	private int number;

	public MultiSetEntry(T entry) {
		this.entry = entry;
		this.number = 1;
	}

	public MultiSetEntry(T entry, int number) {
		this.entry = entry;
		this.number = number;
	}

	public T getEntry() {
		return entry;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	@Override
	public String getName() {
		return getEntry().getName();
	}

	public int getNumber() {
		return number;
	}

	public void addAnother() {
		setNumber(getNumber() + 1);
	}

	public void removeOne() {
		setNumber(Math.max(0, getNumber() - 1));
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof MultiSetEntry<?>)) {
			return false;
		}
		MultiSetEntry<?> other = (MultiSetEntry<?>) o;
		return this.getEntry().equals(other.getEntry())
				&& this.getNumber() == other.getNumber();
	}

	@Override
	public String toString() {
		return number + " of " + getName();
	}
}
