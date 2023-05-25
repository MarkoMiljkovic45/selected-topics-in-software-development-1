package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * Used to represent a token for the <i>Smart Script</i> language
 */
public class SmartScriptToken {

    /**
     * The type of the token
     */
    private final SmartScriptTokenType type;
    /**
     * The value stored in the token
     */
    private final Object value;

    /**
     * The main constructor used to set token properties.
     * @param type token type
     * @param value token value
     */
    public SmartScriptToken(SmartScriptTokenType type, Object value) {
        if (type == null) throw new IllegalArgumentException("Token type can't be null.");
        this.type = type;
        this.value = value;
    }

    /**
     * Getter method for token type
     * @return type
     */
    public SmartScriptTokenType getType() {
        return type;
    }

    /**
     * Getter method for token value
     * @return value
     */
    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        if (value != null) {
            return value.toString();
        } else {
            return "";
        }
    }
}
