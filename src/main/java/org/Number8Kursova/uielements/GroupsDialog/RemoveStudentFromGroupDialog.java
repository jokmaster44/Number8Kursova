package org.Number8Kursova.uielements.GroupsDialog;

import org.Number8Kursova.Manager.Student;

import javax.swing.*;
import java.awt.*;
import java.util.List;


/**
 * Dialog for selecting a student to remove from a group.
 *
 * Displays current group members and allows choosing one.
 */
public class RemoveStudentFromGroupDialog extends JDialog {

    private JList<Student> studentJList;
    private Student selectedStudent;

    /**
     * Creates modal dialog with list of group students.
     *
     * @param owner parent window
     * @param students students available for removal
     */
    public RemoveStudentFromGroupDialog(Frame owner, List<Student> students) {
        super(owner, "Видалити студента з групи", true);

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

        JButton removeButton = new JButton("Видалити");
        JButton cancelButton = new JButton("Скасувати");

        removeButton.addActionListener(e -> selectStudent());
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(removeButton);
        buttonPanel.add(cancelButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Stores selected student and closes dialog.
     *
     * Shows warning if no student is selected.
     */
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

    /**
     * Returns chosen student or null if dialog was cancelled.
     *
     * @return selected student
     */
    public Student getSelectedStudent() {
        return selectedStudent;
    }
}