package es.uniovi.raul.roster50.roster.parser;

/**
 * Interface for tracking issues found during validation of student data.
 * This interface is used in testing to collect issues, and in production to log them to the console.
 */
public interface IssuesTracker {

    void notifyMissingEmail(long row);

    void notifyNonStudent(long row, String role);

}
