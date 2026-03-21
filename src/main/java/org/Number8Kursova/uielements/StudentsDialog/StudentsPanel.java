package org.Number8Kursova.uielements.StudentsDialog;

import org.Number8Kursova.Manager.DeaneryManager;
import org.Number8Kursova.Manager.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;


public class StudentsPanel extends JPanel {

    private final DeaneryManager manager;
    private JTable studentsTable;
    private DefaultTableModel tableModel;

    public StudentsPanel(DeaneryManager manager) {
        this.manager = manager;
        setLayout(new BorderLayout());

        initComponents();
        refreshTable();
    }

    private void initComponents() {
        JButton viewButton = new JButton("Переглянути");
        viewButton.addActionListener(e -> viewSelectedStudent());
        String[] columns = {
                "ID", "Прізвище", "Ім'я", "По батькові",
                "Вік", "Стать", "Група", "Кімната"
        };

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        studentsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(studentsTable);

        JPanel buttonPanel = new JPanel();

        JButton addButton = new JButton("Додати");
        JButton editButton = new JButton("Редагувати");
        JButton deleteButton = new JButton("Видалити");
        JButton refreshButton = new JButton("Оновити");

        addButton.addActionListener(e -> addStudent());
        editButton.addActionListener(e -> editSelectedStudent());
        deleteButton.addActionListener(e -> deleteSelectedStudent());
        refreshButton.addActionListener(e -> refreshTable());

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void viewSelectedStudent() {
        int selectedRow = studentsTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Оберіть студента для перегляду.",
                    "Увага",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int studentId = (int) tableModel.getValueAt(selectedRow, 0);
        Student student = manager.findStudentById(studentId);

        if (student == null) {
            JOptionPane.showMessageDialog(this,
                    "Студента не знайдено.",
                    "Помилка",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        Window window = SwingUtilities.getWindowAncestor(this);
        Frame owner = window instanceof Frame ? (Frame) window : null;

        StudentDetailsDialog dialog = new StudentDetailsDialog(owner, student);
        dialog.setVisible(true);
    }

    private void addStudent() {
        Window window = SwingUtilities.getWindowAncestor(this);
        Frame owner = window instanceof Frame ? (Frame) window : null;

        AddStudentDialog dialog = new AddStudentDialog(owner, manager.generateStudentId());
        dialog.setVisible(true);

        Student student = dialog.getCreatedStudent();
        if (student != null) {
            manager.addStudent(student);
            refreshTable();
        }
    }

    private void editSelectedStudent() {
        int selectedRow = studentsTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Оберіть студента для редагування.",
                    "Увага",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int studentId = (int) tableModel.getValueAt(selectedRow, 0);
        Student student = manager.findStudentById(studentId);

        if (student == null) {
            JOptionPane.showMessageDialog(this,
                    "Студента не знайдено.",
                    "Помилка",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        Window window = SwingUtilities.getWindowAncestor(this);
        Frame owner = window instanceof Frame ? (Frame) window : null;

        EditStudentDialog dialog = new EditStudentDialog(owner, student);
        dialog.setVisible(true);

        if (dialog.isSaved()) {
            refreshTable();
        }
    }

    private void deleteSelectedStudent() {
        int selectedRow = studentsTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Оберіть студента для видалення.",
                    "Увага",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Ви дійсно хочете видалити студента?",
                "Підтвердження",
                JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        int studentId = (int) tableModel.getValueAt(selectedRow, 0);
        Student student = manager.findStudentById(studentId);

        if (student != null) {
            manager.removeStudent(student);
            refreshTable();
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);

        List<Student> students = manager.getAllStudents();

        for (Student student : students) {
            Object roomValue = student.getRoomNumber() == -1 ? "-" : student.getRoomNumber();

            tableModel.addRow(new Object[]{
                    student.getId(),
                    student.getLastName(),
                    student.getFirstName(),
                    student.getMiddleName(),
                    student.getAge(),
                    student.getGender(),
                    student.getGroupName(),
                    roomValue
            });
        }
    }
}