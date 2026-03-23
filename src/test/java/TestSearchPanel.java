package org.Number8Kursova;

import org.Number8Kursova.Manager.ClassRoom;
import org.Number8Kursova.Manager.DeaneryManager;
import org.Number8Kursova.Manager.Student;
import org.Number8Kursova.Manager.StudentGroup;
import org.Number8Kursova.uielements.SearchPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Basic unit tests for SearchPanel.
 *
 * These tests verify that:
 * - search by group returns expected students
 * - search for dormitory residents returns only settled students
 */
public class TestSearchPanel {

    private DeaneryManager manager;

    @BeforeEach
    void setUp() {
        TestDatabaseHelper.resetDatabase();
        manager = new DeaneryManager();
    }

    /**
     * Testcase: Should return students by group search.
     *
     * Steps:
     * 1. Add student.
     * 2. Add group.
     * 3. Assign student to the group.
     * 4. Create SearchPanel.
     * 5. Set search text to group name.
     * 6. Execute group search.
     *
     * Result state:
     * - Result table contains one student.
     */
    @Test
    void testSearchPanel_should_return_students_by_group() throws Exception {

        // Arrange
        Student student = new Student(
                manager.generateStudentId(),
                "Марія",
                "Коваленко",
                "Іванівна",
                19,
                "Жіноча",
                ""
        );
        manager.addStudent(student);

        manager.addGroup(new StudentGroup("ІПЗ-21", 2, "ІПЗ"));
        manager.assignStudentToGroup(student.getId(), "ІПЗ-21");

        final SearchPanel[] panelHolder = new SearchPanel[1];
        SwingUtilities.invokeAndWait(() ->
                panelHolder[0] = new SearchPanel(manager)
        );

        JTextField searchField = extractField(panelHolder[0], "searchField", JTextField.class);
        JTable resultTable = extractField(panelHolder[0], "resultTable", JTable.class);

        // Act
        SwingUtilities.invokeAndWait(() -> searchField.setText("ІПЗ-21"));
        invokePrivate(panelHolder[0], "searchByGroup");

        // Assert
        assertEquals(1, resultTable.getRowCount());
    }

    /**
     * Testcase: Should return dormitory residents.
     *
     * Steps:
     * 1. Add student.
     * 2. Add room.
     * 3. Settle student into dormitory.
     * 4. Create SearchPanel.
     * 5. Execute dormitory search.
     *
     * Result state:
     * - Result table contains one settled student.
     */
    @Test
    void testSearchPanel_should_return_dormitory_residents() throws Exception {

        // Arrange
        Student student = new Student(
                manager.generateStudentId(),
                "Сергій",
                "Левченко",
                "Петрович",
                18,
                "Чоловіча",
                ""
        );
        manager.addStudent(student);
        manager.addRoom(new ClassRoom(101, 2));
        manager.settleStudent(student.getId(), 101);

        final SearchPanel[] panelHolder = new SearchPanel[1];
        SwingUtilities.invokeAndWait(() ->
                panelHolder[0] = new SearchPanel(manager)
        );

        JTable resultTable = extractField(panelHolder[0], "resultTable", JTable.class);

        // Act
        invokePrivate(panelHolder[0], "showDormitoryStudents");

        // Assert
        assertEquals(1, resultTable.getRowCount());
    }

    private void invokePrivate(Object target, String methodName) {
        try {
            Method method = target.getClass().getDeclaredMethod(methodName);
            method.setAccessible(true);
            method.invoke(target);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T extractField(Object target, String fieldName, Class<T> type) {
        try {
            Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return type.cast(field.get(target));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}