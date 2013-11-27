package logic.function.set.simple;

import logic.Nameable;
import logic.function.reflexive.ReflexiveFunction;
import logic.function.set.SetFunction;
import logic.model.universe.Universe;
import logic.set.Set;
import logic.set.finite.StandardSet;

/**
 * @author Steven Weston
 */
public class SimpleSet<T extends Nameable> implements SetFunction<T> {

	private final java.util.Set<ReflexiveFunction<T>> members;

	public SimpleSet(java.util.Set<ReflexiveFunction<T>> members) {
		this.members = members;
	}

	public Set<T> evaluate(Universe<T> universe) throws Exception {
		StandardSet<T> output = new StandardSet<>(toString());
		for (ReflexiveFunction<T> member : members) {
			output.put(member.evaluate(universe));
		}
		return output;
	}

	@Override
	public String toString() {
		String output = "{";
		boolean firstMember = true;
		for (ReflexiveFunction<T> member : members) {
			if (!firstMember) {
				output += " ";
			}
			output += member.toString();
			firstMember = false;
		}
		return output + "}";
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof SimpleSet)) {
			return false;
		}
		SimpleSet<?> other = (SimpleSet<?>) o;
		return members.equals(other.members);
	}
}
