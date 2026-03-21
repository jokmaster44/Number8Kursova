package org.Number8Kursova.uielements;

import org.Number8Kursova.Manager.ClassRoom;
import org.Number8Kursova.Manager.DeaneryManager;
import org.Number8Kursova.Manager.Student;
import org.Number8Kursova.uielements.Room.AddRoomDialog;
import org.Number8Kursova.uielements.Room.EditRoomDialog;
import org.Number8Kursova.uielements.Room.RoomDetailsDialog;
import org.Number8Kursova.uielements.Room.SettleStudentDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DormitoryPanel extends JPanel {

    private final DeaneryManager manager;
    private JTable roomsTable;
    private DefaultTableModel tableModel;

    public DormitoryPanel(DeaneryManager manager) {
        this.manager = manager;
        setLayout(new BorderLayout());

        initComponents();
        refreshTable();
    }

    private void initComponents() {
        String[] columns = {"Кімната", "Місткість", "Зайнято", "Вільно"};

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        roomsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(roomsTable);

        JPanel buttonPanel = new JPanel();

        JButton addRoomButton = new JButton("Додати кімнату");
        JButton viewButton = new JButton("Переглянути");
        JButton editRoomButton = new JButton("Редагувати");
        JButton deleteRoomButton = new JButton("Видалити");
        JButton settleButton = new JButton("Поселити студента");
        JButton evictButton = new JButton("Виселити студента");
        JButton refreshButton = new JButton("Оновити");

        addRoomButton.addActionListener(e -> addRoom());
        viewButton.addActionListener(e -> viewSelectedRoom());
        editRoomButton.addActionListener(e -> editSelectedRoom());
        deleteRoomButton.addActionListener(e -> deleteSelectedRoom());
        settleButton.addActionListener(e -> settleStudentToRoom());
        evictButton.addActionListener(e -> evictStudentFromRoom());
        refreshButton.addActionListener(e -> refreshTable());

        buttonPanel.add(addRoomButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(editRoomButton);
        buttonPanel.add(deleteRoomButton);
        buttonPanel.add(settleButton);
        buttonPanel.add(evictButton);
        buttonPanel.add(refreshButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addRoom() {
        Window window = SwingUtilities.getWindowAncestor(this);
        Frame owner = window instanceof Frame ? (Frame) window : null;

        AddRoomDialog dialog = new AddRoomDialog(owner);
        dialog.setVisible(true);

        ClassRoom room = dialog.getCreatedRoom();
        if (room != null) {
            manager.addRoom(room);
            refreshTable();
        }
    }

    private void viewSelectedRoom() {
        ClassRoom room = getSelectedRoom();
        if (room == null) {
            return;
        }

        Window window = SwingUtilities.getWindowAncestor(this);
        Frame owner = window instanceof Frame ? (Frame) window : null;

        RoomDetailsDialog dialog = new RoomDetailsDialog(owner, room);
        dialog.setVisible(true);
    }

    private void editSelectedRoom() {
        ClassRoom room = getSelectedRoom();
        if (room == null) {
            return;
        }

        Window window = SwingUtilities.getWindowAncestor(this);
        Frame owner = window instanceof Frame ? (Frame) window : null;

        EditRoomDialog dialog = new EditRoomDialog(owner, room);
        dialog.setVisible(true);

        if (dialog.isSaved()) {
            refreshTable();
        }
    }

    private void deleteSelectedRoom() {
        ClassRoom room = getSelectedRoom();
        if (room == null) {
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Ви дійсно хочете видалити кімнату?",
                "Підтвердження",
                JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        manager.removeRoom(room);
        refreshTable();
    }

    private void settleStudentToRoom() {
        ClassRoom room = getSelectedRoom();
        if (room == null) {
            return;
        }

        List<Student> availableStudents = manager.getStudentsNotInDormitory();

        if (availableStudents.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Немає студентів для поселення.",
                    "Інформація",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Window window = SwingUtilities.getWindowAncestor(this);
        Frame owner = window instanceof Frame ? (Frame) window : null;

        SettleStudentDialog dialog = new SettleStudentDialog(owner, availableStudents);
        dialog.setVisible(true);

        Student selectedStudent = dialog.getSelectedStudent();
        if (selectedStudent != null) {
            boolean success = manager.settleStudent(selectedStudent.getId(), room.getRoomNumber());

            if (!success) {
                JOptionPane.showMessageDialog(this,
                        "Не вдалося поселити студента.",
                        "Помилка",
                        JOptionPane.ERROR_MESSAGE);
            }

            refreshTable();
        }
    }

    private void evictStudentFromRoom() {
        List<Student> dormitoryStudents = manager.getDormitoryStudents();

        if (dormitoryStudents.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Немає студентів для виселення.",
                    "Інформація",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Window window = SwingUtilities.getWindowAncestor(this);
        Frame owner = window instanceof Frame ? (Frame) window : null;

        org.Number8Kursova.uielements.EvictStudentDialog dialog = new org.Number8Kursova.uielements.EvictStudentDialog(owner, dormitoryStudents);
        dialog.setVisible(true);

        Student selectedStudent = dialog.getSelectedStudent();
        if (selectedStudent != null) {
            manager.evictStudent(selectedStudent.getId());
            refreshTable();
        }
    }

    private ClassRoom getSelectedRoom() {
        int selectedRow = roomsTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Оберіть кімнату.",
                    "Увага",
                    JOptionPane.WARNING_MESSAGE);
            return null;
        }

        int roomNumber = (int) tableModel.getValueAt(selectedRow, 0);
        ClassRoom room = manager.findRoomByNumber(roomNumber);

        if (room == null) {
            JOptionPane.showMessageDialog(this,
                    "Кімнату не знайдено.",
                    "Помилка",
                    JOptionPane.ERROR_MESSAGE);
        }

        return room;
    }

    private void refreshTable() {
        tableModel.setRowCount(0);

        List<ClassRoom> rooms = manager.getAllRooms();

        for (ClassRoom room : rooms) {
            tableModel.addRow(new Object[]{
                    room.getRoomNumber(),
                    room.getCapacity(),
                    room.getResidents().size(),
                    room.getFreePlaces()
            });
        }
    }
}