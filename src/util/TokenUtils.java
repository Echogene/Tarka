package util;

import reading.lexing.Token;

import java.util.List;

/**
 * @author Steven Weston
 */
public class TokenUtils {

	public static String toString(List<Token> tokens) {

		StringBuilder sb = new StringBuilder();
		for (Token token : tokens) {
			sb.append(token.getValue());
		}
		return sb.toString();
	}

}
