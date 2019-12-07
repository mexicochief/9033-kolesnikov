package ru.cft.focusstart.kolesnikov.gui;

import ru.cft.focusstart.kolesnikov.Message;
import ru.cft.focusstart.kolesnikov.MessageType;

import javax.swing.*;
import java.text.SimpleDateFormat;

public class ChatField implements NeedToRefresh {
    private JScrollPane chatPanel;
    private JTextArea chatWindow;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

    public ChatField() {
        JScrollPane scrollerChat = new JScrollPane();
        chatWindow = new JTextArea();
        chatWindow.setEditable(false);
        scrollerChat.setViewportView(chatWindow);
        chatPanel = scrollerChat;
    }

    public JScrollPane getChatPanel() {
        return chatPanel;
    }

    public void refresh(Message msg) {
        String date = dateFormat.format(msg.getDate());
        chatWindow.append(date + " " +msg.getUserName() + ": " + msg.getMessageVal());
        chatWindow.append(System.lineSeparator());

//        if (msg.getMessageType() == MessageType.USER_CONNECTED) {
//            chatWindow.append(msg.getUserName() + ": " + msg.getMessageType().getInfo());
//        } else {
//            chatWindow.append(msg.getUserName() + ":" + msg.getMessageVal());
//        }
//        chatWindow.append(System.lineSeparator());
    }
}
