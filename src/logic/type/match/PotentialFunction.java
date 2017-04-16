package logic.type.match;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

/**
 * @author Steven Weston
 */
public class PotentialFunction extends Typed {

	private final List<Typed> arguments;

	public PotentialFunction(Set<Type> allTypes, List<Typed> arguments) {
		super(allTypes);
		this.arguments = arguments;
	}
}
