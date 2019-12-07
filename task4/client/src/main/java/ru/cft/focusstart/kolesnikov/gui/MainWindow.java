package ru.cft.focusstart.kolesnikov.gui;


import ru.cft.focusstart.kolesnikov.model.Transmitter;

import javax.swing.*;
import java.awt.*;

public class MainWindow {
    private JFrame mainFrame;
    public UserListField userListField;
    private ChatField chatField;
    private Transmitter transmitter;
    private GreetingFrame greetingFrame;

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
        this.greetingFrame = new GreetingFrame(transmitter);
    }

    public void runApp() {
        greetingFrame.runGreetingWindow();
    }

    public void runMainWindow() {
        greetingFrame.disposeGreetingFrame();

        JPanel mainPanel = makeJPanel();
        GridBagConstraints constraints = new GridBagConstraints();

        chatField = new ChatField();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 5;
        constraints.weightx = 2.0;
        constraints.weighty = 5.0;
        mainPanel.add(chatField.getChatPanel(), constraints);

        userListField = new UserListField();
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
//            chatField.append(message + System.lineSeparator());
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

    public void makeUserInvalidWindow() {
        JFrame connectionProblemFrame = new JFrame();
        JLabel connectionProblemMsg = new JLabel("User invalid");
        connectionProblemFrame.add(connectionProblemMsg);
        connectionProblemFrame.setVisible(true);
        connectionProblemFrame.pack();
    }

    public UserListField getUserListField() {
        return userListField;
    }

    public ChatField getChatField() {
        return chatField;
    }
}
