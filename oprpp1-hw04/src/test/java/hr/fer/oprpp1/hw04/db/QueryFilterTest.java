package hr.fer.oprpp1.hw04.db;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class QueryFilterTest {

    @Test
    public void testQueryFilterTrueEx1() {
        StudentRecord record = new StudentRecord("0000000001", "Doe", "John" , 1);

        QueryParser parser = new QueryParser("query jmbag=\"0000000001\"");
        QueryFilter filter = new QueryFilter(parser.getQuery());

        assertTrue(filter.accepts(record));
    }

    @Test
    public void testQueryFilterTrueEx2() {
        StudentRecord record = new StudentRecord("0000000001", "Doe", "John" , 1);

        QueryParser parser = new QueryParser("query lastName=\"Doe\"");
        QueryFilter filter = new QueryFilter(parser.getQuery());

        assertTrue(filter.accepts(record));
    }

    @Test
    public void testQueryFilterTrueEx3() {
        StudentRecord record = new StudentRecord("0000000001", "Doe", "John" , 1);

        QueryParser parser = new QueryParser("query firstName>\"A\" and lastName LIKE \"D*e\"");
        QueryFilter filter = new QueryFilter(parser.getQuery());

        assertTrue(filter.accepts(record));
    }

    @Test
    public void testQueryFilterTrueEx4() {
        StudentRecord record = new StudentRecord("0000000001", "Doe", "John" , 1);

        QueryParser parser = new QueryParser("query firstName>\"A\" and firstName<\"K\" and lastName LIKE \"D*\" and jmbag<\"0000000002\"");
        QueryFilter filter = new QueryFilter(parser.getQuery());

        assertTrue(filter.accepts(record));
    }

    @Test
    public void testQueryFilterFalseEx1() {
        StudentRecord record = new StudentRecord("0000000002", "Doe", "John" , 1);

        QueryParser parser = new QueryParser("query jmbag=\"0000000001\"");
        QueryFilter filter = new QueryFilter(parser.getQuery());

        assertFalse(filter.accepts(record));
    }

    @Test
    public void testQueryFilterFalseEx2() {
        StudentRecord record = new StudentRecord("0000000001", "Jackson", "John" , 1);

        QueryParser parser = new QueryParser("query lastName=\"Doe\"");
        QueryFilter filter = new QueryFilter(parser.getQuery());

        assertFalse(filter.accepts(record));
    }

    @Test
    public void testQueryFilterFalseEx3() {
        StudentRecord record = new StudentRecord("0000000001", "Jackson", "John" , 1);

        QueryParser parser = new QueryParser("query firstName>\"A\" and lastName LIKE \"D*e\"");
        QueryFilter filter = new QueryFilter(parser.getQuery());

        assertFalse(filter.accepts(record));
    }

    @Test
    public void testQueryFilterFalseEx4() {
        StudentRecord record = new StudentRecord("0000000003", "Doe", "John" , 1);

        QueryParser parser = new QueryParser("query firstName>\"A\" and firstName<\"K\" and lastName LIKE \"D*\" and jmbag<\"0000000002\"");
        QueryFilter filter = new QueryFilter(parser.getQuery());

        assertFalse(filter.accepts(record));
    }
}
