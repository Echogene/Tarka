package logic.function.factory.validation.checking;

import logic.function.factory.validation.token.TokenValidationException;
import logic.function.factory.validation.token.group.TokenGroup;
import logic.oldtype.map.Checkor;

/**
 * @author Steven Weston
 */
public interface TokenGroupChecker extends Checkor<TokenGroup> {

	@Override
	void check(TokenGroup tokenGroup) throws TokenValidationException;
}
