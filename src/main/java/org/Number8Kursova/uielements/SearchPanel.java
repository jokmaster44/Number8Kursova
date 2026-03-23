package org.Number8Kursova.uielements;

import org.Number8Kursova.Manager.DeaneryManager;
import org.Number8Kursova.Manager.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;


/**
 * Panel for searching students by different criteria.
 *
 * Supports search by name, group, dormitory residents
 * and displaying all students.
 */
public class SearchPanel extends JPanel {

    private final DeaneryManager manager;
    private JTextField searchField;
    private JTable resultTable;
    private DefaultTableModel tableModel;

    /**
     * Creates search panel.
     *
     * @param manager central application manager
     */
    public SearchPanel(DeaneryManager manager) {
        this.manager = manager;
        setLayout(new BorderLayout());

        initComponents();
    }

    private void initComponents() {

        JPanel topPanel = new JPanel();

        JLabel searchLabel = new JLabel("Пошук:");
        searchField = new JTextField(20);

        JButton searchByNameButton = new JButton("За ПІБ");
        JButton searchByGroupButton = new JButton("За групою");
        JButton searchDormitoryButton = new JButton("У гуртожитку");
        JButton showAllButton = new JButton("Усі студенти");

        searchByNameButton.addActionListener(e -> searchByName());
        searchByGroupButton.addActionListener(e -> searchByGroup());
        searchDormitoryButton.addActionListener(e -> showDormitoryStudents());
        showAllButton.addActionListener(e -> showAllStudents());

        topPanel.add(searchLabel);
        topPanel.add(searchField);
        topPanel.add(searchByNameButton);
        topPanel.add(searchByGroupButton);
        topPanel.add(searchDormitoryButton);
        topPanel.add(showAllButton);

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

        resultTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(resultTable);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Searches students by first or last name.
     */
    private void searchByName() {
        String text = searchField.getText().trim();

        if (text.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Введіть прізвище або ім'я.",
                    "Увага",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<Student> students = manager.getAllStudents();

        clearTable();

        for (Student s : students) {
            if (s.getLastName().toLowerCase().contains(text.toLowerCase())
                    || s.getFirstName().toLowerCase().contains(text.toLowerCase())) {

                addStudentToTable(s);
            }
        }
    }

    /**
     * Searches students by group name.
     */
    private void searchByGroup() {
        String group = searchField.getText().trim();

        if (group.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Введіть назву групи.",
                    "Увага",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<Student> students = manager.getStudentsByGroupName(group);

        clearTable();

        for (Student s : students) {
            addStudentToTable(s);
        }
    }

    /**
     * Displays students living in dormitory.
     */
    private void showDormitoryStudents() {
        List<Student> students = manager.getDormitoryStudents();

        clearTable();

        for (Student s : students) {
            addStudentToTable(s);
        }
    }

    /**
     * Displays all students.
     */
    private void showAllStudents() {
        List<Student> students = manager.getAllStudents();

        clearTable();

        for (Student s : students) {
            addStudentToTable(s);
        }
    }

    /**
     * Clears result table.
     */
    private void clearTable() {
        tableModel.setRowCount(0);
    }

    /**
     * Adds student data as a new row in the table.
     *
     * @param student student to display
     */
    private void addStudentToTable(Student student) {
        Object roomValue = student.getRoomNumber() == -1
                ? "-"
                : student.getRoomNumber();

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