package logic.function.factory.oldvalidation;

import logic.function.Function;
import logic.function.factory.ValidationException;
import logic.function.factory.oldvalidation.group.validators.NameAtom;
import logic.function.factory.oldvalidation.results.StringResult;
import logic.function.factory.oldvalidation.results.ValidationResult;
import logic.function.factory.validation.group.TokenGroup;

import java.util.Collections;
import java.util.List;

/**
 * @author Steven Weston
 * @deprecated
 */
public class WordAtom extends NameAtom {
	private final List<String> acceptedWords;

	public WordAtom(String acceptedWord) {
		this(Collections.singletonList(acceptedWord));
	}

	public WordAtom(List<String> acceptedWords) {
		this.acceptedWords = acceptedWords;
	}

	@Override
	public ValidationResult validate(TokenGroup group, Function<?, ?> function) throws ValidationException {
		super.validate(group, function);
		if (!acceptedWords.contains(group.getValue())) {
			throw new ValidationException("The group—" + group.toString() + "—did not represent the string " + acceptedWords + ".");
		}
		return new StringResult(group.getValue());
	}
}
