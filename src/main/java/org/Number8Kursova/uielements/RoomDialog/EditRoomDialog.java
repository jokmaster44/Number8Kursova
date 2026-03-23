package org.Number8Kursova.uielements.RoomDialog;

import org.Number8Kursova.Manager.ClassRoom;

import javax.swing.*;
import java.awt.*;


/**
 * Dialog for editing room data.
 *
 * Displays current room values and allows changing them.
 */
public class EditRoomDialog extends JDialog {

    private JTextField roomNumberField;
    private JTextField capacityField;

    private boolean saved = false;
    private final ClassRoom room;

    /**
     * Creates modal dialog for editing a room.
     *
     * @param owner parent window
     * @param room room to edit
     */
    public EditRoomDialog(Frame owner, ClassRoom room) {
        super(owner, "Редагувати кімнату", true);
        this.room = room;

        setSize(350, 200);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        initComponents();
        fillFields();
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

        saveButton.addActionListener(e -> saveChanges());
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Fills input fields with current room data.
     */
    private void fillFields() {
        roomNumberField.setText(String.valueOf(room.getRoomNumber()));
        capacityField.setText(String.valueOf(room.getCapacity()));
    }

    /**
     * Validates input and applies changes to the room.
     */
    private void saveChanges() {
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

        room.setRoomNumber(roomNumber);
        room.setCapacity(capacity);

        saved = true;
        dispose();
    }

    /**
     * Indicates whether user confirmed changes.
     *
     * @return true if room was saved
     */
    public boolean isSaved() {
        return saved;
    }
}