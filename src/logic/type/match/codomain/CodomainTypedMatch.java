package logic.type.match.codomain;

import logic.type.match.arguments.ArgumentedMatch;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * A match for a function that specifies the possible types for its codomain.
 * @author Steven Weston
 */
public class CodomainTypedMatch extends ArgumentedMatch {

	protected final Set<Type> codomainTypes;

	CodomainTypedMatch(
			ArgumentedMatch match
	) {
		super(match.getOriginalNodes(), match.getArguments());
		this.codomainTypes = new HashSet<>();
	}

	public void restrictCodomain(Set<Type> types) throws NoCodomainException {
		if (codomainTypes.isEmpty()) {
			codomainTypes.addAll(types);
		} else {
			codomainTypes.retainAll(types);
			if (codomainTypes.isEmpty()) {
				throw new NoCodomainException(this);
			}
		}
	}

	public Set<Type> getCodomainTypes() {
		return Collections.unmodifiableSet(codomainTypes);
	}
}
