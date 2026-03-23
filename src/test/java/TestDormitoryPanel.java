package org.Number8Kursova;

import org.Number8Kursova.Manager.ClassRoom;
import org.Number8Kursova.Manager.DeaneryManager;
import org.Number8Kursova.Manager.Student;
import org.Number8Kursova.uielements.DormitoryPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Basic unit tests for DormitoryPanel.
 *
 * These tests verify that:
 * - panel is created successfully
 * - added rooms are displayed in the table
 * - room occupancy is displayed correctly after settlement
 */
public class TestDormitoryPanel {

    private DeaneryManager manager;

    @BeforeEach
    void setUp() {
        TestDatabaseHelper.resetDatabase();
        manager = new DeaneryManager();
    }

    /**
     * Testcase: Should create DormitoryPanel successfully.
     *
     * Steps:
     * 1. Create DormitoryPanel.
     *
     * Result state:
     * - Panel is created without errors.
     */
    @Test
    void testDormitoryPanel_should_be_created_successfully() throws Exception {

        // Arrange
        final DormitoryPanel[] panelHolder = new DormitoryPanel[1];

        // Act
        SwingUtilities.invokeAndWait(() ->
                panelHolder[0] = new DormitoryPanel(manager)
        );

        // Assert
        assertNotNull(panelHolder[0]);
    }

    /**
     * Testcase: Should display added rooms in table.
     *
     * Steps:
     * 1. Add two rooms.
     * 2. Create DormitoryPanel.
     * 3. Read row count from the table.
     *
     * Result state:
     * - Table contains two rooms.
     */
    @Test
    void testDormitoryPanel_should_display_added_rooms() throws Exception {

        // Arrange
        manager.addRoom(new ClassRoom(101, 3));
        manager.addRoom(new ClassRoom(102, 2));

        final DormitoryPanel[] panelHolder = new DormitoryPanel[1];

        SwingUtilities.invokeAndWait(() ->
                panelHolder[0] = new DormitoryPanel(manager)
        );

        JTable table = extractTable(panelHolder[0], "roomsTable");

        // Act
        int rowCount = table.getRowCount();

        // Assert
        assertEquals(2, rowCount);
    }

    /**
     * Testcase: Should display room occupancy after student settlement.
     *
     * Steps:
     * 1. Add student.
     * 2. Add room with capacity 2.
     * 3. Settle student into the room.
     * 4. Create DormitoryPanel.
     * 5. Read occupied and free places.
     *
     * Result state:
     * - Occupied places equals 1.
     * - Free places equals 1.
     */
    @Test
    void testDormitoryPanel_should_display_room_occupancy_after_settlement() throws Exception {

        // Arrange
        Student student = new Student(
                manager.generateStudentId(),
                "Іван",
                "Петренко",
                "Олегович",
                18,
                "Чоловіча",
                ""
        );
        manager.addStudent(student);
        manager.addRoom(new ClassRoom(101, 2));
        manager.settleStudent(student.getId(), 101);

        final DormitoryPanel[] panelHolder = new DormitoryPanel[1];

        SwingUtilities.invokeAndWait(() ->
                panelHolder[0] = new DormitoryPanel(manager)
        );

        JTable table = extractTable(panelHolder[0], "roomsTable");

        // Act
        int occupied = (int) table.getValueAt(0, 2);
        int free = (int) table.getValueAt(0, 3);

        // Assert
        assertEquals(1, occupied);
        assertEquals(1, free);
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