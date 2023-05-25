package hr.fer.oprpp1.hw04.db;

/**
 * Class that provides implementations of IFieldValueGetter
 */
public class FieldValueGetters {

    public static final IFieldValueGetter FIRST_NAME;
    public static final IFieldValueGetter LAST_NAME;
    public static final IFieldValueGetter JMBAG;

    static {
        FIRST_NAME = StudentRecord::getFirstName;
        LAST_NAME  = StudentRecord::getLastName;
        JMBAG      = StudentRecord::getJmbag;
    }
}
