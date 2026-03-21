package org.Number8Kursova.uielements.StudentsDialog;

import org.Number8Kursova.Manager.Student;

import javax.swing.*;
import java.awt.*;


public class AddStudentDialog extends JDialog {

    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField middleNameField;
    private JTextField ageField;
    private JTextField genderField;
    private JTextField groupField;

    private Student createdStudent;
    private final int studentId;

    public AddStudentDialog(Frame owner, int studentId) {
        super(owner, "Додати студента", true);
        this.studentId = studentId;

        setSize(400, 300);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        initComponents();
    }

    private void initComponents() {
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 5, 5));

        formPanel.add(new JLabel("Ім'я:"));
        firstNameField = new JTextField();
        formPanel.add(firstNameField);

        formPanel.add(new JLabel("Прізвище:"));
        lastNameField = new JTextField();
        formPanel.add(lastNameField);

        formPanel.add(new JLabel("По батькові:"));
        middleNameField = new JTextField();
        formPanel.add(middleNameField);

        formPanel.add(new JLabel("Вік:"));
        ageField = new JTextField();
        formPanel.add(ageField);

        formPanel.add(new JLabel("Стать:"));
        genderField = new JTextField();
        formPanel.add(genderField);

        formPanel.add(new JLabel("Група:"));
        groupField = new JTextField();
        formPanel.add(groupField);

        JPanel buttonPanel = new JPanel();

        JButton saveButton = new JButton("Зберегти");
        JButton cancelButton = new JButton("Скасувати");

        saveButton.addActionListener(e -> saveStudent());
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void saveStudent() {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String middleName = middleNameField.getText().trim();
        String ageText = ageField.getText().trim();
        String gender = genderField.getText().trim();
        String groupName = groupField.getText().trim();

        if (firstName.isEmpty() || lastName.isEmpty() || ageText.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Заповніть обов'язкові поля: ім'я, прізвище, вік.",
                    "Помилка",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int age;
        try {
            age = Integer.parseInt(ageText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Вік повинен бути числом.",
                    "Помилка",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        createdStudent = new Student(
                studentId,
                firstName,
                lastName,
                middleName,
                age,
                gender,
                groupName
        );

        dispose();
    }

    public Student getCreatedStudent() {
        return createdStudent;
    }
}