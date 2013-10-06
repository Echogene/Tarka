package logic.function.evaluable.constants;

import logic.Nameable;
import logic.function.evaluable.Evaluable;
import logic.model.universe.Universe;

import java.util.Arrays;
import java.util.List;

/**
 * A class representing a logical constant such as tautology or contradiction.
 * @author Steven Weston
 */
public class LogicalConstant<T extends Nameable> implements Evaluable<T> {
	/**
	 * The symbol that represents a tautology.
	 */
	public static final String TAUTOLOGY_SYMBOL = "⊤";
	/**
	 * The symbol that represents a contradiction.
	 */
	public static final String CONTRADICTION_SYMBOL = "⊥";

	/**
	 * A string that collects together the symbols.  Generally used for regex.
	 */
	public final static String CONSTANT_SYMBOLS = TAUTOLOGY_SYMBOL + CONTRADICTION_SYMBOL;
	/**
	 * A list that collects together the symbols.
	 */
	public final static List<String> CONSTANT_SYMBOL_LIST = Arrays.asList(TAUTOLOGY_SYMBOL, CONTRADICTION_SYMBOL);

	/**
	 * The boolean value that the {@code LogicalConstant} takes when evaluated.  It will be {@code false} when it
	 * represents a contradiction and {@code true} when representing a tautology.
	 */
	protected boolean value;

	/**
	 * Construct a {@code LogicalConstant} from a boolean value.
	 * @param value The value with which to construct.  {@code true} should construct a tautology and {@code false} a
	 *              contradiction.
	 */
	public LogicalConstant(boolean value) {
		this.value = value;
	}

	@Override
	public Boolean evaluate(Universe<T> universe) throws Exception {
		return value;
	}

	@Override
	public String toString() {
		return "(" + (value ? TAUTOLOGY_SYMBOL : CONTRADICTION_SYMBOL) + ")";
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof LogicalConstant<?>)) {
			return false;
		}
		LogicalConstant<?> other = (LogicalConstant<?>) o;
		return value == other.value;
	}
}
