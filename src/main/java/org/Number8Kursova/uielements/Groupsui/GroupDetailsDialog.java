package org.Number8Kursova.uielements.Groupsui;

import org.Number8Kursova.Manager.Student;
import org.Number8Kursova.Manager.StudentGroup;

import javax.swing.*;
import java.awt.*;

public class GroupDetailsDialog extends JDialog {

    public GroupDetailsDialog(Frame owner, StudentGroup group) {
        super(owner, "Дані групи", true);

        setSize(500, 400);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        initComponents(group);
    }

    private void initComponents(StudentGroup group) {
        JPanel infoPanel = new JPanel(new GridLayout(0, 1, 5, 5));

        infoPanel.add(new JLabel("Назва групи: " + group.getName()));
        infoPanel.add(new JLabel("Курс: " + group.getCourse()));
        infoPanel.add(new JLabel("Спеціальність: " + group.getSpecialty()));
        infoPanel.add(new JLabel("Кількість студентів: " + group.getStudents().size()));

        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Student student : group.getStudents()) {
            listModel.addElement(student.getLastName() + " " + student.getFirstName() + " " + student.getMiddleName());
        }

        JList<String> studentsList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(studentsList);

        JButton closeButton = new JButton("Закрити");
        closeButton.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);

        add(infoPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}