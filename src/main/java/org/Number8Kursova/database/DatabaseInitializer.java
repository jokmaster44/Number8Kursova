package org.Number8Kursova.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Initializes database structure for the application.
 *
 * Creates required tables if they do not exist.
 */
public class DatabaseInitializer {

    /**
     * Creates all required tables.
     */
    public static void initialize() {
        String createStudentsTable = """
                CREATE TABLE IF NOT EXISTS students (
                    id INTEGER PRIMARY KEY,
                    first_name TEXT NOT NULL,
                    last_name TEXT NOT NULL,
                    middle_name TEXT,
                    age INTEGER,
                    gender TEXT,
                    group_name TEXT,
                    living_in_dormitory INTEGER DEFAULT 0,
                    room_number INTEGER DEFAULT -1
                );
                """;

        String createGroupsTable = """
                CREATE TABLE IF NOT EXISTS student_groups (
                    name TEXT PRIMARY KEY,
                    course INTEGER NOT NULL,
                    specialty TEXT NOT NULL
                );
                """;

        String createRoomsTable = """
                CREATE TABLE IF NOT EXISTS rooms (
                    room_number INTEGER PRIMARY KEY,
                    capacity INTEGER NOT NULL
                );
                """;

        try (Connection connection = DatabaseManager.connect();
             Statement statement = connection.createStatement()) {

            statement.execute(createStudentsTable);
            statement.execute(createGroupsTable);
            statement.execute(createRoomsTable);

            System.out.println("Database tables are ready.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}