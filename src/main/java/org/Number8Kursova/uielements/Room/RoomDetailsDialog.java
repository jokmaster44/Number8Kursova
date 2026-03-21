package org.Number8Kursova.uielements.Room;

import org.Number8Kursova.Manager.ClassRoom;
import org.Number8Kursova.Manager.Student;

import javax.swing.*;
import java.awt.*;

public class RoomDetailsDialog extends JDialog {

    public RoomDetailsDialog(Frame owner, ClassRoom room) {
        super(owner, "Дані кімнати", true);

        setSize(450, 350);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        initComponents(room);
    }

    private void initComponents(ClassRoom room) {
        JPanel infoPanel = new JPanel(new GridLayout(0, 1, 5, 5));

        infoPanel.add(new JLabel("Номер кімнати: " + room.getRoomNumber()));
        infoPanel.add(new JLabel("Місткість: " + room.getCapacity()));
        infoPanel.add(new JLabel("Зайнято: " + room.getResidents().size()));
        infoPanel.add(new JLabel("Вільно: " + room.getFreePlaces()));

        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Student student : room.getResidents()) {
            listModel.addElement(student.getLastName() + " " + student.getFirstName() + " " + student.getMiddleName());
        }

        JList<String> residentsList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(residentsList);

        JButton closeButton = new JButton("Закрити");
        closeButton.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);

        add(infoPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}