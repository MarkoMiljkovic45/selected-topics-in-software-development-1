package hr.fer.oprpp1.hw04.db;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ComparisonOperatorsTest {

    @Test
    public void testComparisonOperatorLessTrue() {
        assertTrue(ComparisonOperators.LESS.satisfied("A", "Z"));
    }

    @Test
    public void testComparisonOperatorLessFalse() {
        assertFalse(ComparisonOperators.LESS.satisfied("Z", "A"));
    }

    @Test
    public void testComparisonOperatorLessFirstOpNull() {
        assertThrows(NullPointerException.class, () -> ComparisonOperators.LESS.satisfied(null, "Z"));
    }

    @Test
    public void testComparisonOperatorLessSecondOpNull() {
        assertThrows(NullPointerException.class, () -> ComparisonOperators.LESS.satisfied("Z", null));
    }

    @Test
    public void testComparisonOperatorLessOrEqualsTrue() {
        assertTrue(ComparisonOperators.LESS_OR_EQUALS.satisfied("A", "Z"));
    }

    @Test
    public void testComparisonOperatorLessOrEqualsFalse() {
        assertFalse(ComparisonOperators.LESS_OR_EQUALS.satisfied("Z", "A"));
    }

    @Test
    public void testComparisonOperatorLessOrEqualsFirstOpNull() {
        assertThrows(NullPointerException.class, () -> ComparisonOperators.LESS_OR_EQUALS.satisfied(null, "Z"));
    }

    @Test
    public void testComparisonOperatorLessOrEqualsSecondOpNull() {
        assertThrows(NullPointerException.class, () -> ComparisonOperators.LESS_OR_EQUALS.satisfied("Z", null));
    }

    @Test
    public void testComparisonOperatorGreaterTrue() {
        assertTrue(ComparisonOperators.GREATER.satisfied("Z", "A"));
    }

    @Test
    public void testComparisonOperatorGreaterFalse() {
        assertFalse(ComparisonOperators.GREATER.satisfied("A", "Z"));
    }

    @Test
    public void testComparisonOperatorGreaterFirstOpNull() {
        assertThrows(NullPointerException.class, () -> ComparisonOperators.GREATER.satisfied(null, "Z"));
    }

    @Test
    public void testComparisonOperatorGreaterSecondOpNull() {
        assertThrows(NullPointerException.class, () -> ComparisonOperators.GREATER.satisfied("Z", null));
    }

    @Test
    public void testComparisonOperatorGreaterOrEqualsTrue() {
        assertTrue(ComparisonOperators.GREATER_OR_EQUALS.satisfied("A", "A"));
    }

    @Test
    public void testComparisonOperatorGreaterOrEqualsFalse() {
        assertFalse(ComparisonOperators.GREATER_OR_EQUALS.satisfied("A", "Z"));
    }

    @Test
    public void testComparisonOperatorGreaterOrEqualsFirstOpNull() {
        assertThrows(NullPointerException.class, () -> ComparisonOperators.GREATER_OR_EQUALS.satisfied(null, "Z"));
    }

    @Test
    public void testComparisonOperatorGreaterOrEqualsSecondOpNull() {
        assertThrows(NullPointerException.class, () -> ComparisonOperators.GREATER_OR_EQUALS.satisfied("Z", null));
    }

    @Test
    public void testComparisonOperatorEqualsTrue() {
        assertTrue(ComparisonOperators.EQUALS.satisfied("A", "A"));
    }

    @Test
    public void testComparisonOperatorEqualsFalse() {
        assertFalse(ComparisonOperators.EQUALS.satisfied("Z", "A"));
    }

    @Test
    public void testComparisonOperatorEqualsFirstOpNull() {
        assertThrows(NullPointerException.class, () -> ComparisonOperators.EQUALS.satisfied(null, "Z"));
    }

    @Test
    public void testComparisonOperatorEqualsSecondOpNull() {
        assertFalse(ComparisonOperators.EQUALS.satisfied("Z", null));
    }

    @Test
    public void testComparisonOperatorNotEqualsTrue() {
        assertTrue(ComparisonOperators.NOT_EQUALS.satisfied("A", "Z"));
    }

    @Test
    public void testComparisonOperatorNotEqualsFalse() {
        assertFalse(ComparisonOperators.NOT_EQUALS.satisfied("A", "A"));
    }

    @Test
    public void testComparisonOperatorNotEqualsFirstOpNull() {
        assertThrows(NullPointerException.class, () -> ComparisonOperators.NOT_EQUALS.satisfied(null, "Z"));
    }

    @Test
    public void testComparisonOperatorNotEqualsSecondOpNull() {
        assertTrue(ComparisonOperators.NOT_EQUALS.satisfied("Z", null));
    }

    @Test
    public void testComparisonOperatorLikeTrue() {
        IComparisonOperator like = ComparisonOperators.LIKE;

        assertTrue(like.satisfied("ABA", "*"));
        assertTrue(like.satisfied("ABA", "A*"));
        assertTrue(like.satisfied("ABA", "*A"));
        assertTrue(like.satisfied("ABA", "A*A"));
        assertTrue(like.satisfied("AAAA", "AA*AA"));
    }

    @Test
    public void testComparisonOperatorLikeFalse() {
        IComparisonOperator like = ComparisonOperators.LIKE;

        assertFalse(like.satisfied("ABA", "AAA"));
        assertFalse(like.satisfied("ABA", "B*"));
        assertFalse(like.satisfied("ABA", "*B"));
        assertFalse(like.satisfied("ABA", "B*A"));
        assertFalse(like.satisfied("AAA", "AA*AA"));
    }

    @Test
    public void testComparisonOperatorLikeFirstOperandNull() {
        assertThrows(NullPointerException.class, () -> ComparisonOperators.LIKE.satisfied(null, "null"));
    }

    @Test
    public void testComparisonOperatorLikeSecondOperandNull() {
        assertThrows(NullPointerException.class, () -> ComparisonOperators.LIKE.satisfied("null", null));
    }

    @Test
    public void testComparisonOperatorLikeMultipleWildcard1() {
        assertThrows(IllegalArgumentException.class, () -> ComparisonOperators.LIKE.satisfied("ABA", "A*B*A"));
    }

    @Test
    public void testComparisonOperatorLikeMultipleWildcard2() {
        assertThrows(IllegalArgumentException.class, () -> ComparisonOperators.LIKE.satisfied("ABA", "***"));
    }
}
