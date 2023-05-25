package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

/**
 * Used to tokenize StudentDatabase queries
 */
public class QueryLexer {

    /**
     * Query to be tokenized stored in char array
     */
    private final char[] data;

    /**
     * Index of first unprocessed character
     */
    private int currentIndex;

    /**
     * Most recently processed token
     */
    private QueryToken currentToken;

    /**
     * List of all supported keyword
     */
    private static final List<String> keywords;

    /**
     * List of all supported comparison operators
     */
    private static final List<String> comparisonOperators;

    /**
     * List of all supported logical operators
     */
    private static final List<String> logicalOperators;

    /**
     * List of characters that are not letters but parts of logical operator constructs
     */
    private static final List<Character> comparisonOperatorCharacters;

    static {
        keywords = new ArrayList<>();
        keywords.add("query");
        keywords.add("exit");

        comparisonOperators = new ArrayList<>();
        comparisonOperators.add("<");
        comparisonOperators.add(">");
        comparisonOperators.add("<=");
        comparisonOperators.add(">=");
        comparisonOperators.add("=");
        comparisonOperators.add("!=");
        comparisonOperators.add("LIKE");

        logicalOperators = new ArrayList<>();
        logicalOperators.add("AND");

        comparisonOperatorCharacters = new ArrayList<>();
        comparisonOperatorCharacters.add('<');
        comparisonOperatorCharacters.add('>');
        comparisonOperatorCharacters.add('=');
        comparisonOperatorCharacters.add('!');
    }

    /**
     * Main constructor for initialising the lexer
     * @param query to be tokenized
     */
    public QueryLexer(char[] query) {
        data = query;
        currentIndex = 0;
        currentToken = null;
    }

    /**
     * Alternate constructor that accepts string
     * @param query in string form
     */
    public QueryLexer(String query) {
        this(query.toCharArray());
    }

    /**
     * Getter method for currentToken
     * @return most recently tokenized token
     */
    public QueryToken getCurrentToken() {
        return currentToken;
    }

    /**
     * Extracts and returns the next token from query
     * @return next token from query
     * @throws NoSuchElementException if called when EOF is reached
     * @throws IndexOutOfBoundsException if StringLiteral isn't closed properly
     * @throws IllegalArgumentException if an unexpected character is reached
     */
    public QueryToken nextToken() {
        currentToken = extractNextToken();
        return getCurrentToken();
    }

    /**
     * Helper method to extract the next token
     * @return the next token
     * @throws NoSuchElementException if called when EOF is reached
     * @throws IndexOutOfBoundsException if StringLiteral isn't closed properly
     * @throws IllegalArgumentException if an unexpected character is reached
     */
    private QueryToken extractNextToken() {
        if (currentToken != null &&                                                     //If it is already asserted that the end is reached (EOF) and
                currentToken.getType() == QueryTokenType.EOF) {                         //the method is called again, an Exception is thrown
            throw new NoSuchElementException("No tokens available.");
        }

        skipBlanks();                                                                   //Skip whitespace ('\r', '\n', '\t', ' ')

        if (currentIndex >= data.length) {                                              //If there are no more characters, generate an EOF token
            return new QueryToken(QueryTokenType.EOF, null);
        }

        char currentChar = data[currentIndex];

        if(Character.isLetter(currentChar)) {
            String word = read(c -> Character.isLetter(c) || Character.isDigit(c));

            if(keywords.contains(word)) {                                               //Test if keyword
                return new QueryToken(QueryTokenType.KEYWORD, word);
            }

            if(comparisonOperators.contains(word)) {                                    //Test if comparison operator
                return new QueryToken(QueryTokenType.COMPARISON_OPERATOR, word);
            }

            if(logicalOperators.contains(word.toUpperCase())) {                         //Test if logical operator
                return new QueryToken(QueryTokenType.LOGICAL_OPERATOR, word);
            }

            return new QueryToken(QueryTokenType.FIELD, word);                          //Only other option is StudentRecord field
        }

        if (currentChar == '\"') {                                                      //Test if string literal
            currentIndex++;                                                             //Skip opening "
            String stringLiteral = read(c -> c != '\"');

            if(currentIndex == data.length) {                                           //If read() ended before reaching " throw exception
                throw new IndexOutOfBoundsException("Lexer reached end of file " +
                        "before end of string literal denoted by \"");
            }

            currentIndex++;                                                             //Skip closing "
            return new QueryToken(QueryTokenType.STRING_LITERAL, stringLiteral);
        }

        if(comparisonOperatorCharacters.contains(currentChar)) {                           //Test for comparison operators
            String logicalOperator = read(comparisonOperatorCharacters::contains);
            return new QueryToken(QueryTokenType.COMPARISON_OPERATOR, logicalOperator);
        }

        throw new IllegalArgumentException("Unexpected character " +
                data[currentIndex] + " at index " + currentIndex);
    }

    /**
     * Helper method used for skipping whitespace ('\r', '\n', '\t', ' ')
     */
    private void skipBlanks() {
        int length = data.length;

        while (currentIndex < length &&
                Character.isWhitespace(data[currentIndex])) {
            currentIndex++;
        }
    }

    /**
     * Helper method to read characters while they satisfy predicate acceptCharacter
     * @param acceptCharacter condition that tests if a character should be read
     * @return word that satisfies predicate
     */
    private String read(Predicate<Character> acceptCharacter) {
        int length       = data.length;
        StringBuilder sb = new StringBuilder();

        while(currentIndex < length && acceptCharacter.test(data[currentIndex])) {
            sb.append(data[currentIndex++]);
        }

        return sb.toString();
    }
}
