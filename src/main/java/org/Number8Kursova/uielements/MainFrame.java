package org.Number8Kursova.uielements;

import org.Number8Kursova.Manager.DeaneryManager;
import org.Number8Kursova.uielements.GroupsDialog.GroupsPanel;
import org.Number8Kursova.uielements.StudentsDialog.StudentsPanel;

import javax.swing.*;
import java.awt.*;


/**
 * Main application window.
 *
 * Contains tabbed interface for students, groups,
 * dormitory management and search.
 */
public class MainFrame extends JFrame {

    private final DeaneryManager manager;

    /**
     * Creates main window of the application.
     *
     * @param manager central application manager
     */
    public MainFrame(DeaneryManager manager) {
        this.manager = manager;

        setTitle("Електронний деканат");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
    }

    /**
     * Initializes tabbed interface and panels.
     */
    private void initComponents() {
        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Студенти", new StudentsPanel(manager));
        tabbedPane.addTab("Групи", new GroupsPanel(manager));
        tabbedPane.addTab("Гуртожиток", new DormitoryPanel(manager));
        tabbedPane.addTab("Пошук", new SearchPanel(manager));

        add(tabbedPane, BorderLayout.CENTER);
    }
}