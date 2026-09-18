package es.uniovi.raul.roster50.roster.parser;

import java.io.*;
import java.util.*;

import org.apache.commons.csv.*;

public class RosterParser {

    public static List<RosterEntry> parseRoster(Reader reader, IssuesTracker issuesTracker)
            throws IOException {

        List<RosterEntry> entries = new ArrayList<>();

        try (CSVParser parser = new CSVParser(reader,
                CSVFormat.DEFAULT.builder().setHeader().setSkipHeaderRecord(true).build())) {

            for (CSVRecord csvRecord : parser) {

                if (skipEntry(csvRecord, parser.getCurrentLineNumber(), issuesTracker))
                    continue; // ignore invalid entries

                String email = findValue(csvRecord, "email").get(); // required for students
                Optional<String> firstName = findValue(csvRecord, "first_name");
                Optional<String> section = findValue(csvRecord, "section");

                var rosterEntry = new RosterEntry(email, firstName, section);

                entries.add(rosterEntry);
            }
        }
        return entries;
    }

    // Skip entries that are not students or have missing email (required for students)
    private static boolean skipEntry(CSVRecord csvRecord, long currentLineNumber, IssuesTracker issuesTracker) {

        // Skip entries that are not students (e.g., teachers, admins, etc.). Blank roles are considered as students.
        String role = findValue(csvRecord, "role").orElse("student");
        if (!"student".equals(role)) {
            issuesTracker.notifyNonStudent(currentLineNumber, role);
            return true;
        }

        // Skip entries with missing email (required for students)
        if (findValue(csvRecord, "email").isEmpty()) {
            issuesTracker.notifyMissingEmail(currentLineNumber);
            return true;
        }

        return false;
    }

    /**
     * Returns the value of the specified column in the CSV record. Used with optional columns. If the value is blank, returns an empty Optional.
     */
    private static Optional<String> findValue(CSVRecord csvRecord, String columnName) {
        try {
            String value = csvRecord.get(columnName);

            if (value == null || value.isBlank())
                return Optional.empty();

            return Optional.of(value);

        } catch (IllegalArgumentException e) { // column not found
            return Optional.empty();
        }
    }

}
