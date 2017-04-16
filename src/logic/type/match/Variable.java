package logic.type.match;

import java.lang.reflect.Type;
import java.util.Set;

/**
 * @author Steven Weston
 */
public class Variable extends Typed {

	private final String symbol;

	public Variable(Set<Type> allTypes, String symbol) {
		super(allTypes);
		this.symbol = symbol;
	}
}
