package hr.fer.oprpp1.hw04.db;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StudentDatabaseTest {

    private final static List<String> testEntries;
    private final static StudentDatabase testDB;

    static {
        testEntries = new ArrayList<>();
        testEntries.add("0000000001\tPeric\tPero\t1");
        testEntries.add("0000000002\tIvic\tIvo\t5");
        testEntries.add("0000000003\tAnic\tAna\t3");

        testDB = new StudentDatabase(testEntries);
    }

    @Test
    public void testConstructorStudentDatabase() {
        assertDoesNotThrow(() -> new StudentDatabase(testEntries));
    }

    @Test
    public void testConstructorDuplicateJmbagStudentDatabase() {
        List<String> duplicateJmbag = new ArrayList<>();

        duplicateJmbag.add("0000000001 Peric Pero 1");
        duplicateJmbag.add("0000000001 Ivic Ivo 5");

        assertThrows(StudentDatabaseException.class, () -> new StudentDatabase(duplicateJmbag));
    }

    @Test
    public void testConstructorGradeTooSmallStudentDatabase() {
        List<String> tooSmallGrade = new ArrayList<>();

        tooSmallGrade.add("0000000001 Peric Pero 0");

        assertThrows(StudentDatabaseException.class, () -> new StudentDatabase(tooSmallGrade));
    }

    @Test
    public void testConstructorGradeTooBigStudentDatabase() {
        List<String> tooBigGrade = new ArrayList<>();

        tooBigGrade.add("0000000001 Ivic Ivo 6");

        assertThrows(StudentDatabaseException.class, () -> new StudentDatabase(tooBigGrade));
    }

    @Test
    public void testForJmbagStudentDatabase() {
        StudentRecord expectedRecord = new StudentRecord("0000000001", "Peric", "Pero", 1);

        assertEquals(expectedRecord, testDB.forJMBAG("0000000001"));
    }

    @Test
    public void testForAbsentJmbagStudentDatabase() {
        assertNull(testDB.forJMBAG("0000000000"));
    }

    @Test
    public void testFilterTrueStudentDatabase() {
        assertEquals(testEntries.size(), testDB.filter(r -> true).size());
    }

    @Test
    public void testFilterFalseStudentDatabase() {
        assertEquals(0, testDB.filter(r -> false).size());
    }
}
