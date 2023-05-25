package hr.fer.oprpp1.hw02.prob1;

/**
 * Used to describe the state of a Lexer
 */
public enum LexerState {
    /** Lexer distinguishes the difference between numbers, words and symbols **/
    BASIC,
    /** Lexer reads all characters (numbers, letters, symbols) as parts of whitespace separated words.  **/
    EXTENDED
}
