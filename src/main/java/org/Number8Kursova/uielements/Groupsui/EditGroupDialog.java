package org.Number8Kursova.uielements.Groupsui;

import org.Number8Kursova.Manager.StudentGroup;

import javax.swing.*;
import java.awt.*;


public class EditGroupDialog extends JDialog {

    private JTextField nameField;
    private JTextField courseField;
    private JTextField specialtyField;

    private boolean saved = false;
    private final StudentGroup group;

    public EditGroupDialog(Frame owner, StudentGroup group) {
        super(owner, "Редагувати групу", true);
        this.group = group;

        setSize(400, 250);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        initComponents();
        fillFields();
    }

    private void initComponents() {
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 5, 5));

        formPanel.add(new JLabel("Назва групи:"));
        nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("Курс:"));
        courseField = new JTextField();
        formPanel.add(courseField);

        formPanel.add(new JLabel("Спеціальність:"));
        specialtyField = new JTextField();
        formPanel.add(specialtyField);

        JPanel buttonPanel = new JPanel();

        JButton saveButton = new JButton("Зберегти");
        JButton cancelButton = new JButton("Скасувати");

        saveButton.addActionListener(e -> saveChanges());
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void fillFields() {
        nameField.setText(group.getName());
        courseField.setText(String.valueOf(group.getCourse()));
        specialtyField.setText(group.getSpecialty());
    }

    private void saveChanges() {
        String name = nameField.getText().trim();
        String courseText = courseField.getText().trim();
        String specialty = specialtyField.getText().trim();

        if (name.isEmpty() || courseText.isEmpty() || specialty.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Заповніть усі поля.",
                    "Помилка",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int course;
        try {
            course = Integer.parseInt(courseText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Курс повинен бути числом.",
                    "Помилка",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        group.setName(name);
        group.setCourse(course);
        group.setSpecialty(specialty);

        saved = true;
        dispose();
    }

    public boolean isSaved() {
        return saved;
    }
}