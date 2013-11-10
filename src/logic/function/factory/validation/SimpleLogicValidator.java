package logic.function.factory.validation;

import logic.function.Function;
import logic.function.factory.ValidationException;
import logic.function.factory.validation.group.GroupValidator;
import logic.function.factory.validation.group.TokenGroup;
import logic.function.factory.validation.results.FunctionResult;
import logic.function.factory.validation.results.StringResult;
import logic.function.factory.validation.results.ValidationResult;
import reading.lexing.Token;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.util.Arrays.asList;
import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.CLOSE_BRACKET;
import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.OPEN_BRACKET;
import static logic.function.factory.validation.GroupValidatorWithNumber.Number;
import static logic.function.factory.validation.GroupValidatorWithNumber.Number.MANY;
import static logic.function.factory.validation.GroupValidatorWithNumber.Number.ONE;
import static util.CollectionUtils.safeNext;
import static util.CollectionUtils.stripFirstAndLast;

/**
 * @author Steven Weston
 */
public class SimpleLogicValidator {
	private final List<String> acceptedOpeningBrackets;
	private final List<String> acceptedClosingBrackets;
	private List<TokenGroup> tokenGroups;

	private List<GroupValidatorWithNumber> groupValidators;

	public SimpleLogicValidator(List<String> acceptedOpeningBrackets, List<String> acceptedClosingBrackets) {
		this.acceptedOpeningBrackets = acceptedOpeningBrackets;
		this.acceptedClosingBrackets = acceptedClosingBrackets;
		this.groupValidators = new ArrayList<>();
	}

	public SimpleLogicValidator() {
		this(asList("("), asList(")"));
	}

	public void addValidator(Number number, GroupValidator validator) {
		groupValidators.add(new GroupValidatorWithNumber(validator, number));
	}

	public List<ValidationResult> validate(List<Token> tokens, List<Function<?, ?>> functions) throws ValidationException {
		List<ValidationResult> output = new ArrayList<>();
		validateBrackets(tokens);
		output.add(new StringResult(tokens.get(0).getValue()));
		tokenGroups = groupTokens(stripFirstAndLast(tokens));
		Iterator<GroupValidatorWithNumber> validatorIterator = groupValidators.iterator();
		GroupValidatorWithNumber currentValidator = safeNext(validatorIterator);
		Iterator<Function<?, ?>> functionIterator = functions == null ? null : functions.iterator();
		Function<?, ?> currentFunction = safeNext(functionIterator);
		for (TokenGroup group : tokenGroups) {
			boolean validated = false;
			boolean getNextFunctionAtEndOfLoop = false;
			while (!validated) {
				if (currentValidator == null) {
					throw new ValidationException("There were too many tokens.");
				}
				// Keep on finding validators until one passes or the validator is mandatory.
				try {
					if (currentValidator.requiresFunction()) {
						ValidationResult result = currentValidator.validate(group, currentFunction);
						output.add(result);
						if (result instanceof FunctionResult) {
							// We have used the current function, so we should get the next one at the end of the outer
							// for loop.
							getNextFunctionAtEndOfLoop = true;
						}
					} else {
						output.add(currentValidator.validate(group, null));
					}
					validated = true;
				} catch (ValidationException e) {
					if (MANY.equals(currentValidator.getNumber())) {
						// MANY includes zero, so we can just assume we should use the next validator.
						currentValidator = safeNext(validatorIterator);
					} else {
						// There must be exactly one token group that matches the validator.  So if this group doesn't
						// match, an error has occured.
						throw new ValidationException(e.getMessage());
					}
				}
			}
			if (ONE.equals(currentValidator.getNumber())) {
				currentValidator = safeNext(validatorIterator);
			}
			if (getNextFunctionAtEndOfLoop) {
				currentFunction = safeNext(functionIterator);
			}
		}
		if (validatorIterator.hasNext()) {
			throw new ValidationException("There were not enough tokens");
		}
		output.add(new StringResult(tokens.get(tokens.size() - 1).getValue()));
		return output;
	}

	public void validateBrackets(List<Token> tokens) throws ValidationException {
		if (tokens.size() < 2) {
			throw new ValidationException("There were fewer than two tokens.");
		}
		Token firstToken = tokens.get(0);
		Token lastToken = tokens.get(tokens.size() - 1);
		if (!firstToken.isOfType(OPEN_BRACKET)) {
			throw new ValidationException("The first token—" + firstToken.toString() + "—was not an opening bracket.");
		}
		if (!lastToken.isOfType(CLOSE_BRACKET)) {
			throw new ValidationException("The last token—" + lastToken.toString() + "—was not an opening bracket.");
		}
		List<Integer> openingIndices = new ArrayList<>();
		if (acceptedOpeningBrackets != null) {
			int index = 0;
			for (String openingBracket : acceptedOpeningBrackets) {
				if (openingBracket.equals(firstToken.getValue())) {
					openingIndices.add(index);
				}
				index++;
			}
			if (openingIndices.isEmpty()) {
				throw new ValidationException("The first token—" + firstToken.toString() + "—was not in " + acceptedOpeningBrackets.toString() + ".");
			}
		}
		List<Integer> closingIndices = new ArrayList<>();
		if (acceptedClosingBrackets !=  null) {
			int index = 0;
			for (String closing : acceptedClosingBrackets) {
				if (closing.equals(lastToken.getValue())) {
					closingIndices.add(index);
				}
				index++;
			}
			if (closingIndices.isEmpty()) {
				throw new ValidationException("The first token—" + lastToken.toString() + "—was not in " + acceptedClosingBrackets.toString() + ".");
			}
		}
		boolean intersection = false;
		for (Integer index : openingIndices) {
			if (closingIndices.contains(index)) {
				intersection = true;
			}
		}
		if (!intersection) {
			throw new ValidationException("The opening−closing pair was not accepted.");
		}
	}

	public List<TokenGroup> groupTokens(List<Token> tokens) throws ValidationException {
		tokenGroups = new ArrayList<>();
		Token lastToken = null;
		for (Token token : tokens) {
			if (token.isOfType(CLOSE_BRACKET)) {
				if (lastToken == null || !lastToken.isOfType(OPEN_BRACKET)) {
					throw new ValidationException("Closing brackets must be immediately preceded by opening brackets.");
				}
				tokenGroups.add(new TokenGroup(lastToken, token));
			} else if (!token.isOfType(OPEN_BRACKET)) {
				tokenGroups.add(new TokenGroup(token));
			}
			lastToken = token;
		}
		return tokenGroups;
	}
}
