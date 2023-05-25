package hr.fer.oprpp1.custom.scripting.lexer;

import hr.fer.oprpp1.custom.collections.LinkedListIndexedCollection;
import hr.fer.oprpp1.custom.collections.List;

/**
 * Lexer used for tokenizing text written in the <i>Smart Script</i> language.
 */
public class SmartScriptLexer {

    /**
     * Text to be tokenized
     */
    private final char[] data;
    /**
     * Index of the first unprocessed character
     */
    private int currentIndex;
    /**
     * Most recently processed token
     */
    private SmartScriptToken currentToken;
    /**
     * Current state of the lexer
     */
    private SmartScriptLexerState state;

    /**
     * A list of all the keywords in the <i>Smart Script</i> language
     */
    private static final List keywords;

    static {
        keywords = new LinkedListIndexedCollection();
        keywords.add("FOR");
        keywords.add("END");
        keywords.add("=");
    }

    /**
     * A list of all operators in the <i>Smart Script</i> language
     */
    private static final List operators;

    static {
        operators = new LinkedListIndexedCollection();
        operators.add('+');
        operators.add('-');
        operators.add('*');
        operators.add('/');
        operators.add('^');
    }

    /**
     * The main constructor used for initialising the lexer
     * @param documentBody text of the document to be tokenized
     */
    public SmartScriptLexer(String documentBody) {
        this.data = documentBody.toCharArray();
        this.currentIndex = 0;
        this.state = SmartScriptLexerState.BASIC;
        this.currentToken = null;
    }

    /**
     * Getter method for the most recently processed token
     * @return most recently processed token
     */
    public SmartScriptToken getCurrentToken() {
        return currentToken;
    }

    /**
     * Setter used for changing the lexers state
     * @param state new state
     */
    public void setState(SmartScriptLexerState state) {
        this.state = state;
    }

    /**
     * Generates and returns the next token.
     * @return next token
     * @throws SmartScriptLexerException if an error occurs while tokenizing
     */
    public SmartScriptToken nextToken() {
        extractNextToken();
        return getCurrentToken();
    }

    /**
     * Helper method used for extracting the next token.
     * @throws SmartScriptLexerException if an error occurs while extracting the next token
     */
    private void extractNextToken() {
        //If it is already asserted that the end is reached (EOF) and the method is called again, an Exception is thrown
        if (currentToken != null &&
                currentToken.getType() == SmartScriptTokenType.EOF) {
            throw new SmartScriptLexerException("No tokens available.");
        }

        //Skip whitespace ('\r', '\n', '\t', ' ')
        skipBlanks();

        //If there are no more characters, generate an EOF token
        if (currentIndex >= data.length) {
            currentToken = new SmartScriptToken(SmartScriptTokenType.EOF, null);
            return;
        }

        if (this.state == SmartScriptLexerState.BASIC) {
            extractNextBasicToken();
            return;
        }

        if (this.state == SmartScriptLexerState.TAG) {
            extractNextTagToken();
            return;
        }

        throw new SmartScriptLexerException("Lexer is in an invalid state: " + this.state);
    }

    /**
     * Helper method used to extract the next token while the lexer is in state BASIC
     */
    private void extractNextBasicToken() {
        char c = data[currentIndex];

        //Test for opened tag {$
        if (c == '{') {
            if (testOpenedTagSequence()) {
                readOpeningTag();
                return;
            }
        }

        readText();
    }

    /**
     * Helper method to test for tag opening sequence {$
     * @return true if the next two characters are {$, false otherwise
     */
    private boolean testOpenedTagSequence() {
        if (currentIndex + 1 < data.length) {
            return data[currentIndex] == '{' &&
                   data[currentIndex + 1] == '$';
        } else {
            return false;
        }
    }

    /**
     * Helper method to extract the closing tag $} token
     */
    private void readOpeningTag() {
        currentToken = new SmartScriptToken(SmartScriptTokenType.OPENED_TAG, null);
        currentIndex += 2;
    }

    /**
     * Helper method used for extracting text while lexer is in state BASIC
     */
    private void readText() {
        int length = data.length;
        StringBuilder text = new StringBuilder();

        while(currentIndex < length) {
            char c = data[currentIndex];

            if (c == '{') {
                if (testOpenedTagSequence()) {
                    break;
                }
            }

            if (c == '\\') {
                text.append(readBasicEscapeSequence());
                continue;
            }

            text.append(c);
            currentIndex++;
        }

        currentToken = new SmartScriptToken(SmartScriptTokenType.TEXT, text.toString());
    }

    /**
     * Helper method to test escape sequences in lexer state BASIC
     * @return escape sequence character
     * @throws SmartScriptLexerException if invalid sequence is read
     */
    private String readBasicEscapeSequence() {
        currentIndex++;

        if (currentIndex < data.length) {
            char c = data[currentIndex++];

            if (c == '\\' || c == '{') {
                return "\\" + c;
            }
        }
        throw new SmartScriptLexerException("Invalid escape sequence.");
    }

    /**
     * Helper method used to extract the next token while the lexer is in state TAG
     */
    private void extractNextTagToken() {
        char c = data[currentIndex];

        //Test for closed tag $}
        if (c == '$') {
            if (testClosedTagSequence()) {
                readClosingTag();
                return;
            }
        }

        //Test for keyword and variable readExpression()
        if (Character.isLetter(c)) {
            readExpression();
            return;
        }

        //Test for echo keyword =
        if (c == '=') {
            readEcho();
            return;
        }

        //Test for @function readFunction()
        if (c == '@') {
            readFunction();
            return;
        }

        //Test for string readString()
        if (c == '"') {
            readString();
            return;
        }

        //Test for number readNumber()
        if (Character.isDigit(c) || c == '+' || c == '-') {
            readNumber();
            return;
        }

        //Test for operator readOperator()
        if (operators.contains(c)) {
            readOperator();
            return;
        }

        throw new SmartScriptLexerException("Unexpected character: " + c + " at index: " + currentIndex);
    }

    /**
     * Helper method to extract the closing tag $} token
     */
    private void readClosingTag() {
        currentToken = new SmartScriptToken(SmartScriptTokenType.CLOSED_TAG, null);
        currentIndex += 2;
    }

    /**
     * Helper method used for extracting variable and keyword tags
     */
    private void readExpression() {
        String word = readWord();
        if (keywords.contains(word.toUpperCase())) {
            currentToken = new SmartScriptToken(SmartScriptTokenType.KEYWORD, word);
        } else {
            currentToken = new SmartScriptToken(SmartScriptTokenType.VARIABLE, word);
        }
    }

    /**
     * Helper method used to read a word while lexer is in state TAG
     */
    private String readWord() {
        int length = data.length;
        StringBuilder sb = new StringBuilder();

        while (currentIndex < length) {
            char c = data[currentIndex];

            if (c == '$') {
                if (testClosedTagSequence()) {
                    break;
                }
            }

            if (isWhitespace(c)) {
                break;
            }

            if (Character.isLetter(c) || Character.isDigit(c) || c == '_') {
                sb.append(c);
                currentIndex++;
            } else {
                break;
            }
        }

        return sb.toString();
    }

    /**
     * Helper method used to extract echo keyword tag
     */
    private void readEcho() {
        currentToken = new SmartScriptToken(SmartScriptTokenType.KEYWORD, "=");
        currentIndex++;
    }

    /**
     * Helper method used to extract a function tag
     */
    private void readFunction() {
        currentIndex++;
        currentToken = new SmartScriptToken(SmartScriptTokenType.FUNCTION, readWord());
    }

    /**
     * Helper method used to extract a string tag
     */
    private void readString() {
        int length = data.length;
        StringBuilder sb = new StringBuilder();

        //Accept first "
        sb.append(data[currentIndex++]);

        while (currentIndex < length) {
            char c = data[currentIndex];

            if (c == '\\') {
                sb.append(readTagEscapeSequence());
                continue;
            }

            sb.append(c);
            currentIndex++;

            if (c == '"') {
                break;
            }
        }

        currentToken = new SmartScriptToken(SmartScriptTokenType.STRING, sb.toString());
    }

    /**
     * Helper method that reads an escape sequence while lexer is in state TAG
     * @return escape sequence
     * @throws SmartScriptLexerException if escape sequence is invalid
     */
    private String readTagEscapeSequence() {
        currentIndex++;

        if (currentIndex < data.length) {
            char c = data[currentIndex++];

            if (
                    c == '\\' ||
                    c == '"'  ||
                    c == 'n'  ||
                    c == 't'  ||
                    c == 'r'
            ) {
                return "\\" + c;
            }
        }
        throw new SmartScriptLexerException("Invalid escape sequence.");
    }

    /**
     * Helper method used to extract a number or an operator tag
     */
    private void readNumber() {
        int length = data.length;

        //Test for operator
        if (currentIndex + 1 < length) {
            if (
                    (data[currentIndex] == '+' || data[currentIndex] == '-') &&
                    !Character.isDigit(data[currentIndex + 1])
            ) {
                readOperator();
                return;
            }
        }

        StringBuilder sb   = new StringBuilder();
        boolean isNegative = false;
        boolean isInt      = true;

        //Test sign
        if (data[currentIndex] == '+' || data[currentIndex] == '-') {
            isNegative = data[currentIndex++] == '-';
        }

        while (currentIndex < length) {
            char c = data[currentIndex];

            if (!Character.isDigit(c) && c != '.') {
                break;
            }

            if (c == '.') isInt = false;

            sb.append(c);
            currentIndex++;
        }

        String number = sb.toString();

        if(isNegative) number = "-" + number;

        try {
            if (isInt) {
                currentToken = new SmartScriptToken(SmartScriptTokenType.INTEGER_CONSTANT, Integer.parseInt(number));
            } else {
                currentToken = new SmartScriptToken(SmartScriptTokenType.DOUBLE_CONSTANT, Double.parseDouble(number));
            }
        } catch (NumberFormatException e) {
            throw new SmartScriptLexerException("Invalid number format.");
        }
    }

    /**
     * Helper method used to extract an operator tag
     */
    private void readOperator() {
        char c = data[currentIndex++];

        if (operators.contains(c)) {
            currentToken = new SmartScriptToken(SmartScriptTokenType.OPERATOR, c);
            return;
        }

        throw new SmartScriptLexerException("Invalid character: " + c + " at index: " + currentIndex);
    }

    /**
     * Helper method to test for tag closing sequence $}
     * @return true if the next two characters are $}, false otherwise
     */
    private boolean testClosedTagSequence() {
        if (currentIndex + 1 < data.length) {
            return data[currentIndex] == '$' &&
                    data[currentIndex + 1] == '}';
        } else {
            return false;
        }
    }

    /**
     * Helper method used for skipping whitespace ('\r', '\n', '\t', ' ')
     */
    private void skipBlanks() {
        int length = data.length;

        while (currentIndex < length &&
                isWhitespace(data[currentIndex])) {
            currentIndex++;
        }
    }

    /**
     * Helper method to test for whitespace characters ('\r', '\n', '\t', ' ')
     * @param c character to be tested
     * @return true if c equals '\r', '\n', '\t' or ' ', false otherwise
     */
    private boolean isWhitespace(char c) {
        return c == ' ' ||
                c == '\n' ||
                c == '\r' ||
                c == '\t';
    }
}
