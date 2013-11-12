package logic.function.factory.validation.token.group;

import logic.function.factory.validation.token.TokenValidationException;
import logic.type.map.Checkor;

/**
 * @author Steven Weston
 */
public interface TokenGroupChecker extends Checkor<TokenGroup> {

	@Override
	void check(TokenGroup tokenGroup) throws TokenValidationException;
}
