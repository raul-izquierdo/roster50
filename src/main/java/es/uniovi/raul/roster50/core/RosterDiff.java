package es.uniovi.raul.roster50.core;

import java.util.List;

import es.uniovi.raul.roster50.roster.parser.RosterEntry;

/**
 * A record representing the differences between two rosters.
 *
 * @param changed List of entries in the new roster that exist in both rosters but have different sections.
 * @param newEntries List of entries that are new in the new roster.
 * @param oldEntries List of entries in the old roster that are no longer present in the new roster.
 */
public record RosterDiff(
        List<RosterEntry> changed,
        List<RosterEntry> newEntries,
        List<RosterEntry> oldEntries) {

    public boolean hasChanges() {
        return !changed.isEmpty() || !newEntries.isEmpty() || !oldEntries.isEmpty();
    }

}
