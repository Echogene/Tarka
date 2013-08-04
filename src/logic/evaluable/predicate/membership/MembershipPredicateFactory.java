package logic.evaluable.predicate.membership;

import logic.Nameable;
import logic.evaluable.predicate.PredicateFactory;
import logic.function.Function;
import logic.function.factory.construction.ValidatorAndConstructor;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

/**
 * @author Steven Weston
 */
public class MembershipPredicateFactory<T extends Nameable> extends PredicateFactory<T> {

	public MembershipPredicateFactory() {
		super(getConstructors());
	}

	private static <T extends Nameable> List<ValidatorAndConstructor<Function<T, Boolean>>> getConstructors() {
		throw new NotImplementedException();
	}
}
