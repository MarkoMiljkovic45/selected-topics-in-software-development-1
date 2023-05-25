package hr.fer.oprpp1.hw04.db;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Class used to represent a database
 */
public class StudentDatabase {

    /**
     * Used to store all entities in form of StudentRecords
     */
    private final List<StudentRecord> records;
    /**
     * Index for jmbag attribute to make O(1) retrieval by jmbag possible
     */
    private final Map<String, StudentRecord> jmbagIndex;

    /**
     * Main constructor fo initialising the database. It takes a list of strings that represent
     * database entries. String must have attributes jmbag, lastName, firstName, finalGrade in that order
     * separated by whitespace. List must also not contain duplicate entries with same jmbag or entries with
     * invalid grades (grades must be between 1 and 5 inclusive)
     * @param entities list of string representations of database entries
     * @throws StudentDatabaseException if entries contains multiple entries with same jmbag, or invalid grades
     */
    public StudentDatabase(List<String> entities) {
        records = new ArrayList<>();
        jmbagIndex = new HashMap<>();

        for(String entry: entities) {
            StudentRecord newRecord;

            try {
                newRecord = parseEntry(entry);
            } catch (Exception e) {
                throw new StudentDatabaseException(e.getMessage());
            }

            if (jmbagIndex.put(newRecord.getJmbag(), newRecord) != null) {
                throw new StudentDatabaseException("Database can't contain multiple entries with same jmbag.");
            }

            records.add(newRecord);
        }
    }

    /**
     * Retrieves a student record with jmbag from database in O(1) time. If there is no
     * student with jmbag in database <code>null</code> is returned.
     * @param jmbag of record to be retrieved
     * @return student record with jmbag if present, <code>null</code> otherwise
     */
    public StudentRecord forJMBAG(String jmbag) {
        return jmbagIndex.get(jmbag);
    }

    /**
     * Used to filter student records by a certain criteria
     * @param filter describes the wanted filter criteria
     * @return a list of all the records that satisfy the filter
     */
    public List<StudentRecord> filter(IFilter filter) {
        return records.stream().filter(filter::accepts).collect(Collectors.toList());
    }

    /**
     * Helper method used to parse string representations of StudentRecord.
     * Entry must have attributes jmbag, lastName, firstName, finalGrade in that order
     * separated by whitespace.
     * @param entry string representations of StudentRecord
     * @return Student record object constructed from entry
     * @throws StudentDatabaseException if grade isn't between 1 and 5
     */
    private StudentRecord parseEntry(String entry) {
        String jmbag;
        String lastName;
        String firstName;
        short  finalGrade;

        try {
            String[] fields = entry.split("\t");

            jmbag      = fields[0];
            lastName   = fields[1];
            firstName  = fields[2];
            finalGrade = Short.parseShort(fields[3]);

        } catch (IndexOutOfBoundsException ex) {
            throw new StudentDatabaseException(ex.getMessage());
        }

        return new StudentRecord(jmbag, lastName, firstName, finalGrade);
    }
}
