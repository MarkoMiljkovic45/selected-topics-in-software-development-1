package hr.fer.oprpp1.hw02.prob1;

/**
 * Enumeration of all possible token types
 */
public enum TokenType {
    /** End Of File - Used to represent that there are no more tokens **/
    EOF,
    /** Any sequence of one or more characters (Character.isLetter() returns true) **/
    WORD,
    /** Any sequence of one or more digits that can be represented as type Long **/
    NUMBER,
    /** Any singular character that is left after removing all the words, numbers and whitespaces **/
    SYMBOL
}
