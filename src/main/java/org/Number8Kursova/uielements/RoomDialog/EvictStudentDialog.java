package org.Number8Kursova.uielements.RoomDialog;

import org.Number8Kursova.Manager.Student;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Dialog for selecting a student to evict from dormitory.
 *
 * Shows students currently living in dormitory.
 */
public class EvictStudentDialog extends JDialog {

    private JList<Student> studentJList;
    private Student selectedStudent;

    /**
     * Creates modal dialog with dormitory residents.
     *
     * @param owner parent window
     * @param students students available for eviction
     */
    public EvictStudentDialog(Frame owner, List<Student> students) {
        super(owner, "Виселити студента", true);

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

        JButton evictButton = new JButton("Виселити");
        JButton cancelButton = new JButton("Скасувати");

        evictButton.addActionListener(e -> selectStudent());
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(evictButton);
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