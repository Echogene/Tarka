package logic.function.factory.validation;

import logic.type.map.CheckorException;

/**
 * @author Steven Weston
 */
public class TokenValidationException extends CheckorException {

	public TokenValidationException(String message) {
		super(message);
	}
}
