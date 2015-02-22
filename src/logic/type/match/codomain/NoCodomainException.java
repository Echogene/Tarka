package logic.type.match.codomain;

import org.jetbrains.annotations.NotNull;
import util.CollectionUtils;

import java.text.MessageFormat;

/**
 * @author Steven Weston
 */
public class NoCodomainException extends Exception {

	public NoCodomainException(@NotNull CodomainTypedMatch match) {

		super(MessageFormat.format(
				"Function {0} had no possible codomain",
				CollectionUtils.toString(match.getOriginalNodes())
		));
	}
}
