package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * An enumeration of all the possible token types of the <i>Smart Script</i> language
 */
public enum SmartScriptTokenType {
    /** Used to represent that there are no more tokens **/
    EOF,
    /** Represents a piece of text **/
    TEXT,
    /** Represents a keyword in the <i>Smart Script</i> language**/
    KEYWORD,
    /** Represents a variable identifier **/
    VARIABLE,
    /** Represents a function identifier **/
    FUNCTION,
    /** Represents an integer constant **/
    INTEGER_CONSTANT,
    /** Represent a double constant **/
    DOUBLE_CONSTANT,
    /** Represent a string constant **/
    STRING,
    /** Represent an operator (+, -, *, /, ^) **/
    OPERATOR,
    /** Represents the opening tag {$ **/
    OPENED_TAG,
    /** Represents the closing tag $} **/
    CLOSED_TAG
}
