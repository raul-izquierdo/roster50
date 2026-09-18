package es.uniovi.raul.roster50.core;

import java.util.List;

import es.uniovi.raul.roster50.roster.parser.RosterEntry;

/**
 * Compares two rosters and identifies the differences between them.
 */
public class RostersComparator {

    public static RosterDiff compareRosters(List<RosterEntry> oldRoster, List<RosterEntry> newRoster) {

        // Changed entries: those that exist in both rosters but have different section
        List<RosterEntry> changed = newRoster.stream()
                .filter(newEntry -> oldRoster.stream()
                        .anyMatch(oldEntry -> oldEntry.email().equalsIgnoreCase(newEntry.email())
                                && !isSameSection(newEntry, oldEntry)))
                .toList();

        // New entries: those that exist in the new roster but not in the old roster
        List<RosterEntry> newEntries = newRoster.stream()
                .filter(newEntry -> oldRoster.stream()
                        .noneMatch(oldEntry -> oldEntry.email().equalsIgnoreCase(newEntry.email())))
                .toList();

        // Old entries: those that exist in the old roster but not in the new roster (must be removed)
        List<RosterEntry> oldEntries = oldRoster.stream()
                .filter(oldEntry -> newRoster.stream()
                        .noneMatch(newEntry -> newEntry.email().equalsIgnoreCase(oldEntry.email())))
                .toList();

        return new RosterDiff(changed, newEntries, oldEntries);
    }

    private static boolean isSameSection(RosterEntry newEntry, RosterEntry oldEntry) {

        // If both sections are empty, they are considered the same
        if (oldEntry.section().isEmpty() && newEntry.section().isEmpty()) {
            return true;
        }

        // If one of the sections is empty and the other is not, they are considered different
        if (oldEntry.section().isEmpty() || newEntry.section().isEmpty()) {
            return false;
        }

        // Both sections are present, compare them case-insensitively
        return oldEntry.section().get().equalsIgnoreCase(newEntry.section().get());
    }
}
