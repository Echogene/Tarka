package logic.function.factory.validation.group.checkers;

import logic.function.factory.validation.TokenValidationException;
import logic.function.factory.validation.group.TokenGroup;
import logic.function.factory.validation.group.TokenGroupChecker;
import logic.function.factory.validation.group.TokenGroupCheckerWithNumber;
import logic.type.map.MapToErrors;
import util.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author Steven Weston
 */
public class DisjunctiveChecker extends TokenGroupCheckerWithNumber {

	private final List<TokenGroupChecker> subCheckers;

	public DisjunctiveChecker(TokenGroupChecker... subCheckers) {
		super(Number.ONE);
		this.subCheckers = Arrays.asList(subCheckers);
	}

	@Override
	public void check(TokenGroup tokenGroup) throws TokenValidationException {
		MapToErrors<TokenGroupChecker> map = new MapToErrors<>(
				subCheckers,
				checker -> checker.check(tokenGroup)
		);
		if (map.allFailed()) {
			throw new TokenValidationException(
					"The group "
							+ tokenGroup.toString()
							+ " failed validation because:"
							+ StringUtils.addCharacterAfterEveryNewline(map.concatenateErrorMessages(), '\t')
			);
		}
	}

	@Override
	public String toString() {
		String output = getClass().getSimpleName();
		boolean first = true;
		for (TokenGroupChecker subChecker : subCheckers) {
			if (!first) {
				output += " ∨";
			}
			output += " " + subChecker.toString();
			first = false;
		}
		return output;
	}
}
