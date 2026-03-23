package org.Number8Kursova;

import org.Number8Kursova.Manager.DeaneryManager;
import org.Number8Kursova.Manager.Student;
import org.Number8Kursova.uielements.StudentsDialog.StudentsPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Basic unit tests for StudentsPanel.
 *
 * These tests verify that:
 * - panel is created successfully
 * - added students are displayed in the table
 *
 * The tests validate panel state after initialization.
 */
public class TestStudentsPanel {

    private DeaneryManager manager;

    @BeforeEach
    void setUp() {
        TestDatabaseHelper.resetDatabase();
        manager = new DeaneryManager();
    }

    /**
     * Testcase: Should create StudentsPanel successfully.
     *
     * Steps:
     * 1. Create StudentsPanel.
     *
     * Result state:
     * - Panel is not null.
     * - Panel uses BorderLayout.
     */
    @Test
    void testStudentsPanel_should_be_created_successfully() throws Exception {

        // Arrange
        final StudentsPanel[] panelHolder = new StudentsPanel[1];

        // Act
        SwingUtilities.invokeAndWait(() ->
                panelHolder[0] = new StudentsPanel(manager)
        );

        // Assert
        assertNotNull(panelHolder[0]);
        assertEquals(BorderLayout.class, panelHolder[0].getLayout().getClass());
    }

    /**
     * Testcase: Should display added students in table.
     *
     * Steps:
     * 1. Add two students to the system.
     * 2. Create StudentsPanel.
     * 3. Read row count from the table.
     *
     * Result state:
     * - Table contains two rows.
     */
    @Test
    void testStudentsPanel_should_display_added_students() throws Exception {

        // Arrange
        manager.addStudent(new Student(
                manager.generateStudentId(),
                "Іван",
                "Петренко",
                "Олегович",
                18,
                "Чоловіча",
                ""
        ));

        manager.addStudent(new Student(
                manager.generateStudentId(),
                "Марія",
                "Коваленко",
                "Іванівна",
                19,
                "Жіноча",
                ""
        ));

        final StudentsPanel[] panelHolder = new StudentsPanel[1];

        SwingUtilities.invokeAndWait(() ->
                panelHolder[0] = new StudentsPanel(manager)
        );

        JTable table = extractTable(panelHolder[0], "studentsTable");

        // Act
        int rowCount = table.getRowCount();

        // Assert
        assertEquals(2, rowCount);
    }

    private JTable extractTable(Object target, String fieldName) {
        try {
            Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return (JTable) field.get(target);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}