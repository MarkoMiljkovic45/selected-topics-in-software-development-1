package hr.fer.oprpp1.hw04.db;

import java.util.Objects;

/**
 * Class used to represent Student entity from database
 */
public class StudentRecord {

    /**
     * A unique student identifier
     * Can't be null
     */
    private final String jmbag;
    /**
     * Student last name
     */
    private final String lastName;
    /**
     * Student first name
     */
    private final String firstName;
    /**
     * Student final grade
     */
    private final short finalGrade;

    public StudentRecord(String jmbag, String lastName, String firstName, short finalGrade) {
        if (jmbag == null) throw new NullPointerException("jmbag can't be null");
        if (finalGrade < 1 || finalGrade > 5) throw new IllegalArgumentException("Grade must be between 1 and 5.");
        this.jmbag = jmbag;
        this.lastName = lastName;
        this.firstName = firstName;
        this.finalGrade = finalGrade;
    }

    public StudentRecord(String jmbag, String lastName, String firstName, int finalGrade) {
        this(jmbag, lastName, firstName, (short) finalGrade);
    }

    public String getJmbag() {
        return jmbag;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public short getFinalGrade() {
        return finalGrade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentRecord that = (StudentRecord) o;
        return jmbag.equals(that.jmbag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jmbag);
    }
}
