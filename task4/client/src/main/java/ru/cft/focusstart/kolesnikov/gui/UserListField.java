package ru.cft.focusstart.kolesnikov.gui;

import ru.cft.focusstart.kolesnikov.Message;

import javax.swing.*;
import java.util.ArrayList;

public class UserListField implements NeedToRefresh {
    private JTextArea userListWindow;
    private JScrollPane userListPanel;

    public UserListField(){
        JScrollPane scrollerUserList = new JScrollPane();
        userListWindow = new JTextArea();
        userListWindow.setEditable(false);
        scrollerUserList.setViewportView(userListWindow);
        this.userListPanel = scrollerUserList;
    }

    public JScrollPane getUserListPanel(){
        return userListPanel;
    }

    public JTextArea getUserListWindow() {
        return userListWindow;
    }


    public void refresh(Message msg) {
        userListWindow.setText("");
        for (String user : msg.getUserList()) {
            userListWindow.append(user);
            userListWindow.append(System.lineSeparator());
        }
    }
}
