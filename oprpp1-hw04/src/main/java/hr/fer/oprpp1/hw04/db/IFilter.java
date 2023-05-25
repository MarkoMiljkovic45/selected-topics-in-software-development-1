package hr.fer.oprpp1.hw04.db;

/**
 * Used for filtering database entities
 */
@FunctionalInterface
public interface IFilter {
    /**
     * Used for filtering StudentRecords by a specific criteria.
     * @param record to be tested
     * @return true if record fits filter criteria, false otherwise
     */
    boolean accepts(StudentRecord record);
}
