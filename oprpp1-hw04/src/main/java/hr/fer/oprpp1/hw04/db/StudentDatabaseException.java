package hr.fer.oprpp1.hw04.db;

/**
 * Used for throwing StudentDatabase exceptions
 */
public class StudentDatabaseException extends RuntimeException {

    public StudentDatabaseException() {
    }

    public StudentDatabaseException(String message) {
        super(message);
    }
}
