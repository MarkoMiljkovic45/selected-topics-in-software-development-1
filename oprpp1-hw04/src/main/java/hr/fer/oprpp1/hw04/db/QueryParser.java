package hr.fer.oprpp1.hw04.db;

import java.util.*;

/**
 * Used for parsing StudentDatabase queries
 */
public class QueryParser {

    /**
     * Lexer used to tokenize query
     */
    private final QueryLexer lexer;

    /**
     * Query parsed into a list of ConditionalExpressions
     */
    private final List<ConditionalExpression> query;

    /**
     * Map used to convert tokens to IFieldValueGetters
     */
    private static final Map<String, IFieldValueGetter> fieldValueGettersMap;

    /**
     * Map used to convert tokens to IComparisonOperators
     */
    private static final Map<String, IComparisonOperator> comparisonOperatorsMap;

    static {
        fieldValueGettersMap = new HashMap<>();
        fieldValueGettersMap.put("firstName", FieldValueGetters.FIRST_NAME);
        fieldValueGettersMap.put("lastName",  FieldValueGetters.LAST_NAME);
        fieldValueGettersMap.put("jmbag",     FieldValueGetters.JMBAG);

        comparisonOperatorsMap = new HashMap<>();
        comparisonOperatorsMap.put("<", ComparisonOperators.LESS);
        comparisonOperatorsMap.put(">", ComparisonOperators.GREATER);
        comparisonOperatorsMap.put("<=", ComparisonOperators.LESS_OR_EQUALS);
        comparisonOperatorsMap.put(">=", ComparisonOperators.GREATER_OR_EQUALS);
        comparisonOperatorsMap.put("=", ComparisonOperators.EQUALS);
        comparisonOperatorsMap.put("!=", ComparisonOperators.NOT_EQUALS);
        comparisonOperatorsMap.put("LIKE", ComparisonOperators.LIKE);
    }

    /**
     * Main constructor used to initialize the parser
     * @param rawQuery query in String form
     * @throws NoSuchElementException if called when EOF is reached
     * @throws IndexOutOfBoundsException if StringLiteral isn't closed properly
     * @throws IllegalArgumentException if an unexpected character or token is reached
     */
    public QueryParser(String rawQuery) {
        lexer = new QueryLexer(rawQuery);
        query = new ArrayList<>();

        lexer.nextToken();
        parse();
    }

    /**
     * Tests if query is direct. A query is direct if it contains only one expression and that
     * expression is jmbag="stringLiteral"
     * @return true if query is direct, false otherwise
     */
    public boolean isDirectQuery() {
        return query.size() == 1 &&
               query.get(0).getFieldGetter().equals(FieldValueGetters.JMBAG) &&
               query.get(0).getComparisonOperator().equals(ComparisonOperators.EQUALS);
    }

    /**
     * If query is direct returns the queried jmbag, otherwise it throws
     * a IllegalStateException
     * @return jmbag that was directly queried
     * @throws IllegalStateException if query is not direct and method is called
     */
    public String getQueriedJMBAG() {
        if (!isDirectQuery()) throw new IllegalStateException("Query is not direct.");
        return query.get(0).getStringLiteral();
    }

    /**
     * Used to get the parsed query as a list of conditional expressions
     * @return query as a list of conditional expressions
     */
    public List<ConditionalExpression> getQuery() {
        return query;
    }

    /**
     * Helper method used to parse query with the help of lexer
     * @throws IndexOutOfBoundsException if StringLiteral isn't closed properly
     * @throws IllegalArgumentException if an unexpected character or token is reached
     * @throws QueryExitException when exit keyword is reached
     */
    private void parse() {
        while(lexer.getCurrentToken().getType() != QueryTokenType.EOF) {
            QueryToken token = lexer.getCurrentToken();

            if (token.getType() == QueryTokenType.KEYWORD) {             //Look for keyword query or exit
                if (token.getValue().equals("query")) {
                    parseQuery();
                    continue;
                }

                if (token.getValue().equals("exit")) {
                    throw new QueryExitException();
                }
            }

            throw new IllegalArgumentException("Unexpected token: " + token + ", expected KEYWORD");
        }
    }

    /**
     * Used to parse a query keyword expression and add it to ConditionalExpression list
     * @throws IndexOutOfBoundsException if StringLiteral isn't closed properly
     * @throws IllegalArgumentException if an unexpected character or token is reached
     */
    private void parseQuery() {
        QueryToken token = lexer.nextToken();

        while(token.getType() != QueryTokenType.EOF && token.getType() != QueryTokenType.KEYWORD) {
            IFieldValueGetter   fieldGetter;
            IComparisonOperator comparisonOperator;
            String              stringLiteral;

            if (token.getType() != QueryTokenType.FIELD) {
                throw new IllegalArgumentException("Unexpected token: " + token.getType() +
                        ", expected FIELD");
            }

            fieldGetter = fieldValueGettersMap.get((String) token.getValue());
            token = lexer.nextToken();

            if (token.getType() != QueryTokenType.COMPARISON_OPERATOR) {
                throw new IllegalArgumentException("Unexpected token: " + token.getType() +
                        ", expected COMPARISON_OPERATOR");
            }

            comparisonOperator = comparisonOperatorsMap.get((String) token.getValue());
            token = lexer.nextToken();

            if (token.getType() != QueryTokenType.STRING_LITERAL) {
                throw new IllegalArgumentException("Unexpected token: " + token.getType() +
                        ", expected STRING_LITERAL");
            }

            stringLiteral = (String) token.getValue();
            token = lexer.nextToken();

            query.add(new ConditionalExpression(fieldGetter, stringLiteral, comparisonOperator));

            if(token.getType() == QueryTokenType.LOGICAL_OPERATOR) {
                token = lexer.nextToken();
            }
        }
    }
}
