package logic.function.evaluable.predicate.membership;

import logic.Nameable;
import logic.function.evaluable.predicate.Predicate;
import logic.function.reflexive.ReflexiveFunction;
import logic.function.reflexive.identity.IdentityFunction;
import logic.function.set.SetFunction;
import logic.function.set.identity.SetIdentityFunction;
import logic.model.universe.Universe;
import logic.set.Set;

/**
 * @author Steven Weston
 */
public class MembershipPredicate<T extends Nameable> extends Predicate<T> {
	public static final String MEMBERSHIP_SYMBOL = "∊";
	protected ReflexiveFunction<T> memberFunction;
	protected SetFunction<T> setFunction;

	public MembershipPredicate(String memberSymbol, String setSymbol) {
		this.memberFunction = new IdentityFunction<>(memberSymbol);
		this.setFunction    = new SetIdentityFunction<>(setSymbol);
	}

	public MembershipPredicate(ReflexiveFunction<T> memberFunction, String setSymbol) {
		this.memberFunction = memberFunction;
		this.setFunction    = new SetIdentityFunction<>(setSymbol);
	}

	public MembershipPredicate(String memberSymbol, SetFunction<T> setFunction) {
		this.memberFunction = new IdentityFunction<>(memberSymbol);
		this.setFunction    = setFunction;
	}

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
	public Boolean evaluate(Universe<T> universe) throws Exception {
		Set<T> set = setFunction.evaluate(universe);
		T member = memberFunction.evaluate(universe);
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
