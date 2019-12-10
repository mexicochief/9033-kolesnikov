package ru.cft.focusstart.kolesnikov.gui.window.generalwindow;


import ru.cft.focusstart.kolesnikov.gui.widgets.ChatField;
import ru.cft.focusstart.kolesnikov.gui.widgets.UserListField;
import ru.cft.focusstart.kolesnikov.gui.window.startwindow.GreetingWindow;
import ru.cft.focusstart.kolesnikov.gui.window.startwindow.StartWindow;
import ru.cft.focusstart.kolesnikov.model.Transmitter;

import javax.swing.*;
import java.awt.*;

public class MainWindow implements GeneralWindow {
    private JFrame mainFrame;
    private UserListField userListField = new UserListField();
    private ChatField chatField = new ChatField();
    private Transmitter transmitter;
    private StartWindow startWindow;

    public MainWindow(Transmitter transmitter) {
        mainFrame = new JFrame();
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        JMenu jMenu = new JMenu("File");
        jMenu.add(exitItem);
        JMenuBar jMenuBar = new JMenuBar();
        jMenuBar.add(jMenu);
        mainFrame.setJMenuBar(jMenuBar);
        mainFrame.setBounds(500, 500, 500, 500);
        this.transmitter = transmitter;
        this.startWindow = new GreetingWindow(transmitter);
    }

    public void runApp() {
        startWindow.runWindow();
    }

    @Override
    public void runWindow() {
        JPanel mainPanel = makeJPanel();
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 5;
        constraints.weightx = 2.0;
        constraints.weighty = 5.0;
        mainPanel.add(chatField.getChatPanel(), constraints);

        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridx = 5;
        constraints.gridwidth = 4;
        constraints.gridheight = GridBagConstraints.REMAINDER;
        constraints.weightx = 1.0;
        constraints.weighty = 5.0;
        mainPanel.add(userListField.getUserListPanel(), constraints);

        JTextField inputTextFieldWindow = new JTextField();
        constraints.anchor = GridBagConstraints.PAGE_END;
        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.gridwidth = 4;
        constraints.gridheight = 0;
        constraints.weightx = 1.0;
        constraints.weighty = 0.0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(inputTextFieldWindow, constraints);

        JButton sendButton = new JButton("Send");
        constraints.gridx = 4;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weightx = 0;
        sendButton.addActionListener(e -> {
            String message = inputTextFieldWindow.getText();
            inputTextFieldWindow.setText("");
            transmitter.sendMsgToServer(message);
        });
        mainPanel.add(sendButton, constraints);

        mainFrame.add(mainPanel);
        mainFrame.setPreferredSize(new Dimension(600, 300));
        mainFrame.setResizable(false);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    private JPanel makeJPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        return mainPanel;
    }

    public UserListField getUserListField() {
        return userListField;
    }

    public ChatField getChatField() {
        return chatField;
    }

    @Override
    public StartWindow getStartWindow() {
        return startWindow;
    }
}
