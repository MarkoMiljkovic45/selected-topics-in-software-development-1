package hr.fer.oprpp1.hw04.db;

/**
 * Used to get StudentRecord field values
 */
@FunctionalInterface
public interface IFieldValueGetter {
    /**
     * Method used to get a value from a StudentRecord field
     * @param record record to read the field from
     * @return value of field
     */
    String get(StudentRecord record);
}
