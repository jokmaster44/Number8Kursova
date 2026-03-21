package org.Number8Kursova.uielements.GroupsDialog;

import org.Number8Kursova.Manager.DeaneryManager;
import org.Number8Kursova.Manager.Student;
import org.Number8Kursova.Manager.StudentGroup;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Panel for managing student groups.
 *
 * Supports viewing, creating, editing and deleting groups,
 * as well as assigning students to groups.
 */
public class GroupsPanel extends JPanel {

    private final DeaneryManager manager;
    private JTable groupsTable;
    private DefaultTableModel tableModel;

    /**
     * Creates groups management panel.
     *
     * @param manager central application manager
     */
    public GroupsPanel(DeaneryManager manager) {
        this.manager = manager;
        setLayout(new BorderLayout());

        initComponents();
        refreshTable();
    }

    private void initComponents() {
        String[] columns = {
                "Назва групи", "Курс", "Спеціальність", "Кількість студентів"
        };

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        groupsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(groupsTable);

        JPanel buttonPanel = new JPanel();

        JButton addButton = new JButton("Додати");
        JButton viewButton = new JButton("Переглянути");
        JButton editButton = new JButton("Редагувати");
        JButton deleteButton = new JButton("Видалити");
        JButton addStudentButton = new JButton("Додати студента");
        JButton removeStudentButton = new JButton("Видалити студента");
        JButton refreshButton = new JButton("Оновити");

        addButton.addActionListener(e -> addGroup());
        viewButton.addActionListener(e -> viewSelectedGroup());
        editButton.addActionListener(e -> editSelectedGroup());
        deleteButton.addActionListener(e -> deleteSelectedGroup());
        addStudentButton.addActionListener(e -> addStudentToSelectedGroup());
        removeStudentButton.addActionListener(e -> removeStudentFromSelectedGroup());
        refreshButton.addActionListener(e -> refreshTable());

        buttonPanel.add(addButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(addStudentButton);
        buttonPanel.add(removeStudentButton);
        buttonPanel.add(refreshButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Opens dialog for creating a new group.
     */
    private void addGroup() {
        Window window = SwingUtilities.getWindowAncestor(this);
        Frame owner = window instanceof Frame ? (Frame) window : null;

        AddGroupDialog dialog = new AddGroupDialog(owner);
        dialog.setVisible(true);

        StudentGroup group = dialog.getCreatedGroup();
        if (group != null) {
            manager.addGroup(group);
            refreshTable();
        }
    }

    /**
     * Opens details dialog for selected group.
     */
    private void viewSelectedGroup() {
        StudentGroup group = getSelectedGroup();
        if (group == null) {
            return;
        }

        Window window = SwingUtilities.getWindowAncestor(this);
        Frame owner = window instanceof Frame ? (Frame) window : null;

        GroupDetailsDialog dialog = new GroupDetailsDialog(owner, group);
        dialog.setVisible(true);
    }

    /**
     * Opens dialog for editing selected group.
     */
    private void editSelectedGroup() {
        int selectedRow = groupsTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Оберіть групу для редагування.",
                    "Увага",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String oldGroupName = (String) tableModel.getValueAt(selectedRow, 0);
        StudentGroup group = manager.findGroupByName(oldGroupName);

        if (group == null) {
            JOptionPane.showMessageDialog(this,
                    "Групу не знайдено.",
                    "Помилка",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        Window window = SwingUtilities.getWindowAncestor(this);
        Frame owner = window instanceof Frame ? (Frame) window : null;

        EditGroupDialog dialog = new EditGroupDialog(owner, group);
        dialog.setVisible(true);

        if (dialog.isSaved()) {
            manager.updateGroup(oldGroupName, group);
            refreshTable();
        }
    }

    /**
     * Deletes currently selected group after confirmation.
     */
    private void deleteSelectedGroup() {
        StudentGroup group = getSelectedGroup();
        if (group == null) {
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Ви дійсно хочете видалити групу?",
                "Підтвердження",
                JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        manager.removeGroup(group);
        refreshTable();
    }

    /**
     * Assigns a student without group to the selected group.
     */
    private void addStudentToSelectedGroup() {
        StudentGroup group = getSelectedGroup();
        if (group == null) {
            return;
        }

        List<Student> availableStudents = manager.getStudentsWithoutGroup();

        if (availableStudents.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Немає студентів для додавання.",
                    "Інформація",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Window window = SwingUtilities.getWindowAncestor(this);
        Frame owner = window instanceof Frame ? (Frame) window : null;

        AddStudentToGroupDialog dialog = new AddStudentToGroupDialog(owner, availableStudents);
        dialog.setVisible(true);

        Student selectedStudent = dialog.getSelectedStudent();
        if (selectedStudent != null) {
            manager.assignStudentToGroup(selectedStudent.getId(), group.getName());
            refreshTable();
        }
    }

    /**
     * Removes a student from the selected group.
     */
    private void removeStudentFromSelectedGroup() {
        StudentGroup group = getSelectedGroup();
        if (group == null) {
            return;
        }

        List<Student> studentsInGroup = manager.getStudentsByGroupName(group.getName());

        if (studentsInGroup.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "У групі немає студентів.",
                    "Інформація",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Window window = SwingUtilities.getWindowAncestor(this);
        Frame owner = window instanceof Frame ? (Frame) window : null;

        RemoveStudentFromGroupDialog dialog = new RemoveStudentFromGroupDialog(owner, studentsInGroup);
        dialog.setVisible(true);

        Student selectedStudent = dialog.getSelectedStudent();
        if (selectedStudent != null) {
            manager.removeStudentFromGroup(selectedStudent.getId());
            refreshTable();
        }
    }

    /**
     * Returns currently selected group from the table.
     *
     * @return selected group or null if not found
     */
    private StudentGroup getSelectedGroup() {
        int selectedRow = groupsTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Оберіть групу.",
                    "Увага",
                    JOptionPane.WARNING_MESSAGE);
            return null;
        }

        String groupName = (String) tableModel.getValueAt(selectedRow, 0);
        StudentGroup group = manager.findGroupByName(groupName);

        if (group == null) {
            JOptionPane.showMessageDialog(this,
                    "Групу не знайдено.",
                    "Помилка",
                    JOptionPane.ERROR_MESSAGE);
        }

        return group;
    }

    /**
     * Reloads group data into the table.
     */
    private void refreshTable() {
        tableModel.setRowCount(0);

        List<StudentGroup> groups = manager.getAllGroups();

        for (StudentGroup group : groups) {
            tableModel.addRow(new Object[]{
                    group.getName(),
                    group.getCourse(),
                    group.getSpecialty(),
                    group.getStudents().size()
            });
        }
    }
}