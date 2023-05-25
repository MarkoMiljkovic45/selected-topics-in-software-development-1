package hr.fer.oprpp1.hw04.db;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FieldValueGettersTest {

    private static final String testRecordFirstName;
    private static final String testRecordLastName;
    private static final String testRecordJmbag;
    private static final StudentRecord testRecord;

    static {
        testRecordFirstName = "John";
        testRecordLastName  = "Doe";
        testRecordJmbag     = "0000000001";

        testRecord = new StudentRecord(testRecordJmbag, testRecordLastName, testRecordFirstName, 1);
    }

    @Test
    public void testFieldValueGetterFirstName() {
        assertEquals(testRecordFirstName, FieldValueGetters.FIRST_NAME.get(testRecord));
    }

    @Test
    public void testFieldValueGetterLastName() {
        assertEquals(testRecordLastName, FieldValueGetters.LAST_NAME.get(testRecord));
    }

    @Test
    public void testFieldValueGetterJmbag() {
        assertEquals(testRecordJmbag, FieldValueGetters.JMBAG.get(testRecord));
    }
}
