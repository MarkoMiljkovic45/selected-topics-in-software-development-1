package hr.fer.oprpp1.hw04.db;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

/**
 * Main class
 */
public class StudentDB {

    public static void main(String[] args) {
        List<String> studentRecordsRaw = List.of(readFromResources("database.txt").split("\r\n"));
        StudentDatabase database = new StudentDatabase(studentRecordsRaw);

        Scanner sc = new Scanner(System.in);
        while(sc.hasNext()) {
            String command = sc.nextLine();

            try {
                QueryParser parser = new QueryParser(command);
                int recordsSelected = 0;

                if (parser.isDirectQuery()) {
                    System.out.println("Using index for record retrieval.");
                    StudentRecord record = database.forJMBAG(parser.getQueriedJMBAG());
                    if (record != null) {
                        RecordFormatter.format(record).forEach(System.out::println);
                        recordsSelected = 1;
                    }
                } else {
                    List<StudentRecord> records = database.filter(new QueryFilter(parser.getQuery()));
                    recordsSelected = records.size();
                    if (recordsSelected > 0) {
                        RecordFormatter.format(records).forEach(System.out::println);
                    }
                }

                System.out.println("Records selected: " + recordsSelected);
            } catch (QueryExitException exit) {
                System.out.println("Goodbye!");
                System.exit(0);
            } catch (RuntimeException ex) {
                System.out.println(ex.getMessage());
            }

            System.out.println();
        }
    }

    /**
     * Helper method to read resources/fileName file
     * @param fileName to be read
     * @return String containing the entire text from fileName
     */
    private static String readFromResources(String fileName) {
        try(InputStream is = StudentDB.class.getClassLoader().getResourceAsStream(fileName)) {
            if(is==null) throw new RuntimeException("File extra/" + fileName + " is unavailable.");
            byte[] data = is.readAllBytes();
            return new String(data, StandardCharsets.UTF_8);
        } catch(IOException ex) {
            throw new RuntimeException("Error reading file.", ex);
        }
    }
}
