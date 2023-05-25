package hr.fer.oprpp1.custom.scripting.parser;

/**
 * Used when an error in parsing occurs
 */
public class SmartScriptParserException extends RuntimeException {

    public SmartScriptParserException() {
    }

    public SmartScriptParserException(String message) {
        super(message);
    }
}
