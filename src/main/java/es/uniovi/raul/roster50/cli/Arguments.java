package es.uniovi.raul.roster50.cli;

import picocli.CommandLine.*;

// CHECKSTYLE:OFF

@Command(name = "roster50", showDefaultValues = true, mixinStandardHelpOptions = true, usageHelpAutoWidth = true, description = Messages.DESCRIPTION, customSynopsis = Messages.USAGE, footer = Messages.CREDITS, versionProvider = PomVersionReader.class)
public class Arguments {

    @Parameters(defaultValue = "new-roster.csv", description = "The csv file with the new roster.")
    public String newRosterFile;

    @Option(names = "-n", defaultValue = "students-to-add.csv", description = "The output file listing new students that must be added manually.")
    public String newStudentsFile;

    @Option(names = "-r", description = "Use this local roster file instead of downloading the roster from the Classroom 50 repository.")
    public String oldRosterFile;

    @Option(names = "-t", description = "GitHub API access token. If not provided, it will try to read from the GITHUB_TOKEN environment variable or from a '.env' file.")
    public String token;

    @Option(names = "-o", description = "GitHub organization name associated with the classroom. If not provided, it will try to read from the CLASSROOM_ORG environment variable or from a '.env' file.")
    public String classroomOrg;

    @Option(names = "-c", description = "GitHub classroom name. If not provided, it will try to read from the CLASSROOM_NAME environment variable or from a '.env' file.")
    public String classroom;
}

class Messages {
    static final String DESCRIPTION = """

            This tool helps you identify which students need to be added, updated, or removed from a Classroom 50 roster.

            For more information, visit: https://github.com/raul-izquierdo/roster50
            """;

    static final String USAGE = "\n\tjava -jar roster50.jar [OPTIONS] [<newRosterFile>]\n";

    static final String CREDITS = """

            Escuela de Ingenieria Informatica. Universidad de Oviedo.
            Raúl Izquierdo Castanedo (raul@uniovi.es)
            """;

}

class PomVersionReader implements IVersionProvider {
    public String[] getVersion() throws Exception {
        return new String[] { Arguments.class.getPackage().getImplementationVersion() };
    }
}
