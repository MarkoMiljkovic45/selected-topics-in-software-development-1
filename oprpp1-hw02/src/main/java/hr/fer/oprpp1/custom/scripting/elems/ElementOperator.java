package hr.fer.oprpp1.custom.scripting.elems;

import java.util.Objects;

/**
 * Used to represent an operator expression
 */
public class ElementOperator extends Element {

    /**
     * Stores the symbol of this operator
     */
    private final String symbol;

    /**
     * The main constructor used to set the symbol of this operator
     * @param symbol of this operator expression
     */
    public ElementOperator(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Getter method for symbol
     * @return symbol
     */
    public String getSymbol() {
        return symbol;
    }

    @Override
    public String asText() {
        return symbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ElementOperator that = (ElementOperator) o;
        return Objects.equals(symbol, that.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol);
    }
}
