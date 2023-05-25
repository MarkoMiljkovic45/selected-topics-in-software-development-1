package hr.fer.oprpp1.hw04.db;

public class QueryExitException extends RuntimeException {

    public QueryExitException() {
    }

    public QueryExitException(String message) {
        super(message);
    }
}
