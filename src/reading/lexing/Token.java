package reading.lexing;

/**
 * @author Steven Weston
 */
public class Token {
	protected TokenType type;
	protected String value;

	public Token(TokenType type, String value) {
		this.type = type;
		this.value = value;
	}

	public TokenType getType() {
		return type;
	}

	public String getValue() {
		return value;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Token)) {
			return false;
		}
		Token t = (Token) o;
		return getType() == t.getType() && getValue().equals(t.getValue());
	}

	@Override
	public String toString() {
		return "[" + getType() + ", " + getValue() + "]";
	}

	public boolean isOfType(TokenType type) {
		return this.getType().equals(type);
	}

	public static final String valueOf(Token token) {
		if (token == null) {
			return null;
		} else {
			return token.getValue();
		}
	}

	public static interface TokenType {}
}
