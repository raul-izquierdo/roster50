package es.uniovi.raul.roster50.roster.parser;

import java.util.*;

public record RosterEntry(String email, Optional<String> firstName, Optional<String> section) {

    public RosterEntry {
        Objects.requireNonNull(email);
        Objects.requireNonNull(firstName);
        Objects.requireNonNull(section);
    }
}
