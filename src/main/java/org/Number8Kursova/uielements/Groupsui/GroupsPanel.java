package org.Number8Kursova.uielements.Groupsui;

import org.Number8Kursova.Manager.DeaneryManager;
import org.Number8Kursova.Manager.Student;
import org.Number8Kursova.Manager.StudentGroup;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class GroupsPanel extends JPanel {

    private final DeaneryManager manager;
    private JTable groupsTable;
    private DefaultTableModel tableModel;

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

    private void editSelectedGroup() {
        StudentGroup group = getSelectedGroup();
        if (group == null) {
            return;
        }

        Window window = SwingUtilities.getWindowAncestor(this);
        Frame owner = window instanceof Frame ? (Frame) window : null;

        EditGroupDialog dialog = new EditGroupDialog(owner, group);
        dialog.setVisible(true);

        if (dialog.isSaved()) {
            refreshTable();
        }
    }

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

    private void addStudentToSelectedGroup() {
        StudentGroup group = getSelectedGroup();
        if (group == null) {
            return;
        }

        List<Student> availableStudents = manager.getAllStudents();

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
            group.addStudent(selectedStudent);
            refreshTable();
        }
    }

    private void removeStudentFromSelectedGroup() {
        StudentGroup group = getSelectedGroup();
        if (group == null) {
            return;
        }

        if (group.getStudents().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "У групі немає студентів.",
                    "Інформація",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Window window = SwingUtilities.getWindowAncestor(this);
        Frame owner = window instanceof Frame ? (Frame) window : null;

        RemoveStudentFromGroupDialog dialog = new RemoveStudentFromGroupDialog(owner, group.getStudents());
        dialog.setVisible(true);

        Student selectedStudent = dialog.getSelectedStudent();
        if (selectedStudent != null) {
            group.removeStudent(selectedStudent);
            refreshTable();
        }
    }

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