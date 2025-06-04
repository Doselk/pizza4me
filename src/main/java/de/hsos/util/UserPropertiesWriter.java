package de.hsos.util;

import java.io.*;
        import java.nio.file.*;
        import java.util.Properties;

public class UserPropertiesWriter {

    private static final Path USERS_PATH = Paths.get("src/main/resources/users.properties");
    private static final Path ROLES_PATH = Paths.get("src/main/resources/roles.properties");

    public static synchronized void addUser(String username, String password, String role) throws IOException {
        addToPropertiesFile(USERS_PATH, username, password);
        addToPropertiesFile(ROLES_PATH, username, role);
    }

    private static void addToPropertiesFile(Path filePath, String key, String value) throws IOException {
        Properties props = new Properties();

        if (Files.exists(filePath)) {
            try (InputStream in = Files.newInputStream(filePath)) {
                props.load(in);
            }
        }

        // Neuen Eintrag hinzufügen
        props.setProperty(key, value);

        // Datei aktualisieren
        try (OutputStream out = Files.newOutputStream(filePath)) {
            props.store(out, null);
        }
    }
}
