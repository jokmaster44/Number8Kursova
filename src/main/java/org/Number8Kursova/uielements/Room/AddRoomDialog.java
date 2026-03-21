package org.Number8Kursova.uielements.Room;

import org.Number8Kursova.Manager.ClassRoom;

import javax.swing.*;
import java.awt.*;

public class AddRoomDialog extends JDialog {

    private JTextField roomNumberField;
    private JTextField capacityField;
    private ClassRoom createdRoom;

    public AddRoomDialog(Frame owner) {
        super(owner, "Додати кімнату", true);

        setSize(350, 200);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        initComponents();
    }

    private void initComponents() {
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 5, 5));

        formPanel.add(new JLabel("Номер кімнати:"));
        roomNumberField = new JTextField();
        formPanel.add(roomNumberField);

        formPanel.add(new JLabel("Місткість:"));
        capacityField = new JTextField();
        formPanel.add(capacityField);

        JPanel buttonPanel = new JPanel();

        JButton saveButton = new JButton("Зберегти");
        JButton cancelButton = new JButton("Скасувати");

        saveButton.addActionListener(e -> saveRoom());
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void saveRoom() {
        String roomNumberText = roomNumberField.getText().trim();
        String capacityText = capacityField.getText().trim();

        if (roomNumberText.isEmpty() || capacityText.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Заповніть усі поля.",
                    "Помилка",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int roomNumber;
        int capacity;

        try {
            roomNumber = Integer.parseInt(roomNumberText);
            capacity = Integer.parseInt(capacityText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Номер кімнати і місткість повинні бути числами.",
                    "Помилка",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        createdRoom = new ClassRoom(roomNumber, capacity);
        dispose();
    }

    public ClassRoom getCreatedRoom() {
        return createdRoom;
    }
}