package logic.function.factory.validation.group;

import logic.function.factory.validation.TokenValidationException;
import logic.type.map.Checkor;

/**
 * @author Steven Weston
 */
public interface TokenGroupChecker extends Checkor<TokenGroup> {

	@Override
	void check(TokenGroup tokenGroup) throws TokenValidationException;
}
