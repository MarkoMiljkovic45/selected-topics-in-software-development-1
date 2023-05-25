package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * Used when a lexer error occurs.
 */
public class SmartScriptLexerException extends RuntimeException {

    public SmartScriptLexerException() {
    }

    public SmartScriptLexerException(String message) {
        super(message);
    }
}
