package hr.fer.oprpp1.custom.scripting.lexer;

public enum SmartScriptLexerState {
    /** Used while tokenizing text outside a tag **/
    BASIC,
    /** Used while tokenizing text inside a tag **/
    TAG
}
