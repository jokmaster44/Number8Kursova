package org.Number8Kursova.uielements.StudentsUi;

import org.Number8Kursova.Manager.Student;

import javax.swing.*;
import java.awt.*;


public class StudentDetailsDialog extends JDialog {

    public StudentDetailsDialog(Frame owner, Student student) {
        super(owner, "Дані студента", true);

        setSize(400, 300);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        initComponents(student);
    }

    private void initComponents(Student student) {
        JPanel infoPanel = new JPanel(new GridLayout(0, 1, 5, 5));

        infoPanel.add(new JLabel("ID: " + student.getId()));
        infoPanel.add(new JLabel("Прізвище: " + student.getLastName()));
        infoPanel.add(new JLabel("Ім'я: " + student.getFirstName()));
        infoPanel.add(new JLabel("По батькові: " + student.getMiddleName()));
        infoPanel.add(new JLabel("Вік: " + student.getAge()));
        infoPanel.add(new JLabel("Стать: " + student.getGender()));
        infoPanel.add(new JLabel("Група: " + student.getGroupName()));

        String dormitoryInfo = student.getRoomNumber() == -1
                ? "Не проживає"
                : "Кімната " + student.getRoomNumber();

        infoPanel.add(new JLabel("Гуртожиток: " + dormitoryInfo));

        JButton closeButton = new JButton("Закрити");
        closeButton.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);

        add(infoPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}