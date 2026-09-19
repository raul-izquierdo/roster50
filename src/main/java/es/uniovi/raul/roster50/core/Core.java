package es.uniovi.raul.roster50.core;

import java.io.FileNotFoundException;
import java.util.List;

import es.uniovi.raul.roster50.roster.parser.RosterEntry;

public class Core {

    public static final int OK = 0;
    public static final int NO_CHANGES = 2; // The roster was up to date, no changes were made
    public static final int ERROR = 2;

    public static int run(List<RosterEntry> oldRoster, List<RosterEntry> newRoster, String newStudentsFile)
            throws FileNotFoundException {

        var diff = RostersComparator.compareRosters(oldRoster, newRoster);

        if (!diff.hasChanges()) {
            System.out.println("The roster was up to date, no changes were made.");
            return NO_CHANGES;
        }

        RosterDiffReporter.reportDiffs(diff, newStudentsFile);

        return OK;
    }

}
