package hr.fer.oprpp1.hw04.db;

import java.util.Objects;

/**
 * Used to represent a query token that QueryLexer produces
 */
public class QueryToken {

    /**
     * Token type
     */
    private final QueryTokenType type;
    /**
     * Value stored in token
     */
    private final Object value;

    /**
     * Main constructor to create a QueryToken
     * @param type of token
     * @param value to be stored in token
     * @throws NullPointerException if type is <code>null</code>
     */
    public QueryToken(QueryTokenType type, Object value) {
        if (type == null) throw new NullPointerException("Token type can't be null");
        this.type = type;
        this.value = value;
    }

    public QueryTokenType getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QueryToken that = (QueryToken) o;
        return type == that.type && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, value);
    }

    @Override
    public String toString() {
        return type + ": " + value;
    }
}
