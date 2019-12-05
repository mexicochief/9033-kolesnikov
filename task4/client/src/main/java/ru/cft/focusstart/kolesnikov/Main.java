package ru.cft.focusstart.kolesnikov;

import ru.cft.focusstart.kolesnikov.model.Transmitter;

public class Main {
    public static void main(String[] args) {
        Transmitter transmitter = new Transmitter();
        ChatFrame window = new ChatFrame(transmitter);
        transmitter.addObserver(window);
        window.runApp();
    }

}
