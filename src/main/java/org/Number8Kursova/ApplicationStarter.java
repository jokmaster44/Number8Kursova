package org.Number8Kursova;

import org.Number8Kursova.Manager.DeaneryManager;
import org.Number8Kursova.database.DatabaseInitializer;
import org.Number8Kursova.uielements.MainFrame;

import javax.swing.*;


/**
 * Application entry point.
 *
 * Initializes database and launches the main GUI.
 */
public class ApplicationStarter {

    /**
     * Starts the application.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DatabaseInitializer.initialize();

            DeaneryManager manager = new DeaneryManager();
            MainFrame frame = new MainFrame(manager);
            frame.setVisible(true);
        });
    }
}