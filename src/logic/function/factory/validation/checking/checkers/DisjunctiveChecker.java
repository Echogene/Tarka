package logic.function.factory.validation.checking.checkers;

import logic.function.Function;
import logic.function.factory.validation.checking.Checker;
import logic.function.factory.validation.checking.CheckerWithNumber;
import logic.function.factory.validation.checking.TokenGroupChecker;
import logic.function.factory.validation.function.FunctionValidationException;
import logic.function.factory.validation.token.TokenValidationException;
import logic.function.factory.validation.token.group.TokenGroup;
import logic.oldtype.map.MapToErrors;
import ophelia.util.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author Steven Weston
 */
public class DisjunctiveChecker extends CheckerWithNumber {

	private final List<Checker> subCheckers;

	public DisjunctiveChecker(Checker... subCheckers) {
		super(Number.ONE);
		this.subCheckers = Arrays.asList(subCheckers);
	}

	@Override
	public void check(TokenGroup tokenGroup) throws TokenValidationException {
		MapToErrors<Checker> map = new MapToErrors<>(
				subCheckers,
				checker -> checker.check(tokenGroup)
		);
		if (map.allFailed()) {
			throw new TokenValidationException(
					"The group "
							+ tokenGroup.toString()
							+ " failed validation because:\n"
							+ StringUtils.addCharacterAfterEveryNewline(map.concatenateErrorMessages(), '\t')
			);
		}
	}

	@Override
	public void check(Function<?, ?, ?> function) throws FunctionValidationException {
		MapToErrors<Checker> map = new MapToErrors<>(
				subCheckers,
				checker -> checker.check(function)
		);
		if (map.allFailed()) {
			throw new FunctionValidationException(
					"The function "
					+ function.toString()
					+ " failed validation because:\n"
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

	public List<Checker> getSubCheckers() {
		return subCheckers;
	}
}
