package logic.function.factory.validation.checking.checkers;

import javafx.util.Pair;
import logic.function.Function;
import logic.function.factory.validation.function.FunctionValidationException;
import logic.function.voidfunction.VoidFunction;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;

/**
 * @author Steven Weston
 */
public class FunctionNonVoidChecker extends FunctionfulChecker {

	public FunctionNonVoidChecker(List<Pair<String, String>> acceptedBracketPairs, List<Class> acceptedFunctionClasses) {
		super(acceptedBracketPairs, acceptedFunctionClasses);
	}

	public FunctionNonVoidChecker() {
		//noinspection unchecked
		this(Collections.EMPTY_LIST, Collections.EMPTY_LIST);
	}

	@Override
	public void check(Function<?, ?> function) throws FunctionValidationException {
		super.check(function);
		if (function instanceof VoidFunction) {
			throw new FunctionValidationException(
					MessageFormat.format(
							"{0} ({1}) was void.",
							function.toString(),
							function.getClass().getSimpleName()
					)
			);
		}
	}
}
