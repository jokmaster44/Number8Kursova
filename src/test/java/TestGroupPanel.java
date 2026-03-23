package org.Number8Kursova;

import org.Number8Kursova.Manager.DeaneryManager;
import org.Number8Kursova.Manager.Student;
import org.Number8Kursova.Manager.StudentGroup;
import org.Number8Kursova.uielements.GroupsDialog.GroupsPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Basic unit tests for GroupsPanel.
 *
 * These tests verify that:
 * - panel is created successfully
 * - added groups are displayed in the table
 * - student count in group is shown correctly
 */
public class TestGroupPanel {

    private DeaneryManager manager;

    @BeforeEach
    void setUp() {
        TestDatabaseHelper.resetDatabase();
        manager = new DeaneryManager();
    }

    /**
     * Testcase: Should create GroupsPanel successfully.
     *
     * Steps:
     * 1. Create GroupsPanel.
     *
     * Result state:
     * - Panel is created without errors.
     */
    @Test
    void testGroupsPanel_should_be_created_successfully() throws Exception {

        // Arrange
        final GroupsPanel[] panelHolder = new GroupsPanel[1];

        // Act
        SwingUtilities.invokeAndWait(() ->
                panelHolder[0] = new GroupsPanel(manager)
        );

        // Assert
        assertNotNull(panelHolder[0]);
    }

    /**
     * Testcase: Should display added groups in table.
     *
     * Steps:
     * 1. Add two groups.
     * 2. Create GroupsPanel.
     * 3. Read row count from the table.
     *
     * Result state:
     * - Table contains two groups.
     */
    @Test
    void testGroupsPanel_should_display_added_groups() throws Exception {

        // Arrange
        manager.addGroup(new StudentGroup("ІПЗ-21", 2, "ІПЗ"));
        manager.addGroup(new StudentGroup("КН-22", 3, "КН"));

        final GroupsPanel[] panelHolder = new GroupsPanel[1];

        SwingUtilities.invokeAndWait(() ->
                panelHolder[0] = new GroupsPanel(manager)
        );

        JTable table = extractTable(panelHolder[0], "groupsTable");

        // Act
        int rowCount = table.getRowCount();

        // Assert
        assertEquals(2, rowCount);
    }

    /**
     * Testcase: Should display assigned student count in group.
     *
     * Steps:
     * 1. Add student.
     * 2. Add group.
     * 3. Assign student to the group.
     * 4. Create GroupsPanel.
     * 5. Read student count from table.
     *
     * Result state:
     * - Group shows one assigned student.
     */
    @Test
    void testGroupsPanel_should_display_student_count_for_group() throws Exception {

        // Arrange
        Student student = new Student(
                manager.generateStudentId(),
                "Олег",
                "Бондаренко",
                "Сергійович",
                18,
                "Чоловіча",
                ""
        );
        manager.addStudent(student);

        StudentGroup group = new StudentGroup("ІПЗ-21", 2, "ІПЗ");
        manager.addGroup(group);
        manager.assignStudentToGroup(student.getId(), "ІПЗ-21");

        final GroupsPanel[] panelHolder = new GroupsPanel[1];

        SwingUtilities.invokeAndWait(() ->
                panelHolder[0] = new GroupsPanel(manager)
        );

        JTable table = extractTable(panelHolder[0], "groupsTable");

        // Act
        int studentsCount = (int) table.getValueAt(0, 3);

        // Assert
        assertEquals(1, studentsCount);
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