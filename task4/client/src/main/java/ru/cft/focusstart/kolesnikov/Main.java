package ru.cft.focusstart.kolesnikov;

import ru.cft.focusstart.kolesnikov.gui.MainWindow;
import ru.cft.focusstart.kolesnikov.model.Transmitter;

public class Main {
    public static void main(String[] args) {
        Transmitter transmitter = new Transmitter();
        MainWindow mainWindow = new MainWindow(transmitter);
        transmitter.addObserver(mainWindow);
        mainWindow.runApp();

    }

}
