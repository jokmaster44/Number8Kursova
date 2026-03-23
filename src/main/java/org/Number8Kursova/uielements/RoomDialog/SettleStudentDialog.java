package org.Number8Kursova.uielements.RoomDialog;

import org.Number8Kursova.Manager.Student;

import javax.swing.*;
import java.awt.*;
import java.util.List;


/**
 * Dialog for selecting a student to settle in a room.
 *
 * Displays students available for dormitory settlement.
 */
public class SettleStudentDialog extends JDialog {

    private JList<Student> studentJList;
    private Student selectedStudent;

    /**
     * Creates modal dialog with list of students.
     *
     * @param owner parent window
     * @param students students available for settlement
     */
    public SettleStudentDialog(Frame owner, List<Student> students) {
        super(owner, "Поселити студента", true);

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

        JButton settleButton = new JButton("Поселити");
        JButton cancelButton = new JButton("Скасувати");

        settleButton.addActionListener(e -> selectStudent());
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(settleButton);
        buttonPanel.add(cancelButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Stores selected student and closes dialog.
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