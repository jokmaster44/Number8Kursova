package org.Number8Kursova;

import org.Number8Kursova.Manager.DeaneryManager;
import org.Number8Kursova.uielements.MainFrame;

import javax.swing.*;


public class ApplicationStarter {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DeaneryManager manager = new DeaneryManager();
            MainFrame frame = new MainFrame(manager);
            frame.setVisible(true);
        });
    }
}