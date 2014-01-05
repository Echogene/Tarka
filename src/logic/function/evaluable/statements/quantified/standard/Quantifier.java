package logic.function.evaluable.statements.quantified.standard;

import logic.Connective;
import logic.Nameable;
import logic.function.evaluable.Evaluable;
import logic.model.Model;
import logic.set.Set;
import logic.set.finite.FiniteSet;
import logic.set.undeterminable.NotCertainlyFiniteSetException;
import util.FunctionUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author Steven Weston
 */
public class Quantifier extends Connective {
	public final static String EXISTS_SYMBOL = "∃";
	public final static String NEXISTS_SYMBOL = "∄";
	public final static String FOR_ALL_SYMBOL = "∀";
	public final static String NFOR_ALL_SYMBOL = "¬∀";
	public final static String EXISTS_UNIQUE_SYMBOL = "∃!";
	public final static String NEXISTS_UNIQUE_SYMBOL = "∄!";

	public final static List<String> QUANTIFIER_SYMBOL_LIST = Arrays.asList(
			EXISTS_SYMBOL,
			NEXISTS_SYMBOL,
			FOR_ALL_SYMBOL,
			NFOR_ALL_SYMBOL,
			EXISTS_UNIQUE_SYMBOL,
			NEXISTS_UNIQUE_SYMBOL
	);

	public final static String QUANTIFIER_SYMBOLS = "∃∄∀¬!";

	public enum QuantifierType implements ConnectiveType {
		EXISTS, NEXISTS, FOR_ALL, NFOR_ALL, EXISTS_UNIQUE, NEXISTS_UNIQUE
	}

	Quantifier(QuantifierType type) {
		this.type = type;
	}

	public <T extends Nameable> boolean apply(String variableSymbol, Evaluable<T> evaluable, Model<T, ?, ?> model) throws Exception {
		return apply(variableSymbol, evaluable, model, model.getUniverse().getUniversalSet());
	}

	public <T extends Nameable> boolean apply(
			String variableSymbol,
			Evaluable<T> evaluable,
			Model<T, ?, ?> model,
			Set<T> set) throws Exception {

		int existCount = 0;
		Boolean output = null;
		if (!(set instanceof FiniteSet<?>)) {
			throw new NotCertainlyFiniteSetException("Cannot determine quantifier validity in a set that is not certainly finite.");
		}
		FiniteSet<T> finiteSet = (FiniteSet<T>) set;
		for (T t : finiteSet) {
			FunctionUtils.reduce(evaluable, variableSymbol, t);
			if (evaluable.evaluate(model)) {
				if (type.equals(QuantifierType.EXISTS)) {
					output = true;
					break;
				} else if (type.equals(QuantifierType.NEXISTS)) {
					output = false;
					break;
				} else if (type.equals(QuantifierType.EXISTS_UNIQUE)) {
					if (existCount++ > 0) {
						output = false;
						break;
					}
				} else if (type.equals(QuantifierType.NEXISTS_UNIQUE)) {
					if (existCount++ > 0) {
						output = true;
						break;
					}
				}
			} else {
				if (type.equals(QuantifierType.FOR_ALL)) {
					output = false;
					break;
				} else if (type.equals(QuantifierType.NFOR_ALL)) {
					output = true;
					break;
				}
			}
		}
		if (output != null) {
			return output;
		} else if (type.equals(QuantifierType.EXISTS)) {
			return false;
		} else if (type.equals(QuantifierType.NEXISTS)) {
			return true;
		} else if (type.equals(QuantifierType.FOR_ALL)) {
			return true;
		} else if (type.equals(QuantifierType.NFOR_ALL)) {
			return false;
		} else if (type.equals(QuantifierType.EXISTS_UNIQUE)) {
			return existCount == 1;
		} else if (type.equals(QuantifierType.NEXISTS_UNIQUE)) {
			return existCount != 1;
		} else {
			throw new RuntimeException("Unknown or unimplemented quantifier type");
		}
	}

	@Override
	public String toString() {
		if (type.equals(QuantifierType.EXISTS)) {
			return EXISTS_SYMBOL;
		} else if (type.equals(QuantifierType.NEXISTS)) {
			return NEXISTS_SYMBOL;
		} else if (type.equals(QuantifierType.FOR_ALL)) {
			return FOR_ALL_SYMBOL;
		} else if (type.equals(QuantifierType.NFOR_ALL)) {
			return NFOR_ALL_SYMBOL;
		} else if (type.equals(QuantifierType.EXISTS_UNIQUE)) {
			return EXISTS_UNIQUE_SYMBOL;
		} else if (type.equals(QuantifierType.NEXISTS_UNIQUE)) {
			return NEXISTS_UNIQUE_SYMBOL;
		} else {
			throw new RuntimeException("Unknown or unimplemented quantifier type");
		}
	}
}
