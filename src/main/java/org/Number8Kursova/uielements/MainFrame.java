package org.Number8Kursova.uielements;

import org.Number8Kursova.Manager.DeaneryManager;
import org.Number8Kursova.uielements.Groupsui.GroupsPanel;
import org.Number8Kursova.uielements.StudentsUi.StudentsPanel;

import javax.swing.*;
import java.awt.*;


public class MainFrame extends JFrame {

    private final DeaneryManager manager;

    public MainFrame(DeaneryManager manager) {
        this.manager = manager;

        setTitle("Електронний деканат");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {
        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Студенти", new StudentsPanel(manager));
        tabbedPane.addTab("Групи", new GroupsPanel(manager));
        tabbedPane.addTab("Гуртожиток", new DormitoryPanel(manager));
        tabbedPane.addTab("Пошук", new SearchPanel(manager));

        add(tabbedPane, BorderLayout.CENTER);
    }
}