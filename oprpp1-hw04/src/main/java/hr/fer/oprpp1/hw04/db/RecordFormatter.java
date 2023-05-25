package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class used to format StudentRecords for better data display
 */
public class RecordFormatter {

    /**
     * Number of whitespace added to column content.
     * Half of padding is added to the front of the content
     * and the other half to the end.
     */
    private static final int padding = 2;

    /**
     * Formats StudentRecord in form:
     * +=======+==========+===========+============+
     * | jmbag | lastName | firstName | finalGrade |
     * +=======+==========+===========+============+
     * @param records to be formatted
     * @return list of rows in formatted pattern with beginning and end separators
     */
    public static List<String> format(List<StudentRecord> records) {
        int maxJmbagLen      = 0;
        int maxLastNameLen   = 0;
        int maxFirstNameLen  = 0;
        int maxFinalGradeLen = 1;                                       //Always 1
        List<String> output  = new ArrayList<>();

        for (StudentRecord record: records) {                           //Find maximum lengths
            if (maxJmbagLen < record.getJmbag().length()) {
                maxJmbagLen = record.getJmbag().length();
            }
            if (maxLastNameLen < record.getLastName().length()) {
                maxLastNameLen = record.getLastName().length();
            }
            if (maxFirstNameLen < record.getFirstName().length()) {
                maxFirstNameLen = record.getFirstName().length();
            }
        }

        int jmbagLen = maxJmbagLen + padding;
        int lastNameLen = maxLastNameLen + padding;
        int firstNameLen = maxFirstNameLen + padding;
        int finalGradeLen = maxFinalGradeLen + padding;

        String separator = generateSeparator(jmbagLen, lastNameLen, firstNameLen, finalGradeLen);

        output.add(separator);
        for (StudentRecord record: records) {
            String jmbag      = addPadding(record.getJmbag(), jmbagLen);
            String lastName   = addPadding(record.getLastName(), lastNameLen);
            String firstName  = addPadding(record.getFirstName(), firstNameLen);
            String finalGrade = addPadding(Short.toString(record.getFinalGrade()), finalGradeLen);

            output.add("|" + jmbag + "|" + lastName + "|" + firstName + "|" + finalGrade + "|");
        }
        output.add(separator);

        return output;
    }

    /**
     * Formats StudentRecord in form:
     * +=======+==========+===========+============+
     * | jmbag | lastName | firstName | finalGrade |
     * +=======+==========+===========+============+
     * @param record to be formatted
     * @return formatted record
     */
    public static List<String> format(StudentRecord record) {
        return format(List.of(record));
    }

    /**
     * Helper method used to generate beginning and end separator
     * @param widths of different columns
     * @return separator
     */
    private static String generateSeparator(int...widths) {
        StringBuilder sb = new StringBuilder("+");

        for (int width: widths) {
            sb.append(String.join("", Collections.nCopies(width, "="))).append("+");
        }

        return sb.toString();
    }

    /**
     * Adds whitespace to str until it is of length len with padding.
     * If str length is greater than len - padding then str + padding
     * is returned
     * @param str to be padded
     * @param len desired length
     * @return padded str
     */
    private static String addPadding(String str, int len) {
        String paddingFront = String.join("", Collections.nCopies(padding / 2, " "));
        String paddingEnd   = String.join("", Collections.nCopies(padding / 2, " "));

        if (str.length() >= len - padding) return paddingFront + str + paddingEnd;

        return paddingFront +
                str +
                String.join("", Collections.nCopies(len - padding - str.length(), " ")) +
                paddingEnd;
    }
}
