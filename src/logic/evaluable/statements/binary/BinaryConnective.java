package logic.evaluable.statements.binary;

import logic.Connective;

import java.util.Arrays;
import java.util.List;

/**
 * @author Steven Weston
 */
public class BinaryConnective extends Connective {
	public final static String OR_SYMBOL = "∨";
	public final static String NOR_SYMBOL = "⊽";
	public final static String AND_SYMBOL = "∧";
	public final static String NAND_SYMBOL = "⊼";
	public final static String IMPLIES_SYMBOL = "→";
	public final static String NIMPLIES_SYMBOL = "↛";
	public final static String IFF_SYMBOL = "↔";
	public final static String NIFF_SYMBOL = "↮";
	public final static String IMPLIED_BY_SYMBOL = "←";
	public final static String NIMPLIED_BY_SYMBOL = "↚";
	
	public final static String BINARY_CONNECTIVE_SYMBOLS = OR_SYMBOL + NOR_SYMBOL + AND_SYMBOL + NAND_SYMBOL
			+ IMPLIES_SYMBOL + NIMPLIES_SYMBOL + IFF_SYMBOL + NIFF_SYMBOL + IMPLIED_BY_SYMBOL + NIMPLIED_BY_SYMBOL;
	public final static List<String> BINARY_CONNECTIVE_SYMBOL_LIST = Arrays.asList(OR_SYMBOL, NOR_SYMBOL, AND_SYMBOL,
			NAND_SYMBOL, IMPLIES_SYMBOL, NIMPLIES_SYMBOL, IFF_SYMBOL, NIFF_SYMBOL, IMPLIED_BY_SYMBOL,
			NIMPLIED_BY_SYMBOL);

	enum BinaryConnectiveType implements ConnectiveType {
		OR, NOR, AND, NAND, IMPLIES, NIMPLIES, IFF, NIFF, IMPLIED_BY, NIMPLIED_BY
	}

	BinaryConnective(BinaryConnectiveType type) {
		this.type = type;
	}

	public boolean apply(boolean a, boolean b) {
		if (type.equals(BinaryConnectiveType.OR)) {
			return a || b;
		} else if (type.equals(BinaryConnectiveType.NOR)) {
			return !(a || b);
		} else if (type.equals(BinaryConnectiveType.AND)) {
			return a && b;
		} else if (type.equals(BinaryConnectiveType.NAND)) {
			return  !(a && b);
		} else if (type.equals(BinaryConnectiveType.IMPLIES)) {
			return (!a) || b;
		} else if (type.equals(BinaryConnectiveType.NIMPLIES)) {
			return !((!a) || b);
		} else if (type.equals(BinaryConnectiveType.IFF)) {
			return a == b;
		} else if (type.equals(BinaryConnectiveType.NIFF)) {
			return a != b;
		} else if (type.equals(BinaryConnectiveType.IMPLIED_BY)) {
			return a || (!b);
		} else if (type.equals(BinaryConnectiveType.NIMPLIED_BY)) {
			return !(a || (!b));
		} else {
			throw new RuntimeException("Unknown binary connective type");
		}
	}

	@Override
	public String toString() {
		if (BinaryConnectiveType.OR.equals(type)) {
			return OR_SYMBOL;
		} else if (BinaryConnectiveType.NOR.equals(type)) {
			return NOR_SYMBOL;
		} else if (BinaryConnectiveType.AND.equals(type)) {
			return AND_SYMBOL;
		} else if (BinaryConnectiveType.NAND.equals(type)) {
			return NAND_SYMBOL;
		} else if (BinaryConnectiveType.IMPLIES.equals(type)) {
			return IMPLIES_SYMBOL;
		} else if (BinaryConnectiveType.NIMPLIES.equals(type)) {
			return NIMPLIES_SYMBOL;
		} else if (BinaryConnectiveType.IFF.equals(type)) {
			return IFF_SYMBOL;
		} else if (BinaryConnectiveType.NIFF.equals(type)) {
			return NIFF_SYMBOL;
		} else if (BinaryConnectiveType.IMPLIED_BY.equals(type)) {
			return IMPLIED_BY_SYMBOL;
		} else if (BinaryConnectiveType.NIMPLIED_BY.equals(type)) {
			return NIMPLIED_BY_SYMBOL;
		} else {
			throw new RuntimeException("Unknown binary connective type");
		}
	}
}
