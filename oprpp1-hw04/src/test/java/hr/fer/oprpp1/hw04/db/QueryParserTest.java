package hr.fer.oprpp1.hw04.db;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QueryParserTest {

    @Test
    public void testQueryParserIsDirectQueryTrue() {
        QueryParser parser = new QueryParser("query jmbag=\"0000000003\"\n");
        assertTrue(parser.isDirectQuery());
    }

    @Test
    public void testQueryParserIsDirectQueryFalse() {
        QueryParser parser = new QueryParser("query jmbag=\"0000000003\" and jmbag=\"0000000003\"\n");
        assertFalse(parser.isDirectQuery());
    }

    @Test
    public void testQueryParserGetQueriedJmbag() {
        QueryParser parser = new QueryParser("query jmbag=\"0000000003\"\n");
        assertEquals("0000000003", parser.getQueriedJMBAG());
    }

    @Test
    public void testQueryParserGetQueriedJmbagNotDirectQuery() {
        QueryParser parser = new QueryParser("query jmbag=\"0000000003\" and jmbag=\"0000000003\"\n");
        assertThrows(IllegalStateException.class, parser::getQueriedJMBAG);
    }

    @Test
    public void testQueryParserOpenedStringLiteral() {
        assertThrows(IndexOutOfBoundsException.class, () -> new QueryParser("query lastName LIKE \"Ba"));
    }

    @Test
    public void testQueryParserSyntaxError() {
        assertThrows(IllegalArgumentException.class, () -> new QueryParser("query lastName firstName"));
    }

    @Test
    public void testQueryParserEx1() {
        QueryParser parser = new QueryParser("query jmbag=\"0000000003\"\n");

        List<ConditionalExpression> expected = new ArrayList<>(List.of(
                new ConditionalExpression(FieldValueGetters.JMBAG, "0000000003", ComparisonOperators.EQUALS)
        ));

        List<ConditionalExpression> actual = parser.getQuery();

        assertEquals(expected, actual);
    }

    @Test
    public void testQueryParserEx2() {
        QueryParser parser = new QueryParser("query     lastName   =     \"Blažić\"");

        List<ConditionalExpression> expected = new ArrayList<>(List.of(
                new ConditionalExpression(FieldValueGetters.LAST_NAME, "Blažić", ComparisonOperators.EQUALS)
        ));

        List<ConditionalExpression> actual = parser.getQuery();

        assertEquals(expected, actual);
    }

    @Test
    public void testQueryParserEx3() {
        QueryParser parser = new QueryParser("query firstName>\"A\" and lastName LIKE \"B*ć\"\n");

        List<ConditionalExpression> expected = new ArrayList<>(List.of(
                new ConditionalExpression(FieldValueGetters.FIRST_NAME, "A", ComparisonOperators.GREATER),
                new ConditionalExpression(FieldValueGetters.LAST_NAME, "B*ć", ComparisonOperators.LIKE)
        ));

        List<ConditionalExpression> actual = parser.getQuery();

        assertEquals(expected, actual);
    }

    @Test
    public void testQueryParserEx4() {
        QueryParser parser = new QueryParser("query firstName>\"A\" and firstName<\"C\" and lastName LIKE \"B*ć\" and jmbag>\"0000000002\"\n");

        List<ConditionalExpression> expected = new ArrayList<>(List.of(
                new ConditionalExpression(FieldValueGetters.FIRST_NAME, "A", ComparisonOperators.GREATER),
                new ConditionalExpression(FieldValueGetters.FIRST_NAME, "C", ComparisonOperators.LESS),
                new ConditionalExpression(FieldValueGetters.LAST_NAME, "B*ć", ComparisonOperators.LIKE),
                new ConditionalExpression(FieldValueGetters.JMBAG, "0000000002", ComparisonOperators.GREATER)
        ));

        List<ConditionalExpression> actual = parser.getQuery();

        assertEquals(expected, actual);
    }

    @Test
    public void testQueryParserEx5() {
        assertThrows(QueryExitException.class, () -> new QueryParser("exit"));
    }
}
