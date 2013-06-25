package reading.lexing;

import reading.reading.ReadingException;

/**
 * @author Steven Weston
 */
public class LexerException extends ReadingException {
    public LexerException() {
        super();
    }

    public LexerException(String message) {
        super(message);
    }
}
