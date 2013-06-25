package logic.evaluable.statements.quantified;

import logic.Connective;
import logic.Nameable;
import logic.evaluable.Evaluable;
import logic.model.universe.Universe;
import logic.set.Set;

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

	public final static String QUANTIFIER_SYMBOLS = "∃∄∀¬!";

	public enum QuantifierType implements ConnectiveType {
		EXISTS, NEXISTS, FOR_ALL, NFOR_ALL, EXISTS_UNIQUE, NEXISTS_UNIQUE
	}

	Quantifier(QuantifierType type) {
		this.type = type;
	}

	public <T extends Nameable> boolean apply(
			String variableSymbol,
			Evaluable<T> evaluable,
			Universe<T> universe) throws Exception {

		Set<T> valueSet = universe.getValueSet();
		universe.assignVariable(variableSymbol);
		int existCount = 0;
		Boolean output = null;
		for (T t : valueSet) {
			universe.setVariable(variableSymbol, t);
			if (evaluable.evaluate(universe)) {
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
		universe.unassignVariable(variableSymbol);
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
