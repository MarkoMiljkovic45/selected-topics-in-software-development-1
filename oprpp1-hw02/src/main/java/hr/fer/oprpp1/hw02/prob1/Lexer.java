package hr.fer.oprpp1.hw02.prob1;

/**
 * Used for tokenizing the input text
 */
public class Lexer {
    /**
     * Input text
     */
    private final char[] data;
    /**
     * Current token
     */
    private Token token;
    /**
     * Index of the first unprocessed character
     */
    private int currentIndex;
    /**
     * Current state of lexer
     */
    private LexerState state;

    /**
     * Constructor that accepts input text that will be tokenized
     * @param text the input text
     */
    public Lexer(String text) {
        this.data = text.toCharArray();
        this.currentIndex = 0;
        this.token = null;
        setState(LexerState.BASIC);
    }

    /**
     * Returns the last generated token. It can be called multiple times.
     * It does not generate the next token.
     * @return the last generated token
     */
    public Token getToken() {
        return token;
    }

    /**
     * Generates and returns the next token.
     * @return the next token
     * @throws LexerException if an error occurs
     */
    public Token nextToken() {
        extractNextToken();
        return getToken();
    }

    /**
     * Used for changing lexer state
     * @param state new state
     */
    public void setState(LexerState state) {
        if (state == null) throw new NullPointerException("State can't be null.");
        this.state = state;
    }

    /**
     * Helper method used for extracting the next token.
     */
    private void extractNextToken() {
        //If it is already asserted that the end is reached (EOF) and the method is called again, an Exception is thrown
        if (token != null &&
            token.getType() == TokenType.EOF) {
            throw new LexerException("No tokens available.");
        }

        //Skip whitespace ('\r', '\n', '\t', ' ')
        skipBlanks();

        //If there are no more characters, generate an EOF token
        if (currentIndex >= data.length) {
            token = new Token(TokenType.EOF, null);
            return;
        }

        //Test if in EXTENDED state
        if (state == LexerState.EXTENDED &&
            data[currentIndex] != '#') {
            extractNextExtendedToken();
            return;
        }

        //Test if the next token is a word and read it if true
        if (Character.isLetter(data[currentIndex]) ||
            data[currentIndex] == '\\') {
            readWord();
            return;
        }

        //Test if the next token is a number and read it if true
        if (Character.isDigit(data[currentIndex])) {
            readNumber();
            return;
        }

        //The only remaining option is a symbol
        Character c = Character.valueOf(data[currentIndex++]);
        token = new Token(TokenType.SYMBOL, c);
    }

    public void extractNextExtendedToken() {
        int length         = data.length;
        StringBuilder word = new StringBuilder();

        while(currentIndex < length) {
            char c = data[currentIndex];

            if (isWhitespace(c) || c == '#') {
                break;
            }

            word.append(c);
            currentIndex++;
        }

        token = new Token(TokenType.WORD, word.toString());
    }

    /**
     * Helper method used for extracting numbers from data
     */
    private void readNumber() {
        int startIndex = currentIndex;
        int length     = data.length;

        while(currentIndex < length) {
            if (Character.isDigit(data[currentIndex])) {
                currentIndex++;
            } else {
                break;
            }
        }

        String stringValue = new String(data, startIndex, currentIndex - startIndex);

        try {
            Long value = Long.valueOf(stringValue);
            token = new Token(TokenType.NUMBER, value);

        } catch (NumberFormatException e) {
            throw new LexerException("Invalid number format: " + stringValue);
        }
    }

    /**
     * Helper method used for extracting words from data
     */
    private void readWord() {
        StringBuilder word = new StringBuilder();
        int length         = data.length;

        while(currentIndex < length) {
            char c = data[currentIndex];

            if (c == '\\') {
                word.append(parseEscapeCharacter());
            } else if (Character.isLetter(c)){
                word.append(c);
                currentIndex++;
            } else {
                break;
            }
        }

        token = new Token(TokenType.WORD, word.toString());
    }

    private char parseEscapeCharacter() {
        int length = data.length;

        if (currentIndex >= length) throw new LexerException();

        char c = data[currentIndex++];

        if(c != '\\' || currentIndex >= length) throw new LexerException("No escape sequence found");

        c = data[currentIndex++];

        if (Character.isDigit(c) || c == '\\') {
            return c;
        }

        throw new LexerException("Unexpected escape sequence: \\" + c);
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
