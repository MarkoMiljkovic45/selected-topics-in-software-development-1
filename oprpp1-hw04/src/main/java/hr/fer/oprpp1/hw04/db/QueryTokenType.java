package hr.fer.oprpp1.hw04.db;

/**
 * Types of query tokens
 */
public enum QueryTokenType {
    /* End of File, signals parser to stop calling nextToken() */
    EOF,
    /* StudentRecord field identifier */
    FIELD,
    /* SimpleQueryLanguage keyword, currently only supports "query" and "exit" */
    KEYWORD,
    /* Comparison operators: <, <=, >, >=, =, !=, LIKE */
    COMPARISON_OPERATOR,
    /* Logical operators: AND (case insensitive) */
    LOGICAL_OPERATOR,
    /* String constant, beginning and end marked with "*/
    STRING_LITERAL
}
