package logic.function.evaluable.predicate.membership;

import logic.Nameable;
import logic.function.evaluable.predicate.Predicate;
import logic.function.reflexive.ReflexiveFunction;
import logic.function.set.SetFunction;
import logic.model.Model;
import logic.set.Set;

/**
 * @author Steven Weston
 */
public class MembershipPredicate<T extends Nameable> extends Predicate<T> {

	public static final String MEMBERSHIP_SYMBOL = "∊";

	private final ReflexiveFunction<T> memberFunction;
	private final SetFunction<T> setFunction;

	public MembershipPredicate(ReflexiveFunction<T> memberFunction, SetFunction<T> setFunction) {
		this.memberFunction = memberFunction;
		this.setFunction    = setFunction;
	}

	public ReflexiveFunction<T> getMemberFunction() {
		return memberFunction;
	}

	public SetFunction getSetFunction() {
		return setFunction;
	}

	@Override
	public Boolean evaluate(Model<T, ?, ?> model) throws Exception {
		Set<T> set = setFunction.evaluate(model);
		T member = memberFunction.evaluate(model);
		return set.containsValue(member);
	}

	@Override
	public String toString() {
		return "(" + getMemberFunction().toString() + " " + MEMBERSHIP_SYMBOL + " " + getSetFunction().toString() + ")";
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof MembershipPredicate<?>)) {
			return false;
		}
		MembershipPredicate<?> other = (MembershipPredicate<?>) o;
		return getMemberFunction().equals(other.getMemberFunction()) && getSetFunction().equals(other.getSetFunction());
	}
}
