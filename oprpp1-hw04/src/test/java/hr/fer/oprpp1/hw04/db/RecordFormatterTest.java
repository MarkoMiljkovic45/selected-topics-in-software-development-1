package hr.fer.oprpp1.hw04.db;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RecordFormatterTest {

    @Test
    public void testRecordFormatterEx1() {
        StudentRecord record = new StudentRecord("0000000002", "Doe", "John" , 1);

        String expected = """
                +============+=====+======+===+
                | 0000000002 | Doe | John | 1 |
                +============+=====+======+===+
                """;

        String actual = concatRows(RecordFormatter.format(List.of(record)));

        assertEquals(expected, actual);
    }

    @Test
    public void testRecordFormatterEx2() {
        List<StudentRecord> records = List.of(
                new StudentRecord("0000000001", "Smith", "Will", 5),
                new StudentRecord("0000000002", "Doe", "John" , 1),
                new StudentRecord("0000000003", "Ferguson", "Robert", 3)
        );

        String expected = """
                +============+==========+========+===+
                | 0000000001 | Smith    | Will   | 5 |
                | 0000000002 | Doe      | John   | 1 |
                | 0000000003 | Ferguson | Robert | 3 |
                +============+==========+========+===+
                """;

        String actual = concatRows(RecordFormatter.format(records));

        assertEquals(expected, actual);
    }

    /**
     * Helper method to get a single string from list of rows
     * @param rows to be concatenated
     * @return string of concatenated rows
     */
    private static String concatRows(List<String> rows) {
        StringBuilder sb = new StringBuilder();

        for (String str: rows) {
            sb.append(str).append("\n");
        }

        return sb.toString();
    }
}
