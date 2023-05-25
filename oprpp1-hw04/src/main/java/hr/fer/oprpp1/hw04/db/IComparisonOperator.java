package hr.fer.oprpp1.hw04.db;

/**
 * Used to compare two strings using some comparison operator
 */
@FunctionalInterface
public interface IComparisonOperator {

    /**
     * Compares value1 with value2 using a defined comparison operator.
     * @param value1 first operand
     * @param value2 second operand
     * @return result of value1 [comparison operator] value2
     * @throws IllegalArgumentException if method gets bad operands
     * @throws NullPointerException if operand is <code>null</code> when it shouldn't be
     */
    boolean satisfied(String value1, String value2);
}
