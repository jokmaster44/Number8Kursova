package org.Number8Kursova.uielements.Groupsui;

import org.Number8Kursova.Manager.Student;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RemoveStudentFromGroupDialog extends JDialog {

    private JList<Student> studentJList;
    private Student selectedStudent;

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