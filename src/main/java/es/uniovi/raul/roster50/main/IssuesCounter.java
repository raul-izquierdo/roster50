package es.uniovi.raul.roster50.main;

import es.uniovi.raul.roster50.roster.parser.IssuesTracker;

public final class IssuesCounter implements IssuesTracker {

    private int missingEmailCount = 0;
    private int nonStudentCount = 0;

    @Override
    public void notifyMissingEmail(long row) {
        missingEmailCount++;
    }

    @Override
    public void notifyNonStudent(long row, String role) {
        nonStudentCount++;
    }

    public int getMissingEmailCount() {
        return missingEmailCount;
    }

    public int getNonStudentCount() {
        return nonStudentCount;
    }

}
