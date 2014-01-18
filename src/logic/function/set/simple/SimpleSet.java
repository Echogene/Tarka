package logic.function.set.simple;

import logic.Nameable;
import logic.function.Function;
import logic.function.reflexive.ReflexiveFunction;
import logic.function.set.SetFunction;
import logic.model.Model;
import logic.set.Set;
import logic.set.finite.StandardSet;

import java.util.HashSet;
import java.util.Map;

/**
 * @author Steven Weston
 */
public class SimpleSet<T extends Nameable> implements SetFunction<T, SimpleSet<T>> {

	private final java.util.Set<ReflexiveFunction<T, ?>> members;

	public SimpleSet(java.util.Set<ReflexiveFunction<T, ?>> members) {
		this.members = members;
	}

	public Set<T> evaluate(Model<T, ?, ?> model) throws Exception {
		StandardSet<T> output = new StandardSet<>(toString());
		for (ReflexiveFunction<T, ?> member : members) {
			output.put(member.evaluate(model));
		}
		return output;
	}

	@Override
	public void reduce(Map<String, Function<T, ?, ?>> reductions) {
		for (ReflexiveFunction<T, ?> member : members) {
			member.reduce(reductions);
		}
	}

	@Override
	public SimpleSet<T> copy() {
		java.util.Set<ReflexiveFunction<T, ?>> newMembers = new HashSet<>();
		for (ReflexiveFunction<T, ?> member : members) {
			newMembers.add(member.copy());
		}
		return new SimpleSet<>(newMembers);
	}

	@Override
	public String toString() {
		String output = "{";
		boolean firstMember = true;
		for (ReflexiveFunction<T, ?> member : members) {
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
