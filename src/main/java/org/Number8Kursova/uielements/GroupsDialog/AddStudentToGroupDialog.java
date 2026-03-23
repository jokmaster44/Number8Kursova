package org.Number8Kursova.uielements.GroupsDialog;

import org.Number8Kursova.Manager.Student;

import javax.swing.*;
import java.awt.*;
import java.util.List;


/**
 * Dialog for selecting a student to add to a group.
 *
 * Displays a list of available students and allows choosing one.
 */
public class AddStudentToGroupDialog extends JDialog {

    private JList<Student> studentJList;
    private Student selectedStudent;

    /**
     * Creates modal dialog with list of students.
     *
     * @param owner parent window
     * @param students list of students available for selection
     */
    public AddStudentToGroupDialog(Frame owner, List<Student> students) {
        super(owner, "Додати студента до групи", true);

        setSize(400, 300);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        initComponents(students);
    }

    private void initComponents(List<Student> students) {
        DefaultListModel<Student> listModel = new DefaultListModel<>();
        for (Student student : students) {
            listModel.addElement(student);
        }

        studentJList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(studentJList);

        JPanel buttonPanel = new JPanel();

        JButton addButton = new JButton("Додати");
        JButton cancelButton = new JButton("Скасувати");

        addButton.addActionListener(e -> selectStudent());
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void selectStudent() {
        selectedStudent = studentJList.getSelectedValue();

        if (selectedStudent == null) {
            JOptionPane.showMessageDialog(this,
                    "Оберіть студента.",
                    "Увага",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        dispose();
    }

    public Student getSelectedStudent() {
        return selectedStudent;
    }
}