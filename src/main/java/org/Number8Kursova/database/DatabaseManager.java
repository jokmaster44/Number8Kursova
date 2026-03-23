package org.Number8Kursova.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Provides connection to SQLite database.
 */
public class DatabaseManager {

    private static final String URL = "jdbc:sqlite:deanery.db";

    /**
     * Opens a connection to the database file.
     *
     * @return SQL connection
     * @throws SQLException if connection cannot be created
     */
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}