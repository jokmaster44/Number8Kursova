package org.Number8Kursova.uielements.GroupsDialog;

import org.Number8Kursova.Manager.StudentGroup;

import javax.swing.*;
import java.awt.*;


/**
 * Dialog window for creating a new student group.
 *
 * Allows the user to enter group name, course and specialty.
 * After successful validation a StudentGroup object is created.
 */
public class AddGroupDialog extends JDialog {

    private JTextField nameField;
    private JTextField courseField;
    private JTextField specialtyField;

    private StudentGroup createdGroup;

    /**
     * Creates modal dialog for adding a group.
     *
     * @param owner parent window
     */
    public AddGroupDialog(Frame owner) {
        super(owner, "Додати групу", true);

        setSize(400, 250);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        initComponents();
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

        saveButton.addActionListener(e -> saveGroup());
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Validates input fields and creates a new group.
     *
     * Shows an error message if data is invalid.
     */
    private void saveGroup() {
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

        createdGroup = new StudentGroup(name, course, specialty);
        dispose();
    }

    public StudentGroup getCreatedGroup() {
        return createdGroup;
    }
}