package es.uniovi.raul.roster50.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import es.uniovi.raul.roster50.roster.parser.RosterEntry;

class RostersComparatorTest {

    @Test
    void compareRostersReturnsEmptyDiffWhenBothRostersAreEmpty() {
        var oldRoster = List.<RosterEntry>of();
        var newRoster = List.<RosterEntry>of();

        var diff = RostersComparator.compareRosters(oldRoster, newRoster);

        assertEquals(List.of(), diff.changed());
        assertEquals(List.of(), diff.newEntries());
        assertEquals(List.of(), diff.oldEntries());
    }

    @Test
    void compareRostersTreatsSameEmailAndSameSectionAsUnchanged() {
        var oldRoster = List.of(
                entry("alice@example.com", "A", "Alice"),
                entry("bob@example.com", "B", "Bob"));
        var newRoster = List.of(
                entry("alice@example.com", "A", "Alice Smith"),
                entry("bob@example.com", "B", "Bob"));

        var diff = RostersComparator.compareRosters(oldRoster, newRoster);

        assertEquals(List.of(), diff.changed());
        assertEquals(List.of(), diff.newEntries());
        assertEquals(List.of(), diff.oldEntries());
    }

    @Test
    void compareRostersDetectsChangedSectionsByEmail() {
        var oldRoster = List.of(
                entry("alice@example.com", "A", "Alice"),
                entry("bob@example.com", "B", "Bob"),
                entry("charlie@example.com", "C", "Charlie"));
        var newRoster = List.of(
                entry("alice@example.com", "B", "Alice"),
                entry("bob@example.com", "B", "Bob"),
                entry("diana@example.com", "D", "Diana"));

        var diff = RostersComparator.compareRosters(oldRoster, newRoster);

        assertEquals(List.of(entry("alice@example.com", "B", "Alice")), diff.changed());
        assertEquals(List.of(entry("diana@example.com", "D", "Diana")), diff.newEntries());
        assertEquals(List.of(entry("charlie@example.com", "C", "Charlie")), diff.oldEntries());
    }

    @Test
    void compareRostersDetectsOnlyNewEntriesWhenNoOldEntriesMatch() {
        var oldRoster = List.of(
                entry("alice@example.com", "A", "Alice"));
        var newRoster = List.of(
                entry("bob@example.com", "B", "Bob"),
                entry("charlie@example.com", "C", "Charlie"));

        var diff = RostersComparator.compareRosters(oldRoster, newRoster);

        assertEquals(List.of(), diff.changed());
        assertEquals(List.of(
                entry("bob@example.com", "B", "Bob"),
                entry("charlie@example.com", "C", "Charlie")), diff.newEntries());
        assertEquals(List.of(entry("alice@example.com", "A", "Alice")), diff.oldEntries());
    }

    @Test
    void compareRostersHandlesMixedChangesWithDeletedAndAddedEntries() {
        var oldRoster = List.of(
                entry("alice@example.com", "A", "Alice"),
                entry("bob@example.com", "B", "Bob"),
                entry("charlie@example.com", "C", "Charlie"),
                entry("diana@example.com", "D", "Diana"));
        var newRoster = List.of(
                entry("alice@example.com", "A", "Alice"),
                entry("bob@example.com", "C", "Bob"),
                entry("charlie@example.com", "C", "Charlie"),
                entry("erin@example.com", "E", "Erin"));

        var diff = RostersComparator.compareRosters(oldRoster, newRoster);

        assertEquals(List.of(entry("bob@example.com", "C", "Bob")), diff.changed());
        assertEquals(List.of(entry("erin@example.com", "E", "Erin")), diff.newEntries());
        assertEquals(List.of(entry("diana@example.com", "D", "Diana")), diff.oldEntries());
    }

    private static RosterEntry entry(String email, String section, String firstName) {
        return new RosterEntry(email, Optional.of(firstName), Optional.of(section));
    }
}
