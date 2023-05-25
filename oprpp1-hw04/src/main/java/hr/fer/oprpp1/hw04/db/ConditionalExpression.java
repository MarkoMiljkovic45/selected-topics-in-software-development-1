package hr.fer.oprpp1.hw04.db;

import java.util.Objects;

/**
 * Class used to evaluate conditional expressions
 */
public class ConditionalExpression {

    /**
     * Gets the field that will be compared with stringLiteral
     */
    private final IFieldValueGetter fieldGetter;
    /**
     * String that will be compared against a certain field of StudentRecord
     */
    private final String stringLiteral;
    /**
     * A comparison operator that will be used to compare a StudentRecord field and stringLiteral
     */
    private final IComparisonOperator comparisonOperator;

    /**
     * The main constructor
     * @param fieldGetter specifies the field from StudentRecord that will be compared
     * @param stringLiteral that will be compared to StudentRecord field
     * @param comparisonOperator that will be used to compare a StudentRecord field and stringLiteral
     * @throws NullPointerException if any parameter is <code>null</code>
     */
    public ConditionalExpression(IFieldValueGetter fieldGetter, String stringLiteral, IComparisonOperator comparisonOperator) {
        if (fieldGetter == null) throw new NullPointerException("Field getter can't be null.");
        if (comparisonOperator == null) throw new NullPointerException("Comparison operator can't be null");
        if (stringLiteral == null) throw new NullPointerException("String literal can't be null");

        this.fieldGetter = fieldGetter;
        this.stringLiteral = stringLiteral;
        this.comparisonOperator = comparisonOperator;
    }

    public IFieldValueGetter getFieldGetter() {
        return fieldGetter;
    }

    public String getStringLiteral() {
        return stringLiteral;
    }

    public IComparisonOperator getComparisonOperator() {
        return comparisonOperator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConditionalExpression that = (ConditionalExpression) o;
        return fieldGetter.equals(that.fieldGetter) && stringLiteral.equals(that.stringLiteral) && comparisonOperator.equals(that.comparisonOperator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fieldGetter, stringLiteral, comparisonOperator);
    }

    @Override
    public String toString() {
        return fieldGetter + " " + comparisonOperator + " \"" + stringLiteral + "\"";
    }
}
