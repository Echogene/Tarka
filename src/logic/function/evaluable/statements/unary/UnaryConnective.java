package logic.function.evaluable.statements.unary;

import logic.Connective;

import java.util.Arrays;
import java.util.List;

/**
 * @author Steven Weston
 */
public class UnaryConnective extends Connective {
	public final static String NEGATION_SYMBOL = "¬";
	public final static String EMPTY_SYMBOL = "";
	
	public final static String UNARY_CONNECTIVE_SYMBOLS = NEGATION_SYMBOL + EMPTY_SYMBOL;
	public final static List<String> UNARY_CONNECTIVE_SYMBOL_LIST = Arrays.asList(NEGATION_SYMBOL, EMPTY_SYMBOL);

	public enum UnaryConnectiveType implements ConnectiveType {
		NEGATION, EMPTY
	}

	public UnaryConnective(UnaryConnectiveType type) {
		this.type = type;
	}

	public boolean apply(boolean b) {
		if (type.equals(UnaryConnectiveType.NEGATION)) {
			return !b;
		} else if (type.equals(UnaryConnectiveType.EMPTY)) {
			return b;
		} else {
			throw new RuntimeException("Unknown unary connective type");
		}
	}

	@Override
	public String toString() {
		if (type.equals(UnaryConnectiveType.NEGATION)) {
			return NEGATION_SYMBOL;
		} else if (type.equals(UnaryConnectiveType.EMPTY)) {
			return EMPTY_SYMBOL;
		} else {
			throw new RuntimeException("Unknown unary connective type");
		}
	}
}
