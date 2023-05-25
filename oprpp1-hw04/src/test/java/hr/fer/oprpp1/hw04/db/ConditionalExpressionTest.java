package hr.fer.oprpp1.hw04.db;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ConditionalExpressionTest {

    @Test
    public void testConditionalExpression() {
        ConditionalExpression expr = new ConditionalExpression(
                FieldValueGetters.LAST_NAME,
                "Bos*",
                ComparisonOperators.LIKE
        );

        StudentRecord record = new StudentRecord("1", "Boston", "John", 1);

        boolean recordSatisfies = expr.getComparisonOperator().satisfied(
                expr.getFieldGetter().get(record), // returns lastName from given record
                expr.getStringLiteral() // returns "Bos*"
        );

        assertEquals(FieldValueGetters.LAST_NAME, expr.getFieldGetter());
        assertEquals("Bos*", expr.getStringLiteral());
        assertEquals(ComparisonOperators.LIKE, expr.getComparisonOperator());

        assertTrue(recordSatisfies);
    }

    @Test
    public void testConditionalExpressionFieldGetterNull() {
        assertThrows(NullPointerException.class, () -> new ConditionalExpression(
                null,
                "Bos*",
                ComparisonOperators.LIKE
        ));
    }

    @Test
    public void testConditionalOperatorComparisonOperatorNull() {
        assertThrows(NullPointerException.class, () -> new ConditionalExpression(
                FieldValueGetters.LAST_NAME,
                "Bos*",
                null
        ));
    }
}
