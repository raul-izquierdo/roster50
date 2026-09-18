package es.uniovi.raul.roster50.core;

import java.io.*;
import java.util.List;

import es.uniovi.raul.roster50.roster.parser.RosterEntry;

public class RosterDiffReporter {

    public static void reportDiffs(RosterDiff diff, String outputFilePath)
            throws FileNotFoundException {

        reportEntriesToAdd(diff.newEntries(), outputFilePath);
        reportEntriesToChange(diff.changed());
        reportEntriesToDelete(diff.oldEntries());
    }

    public static void reportEntriesToAdd(List<RosterEntry> newEntries, String outputFilePath)
            throws FileNotFoundException {

        System.out.printf("""

                ## Entries that must be added to the roster

                    %d students need to be added to the roster.
                    Import the file '%s' into Classroom 50.

                """, newEntries.size(), outputFilePath);

        try (PrintStream out = new PrintStream(outputFilePath)) {
            reportEntriesToAdd(newEntries, out);
        }

    }

    public static void reportEntriesToAdd(List<RosterEntry> newEntries, PrintStream out) {

        // Write the header for the CSV file
        out.println("email, first_name, section");

        newEntries.forEach(entry -> writeCsvEntry(entry, out));

    }

    private static void writeCsvEntry(RosterEntry entry, PrintStream out) {
        String email = entry.email();
        String name = entry.firstName().orElse("");
        String group = entry.section().orElse("");

        // Always add quotes around the name to handle cases where the name contains commas
        name = "\"" + name + "\"";

        out.printf("%s,%s,%s%n", email, name, group);
    }

    private static void reportEntriesToChange(List<RosterEntry> changed) {
        System.out.printf("""

                ## Entries that require group updates

                    %d students need their group updated.

                """, changed.size());

        changed.forEach(entry -> System.out.printf("\t%s\t(%s) -> %s%n",
                entry.firstName().orElse("<no name>"),
                entry.email(),
                entry.section().orElse("<no section>")));
    }

    private static void reportEntriesToDelete(List<RosterEntry> deleted) {
        System.out.printf("""

                ## Entries that must be removed from the roster

                    %d students must be removed from the roster.

                """, deleted.size());

        deleted.forEach(entry -> System.out.printf("\t%s (%s)%n",
                entry.firstName().orElse("<no name>"),
                entry.email()));
    }
}
