package org.Number8Kursova;

import org.Number8Kursova.database.DatabaseInitializer;
import org.Number8Kursova.database.DatabaseManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


public class TestDatabaseHelper {

    public static void resetDatabase() {
        DatabaseInitializer.initialize();

        try (Connection connection = DatabaseManager.connect();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate("DELETE FROM students");
            statement.executeUpdate("DELETE FROM student_groups");
            statement.executeUpdate("DELETE FROM rooms");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}