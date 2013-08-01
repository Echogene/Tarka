package logic.function.factory.validation.group;

import logic.function.Function;
import logic.function.factory.ValidationException;

import java.util.List;

/**
 * @author Steven Weston
 */
public class FunctionAtom implements AtomicValidator {
	/**
	 * This is synchronised with the closing brackets.  To be accepted, the opening bracket must have the same index in
	 * this as the closing bracket has in {@code acceptedClosingBrackets}.
	 */
	private List<String> acceptedOpeningBrackets;
	/**
	 * This is synchronised with the opening brackets.  To be accepted, the closing bracket must have the same index in
	 * this as the opening bracket has in {@code acceptedOpeningBrackets}.
	 */
	private List<String> acceptedClosingBrackets;

	private List<Class> acceptedFunctionClasses;

	public FunctionAtom(List<String> acceptedOpeningBrackets, List<Class> acceptedFunctionClasses, List<String> acceptedClosingBrackets) {
		this.acceptedOpeningBrackets = acceptedOpeningBrackets;
		this.acceptedClosingBrackets = acceptedClosingBrackets;
		this.acceptedFunctionClasses = acceptedFunctionClasses;
	}

	@Override
	public GroupValidator validate(TokenGroup group, Function<?, ?> function) throws ValidationException {
		if (!group.representsFunction()) {
			throw new ValidationException("The group—" + group.toString() + "—did not represent a function.");
		}
		String openingBracket = group.getOpeningBracket();
		int openingIndex = acceptedOpeningBrackets.indexOf(openingBracket);
		if (openingIndex == -1) {
			throw new ValidationException("The opening bracket—" + openingBracket + "—was not in " + acceptedOpeningBrackets.toString() + ".");
		}
		String closingBracket = group.getClosingBracket();
		int closingIndex = acceptedClosingBrackets.indexOf(closingBracket);
		if (closingIndex == -1) {
			throw new ValidationException("The closing bracket—" + closingBracket + "—was not in " + acceptedClosingBrackets.toString() + ".");
		}
		if (openingIndex != closingIndex) {
			throw new ValidationException("The opening−closing pair was not accepted.");
		}
		boolean classFound = false;
		for (Class clazz : acceptedFunctionClasses) {
			if (clazz.isInstance(function)) {
				classFound = true;
			}
		}
		if (!classFound) {
			throw new ValidationException("The function—" + function.toString() + "—was not in " + acceptedFunctionClasses.toString() + ".");
		}
		return this;
	}
}
