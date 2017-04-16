package logic.type.match;


import java.lang.reflect.Type;
import java.util.Map;

import static ophelia.util.StringUtils.indent;

/**
 * @author Steven Weston
 */
public class NoPossibleTypeException extends Exception {

	public NoPossibleTypeException(Map<Type, TypeNotPossibleException> collapsion) {
		super(createMessage(collapsion));
	}

	@SuppressWarnings("ThrowableResultOfMethodCallIgnored")
	private static String createMessage(Map<Type, TypeNotPossibleException> collapsion) {

		StringBuilder sb = new StringBuilder();
		for (Map.Entry<Type, TypeNotPossibleException> entry : collapsion.entrySet()) {
			Type type = entry.getKey();
			TypeNotPossibleException reasonForFailure = entry.getValue();
			sb.append(type.getTypeName());
			sb.append(" ->\n");
			sb.append(indent(reasonForFailure.getMessage()));
		}
		return sb.toString();
	}
}
