package logic.evaluable.statements.binary;

import logic.ConnectiveFactory;
import logic.factory.FactoryException;
import reading.lexing.Token;

import java.util.List;

import static logic.evaluable.statements.binary.BinaryConnective.*;
import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.OPERATOR;

/**
 * @author Steven Weston
 */
public class BinaryConnectiveFactory implements ConnectiveFactory<BinaryConnective> {
	@Override
	public BinaryConnective createElement(String string) throws FactoryException {
		return BinaryConnectiveFactory.create(string);
	}

	public static BinaryConnective create(String string) throws FactoryException {
		switch (string) {
			case OR_SYMBOL:
				return new BinaryConnective(BinaryConnectiveType.OR);
			case NOR_SYMBOL:
				return new BinaryConnective(BinaryConnectiveType.NOR);
			case AND_SYMBOL:
				return new BinaryConnective(BinaryConnectiveType.AND);
			case NAND_SYMBOL:
				return new BinaryConnective(BinaryConnectiveType.NAND);
			case IMPLIES_SYMBOL:
				return new BinaryConnective(BinaryConnectiveType.IMPLIES);
			case NIMPLIES_SYMBOL:
				return new BinaryConnective(BinaryConnectiveType.NIMPLIES);
			case IFF_SYMBOL:
				return new BinaryConnective(BinaryConnectiveType.IFF);
			case NIFF_SYMBOL:
				return new BinaryConnective(BinaryConnectiveType.NIFF);
			case IMPLIED_BY_SYMBOL:
				return new BinaryConnective(BinaryConnectiveType.IMPLIED_BY);
			case NIMPLIED_BY_SYMBOL:
				return new BinaryConnective(BinaryConnectiveType.NIMPLIED_BY);
		}
		throw new FactoryException("Could not create BinaryConnective");
	}

	@Override
	public BinaryConnective createElement(List<Token> tokens) throws FactoryException {
		if (matchesOneOperatorToken(tokens)) {
			return createElement(tokens.get(0).getValue());
		}
		throw new FactoryException("Could not create BinaryConnective");
	}

	private boolean matchesOneOperatorToken(List<Token> tokens) {
		return tokens != null
				&& tokens.size() == 1
				&& tokens.get(0).isOfType(OPERATOR);
	}
}
