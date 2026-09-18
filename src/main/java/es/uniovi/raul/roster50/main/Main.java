package es.uniovi.raul.roster50.main;

import static es.uniovi.raul.roster50.roster.net.RosterDownloader.*;
import static es.uniovi.raul.roster50.roster.parser.RosterParser.*;

import java.io.*;
import java.util.*;

import es.uniovi.raul.roster50.cli.*;
import es.uniovi.raul.roster50.core.Core;
import es.uniovi.raul.roster50.roster.net.RosterDownloader.RosterDownloadException;
import es.uniovi.raul.roster50.roster.parser.RosterEntry;

/**
 * Entry point for the app.
 */
public class Main {

    public static void main(String[] args) {

        Optional<Arguments> argumentsOpt = ArgumentsParser.parse(args);

        if (argumentsOpt.isEmpty())
            System.exit(Core.ERROR);

        int exitCode;
        try {
            exitCode = loadAndRun(argumentsOpt.get());

        } catch (Exception e) {
            System.err.printf("%n[Error] %s%n", e.getMessage());
            exitCode = Core.ERROR;
        }

        System.exit(exitCode);
    }

    private static int loadAndRun(Arguments arguments)
            throws IOException, RosterDownloadException {

        // Load...
        List<RosterEntry> oldRoster = loadRoster(getOldRosterReader(arguments));

        System.out.printf("%n## Reading new roster file: '%s'%n", arguments.newRosterFile);
        List<RosterEntry> newRoster = loadRoster(new FileReader(arguments.newRosterFile));

        // ... and Run
        return Core.run(oldRoster, newRoster, arguments.newStudentsFile);

    }

    // Determine the source of the old roster: either a local file or a GitHub repository.
    private static Reader getOldRosterReader(Arguments arguments) throws IOException, RosterDownloadException {

        if (arguments.oldRosterFile != null) {
            System.out.printf("%n## Using local roster file: %s%n", arguments.oldRosterFile);
            return new FileReader(arguments.oldRosterFile);
        }

        System.out.printf(
                "%n## No local roster file provided. Proceeding to load roster from GitHub repository '%s' in organization '%s'...%n",
                arguments.classroom, arguments.classroomOrg);

        String rosterCsv = downloadRoster(arguments.token, arguments.classroomOrg, arguments.classroom);
        return new StringReader(rosterCsv);
    }

    private static List<RosterEntry> loadRoster(Reader reader) throws IOException {

        var issuesTracker = new IssuesCounter();
        List<RosterEntry> roster = parseRoster(reader, issuesTracker);

        // Report issues found during parsing
        if (issuesTracker.getMissingEmailCount() > 0)
            System.out.printf("    %d entries skipped because they are missing email addresses.%n",
                    issuesTracker.getMissingEmailCount());

        if (issuesTracker.getNonStudentCount() > 1) // Yo siempre apareceré como "teacher"
            System.out.printf("    %d entries skipped because they are not students.%n",
                    issuesTracker.getNonStudentCount());

        int studentsWithoutFirstName = (int) roster.stream().filter(entry -> entry.firstName().isEmpty()).count();
        if (studentsWithoutFirstName > 0)
            System.out.printf("    %d entries accepted but have no first name.%n", studentsWithoutFirstName);

        return roster;
    }

}
