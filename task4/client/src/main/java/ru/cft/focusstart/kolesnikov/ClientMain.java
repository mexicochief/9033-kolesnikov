package ru.cft.focusstart.kolesnikov;

import ru.cft.focusstart.kolesnikov.gui.window.generalwindow.MainWindow;
import ru.cft.focusstart.kolesnikov.model.Transmitter;

public class ClientMain {
    public static void main(String[] args) {
        Transmitter transmitter = new Transmitter();
        MainWindow mainWindow = new MainWindow(transmitter);
        transmitter.addWindow(mainWindow);
        transmitter.addUserListObserver(mainWindow.getUserListField());
        transmitter.addMessageListObserver(mainWindow.getChatField());
        mainWindow.runApp();
    }
}
