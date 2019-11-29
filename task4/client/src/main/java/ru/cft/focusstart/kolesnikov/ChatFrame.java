package ru.cft.focusstart.kolesnikov;

import ru.cft.focusstart.kolesnikov.model.Transmitter;

import javax.swing.*;

import java.awt.*;

import java.util.ArrayList;


public class ChatFrame {
    private JFrame frame;
    private ArrayList<String> userNames;
    private Transmitter transmitter;
    private JTextArea chatField;
    private JTextArea userList;
    private Runnable refreshingChatWindow = () -> refreshChatWindow();
    private Thread refreshingChatWindowThread = new Thread(refreshingChatWindow);

    ChatFrame() {
        transmitter = new Transmitter();
        userNames = new ArrayList<>();
    }

    public void runApp() {
        runGreetingWindow();
        //refreshingChatWindowThread.start();
    }

    private void runGreetingWindow() {
        JFrame greetingFrame = new JFrame();
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        JTextField serverTextField = new JTextField();
        JTextField nameTextField = new JTextField();
        JButton button = new JButton("Connect");
        serverTextField.setBorder(BorderFactory.createTitledBorder("Server"));
        nameTextField.setBorder(BorderFactory.createTitledBorder("User name"));
        button.addActionListener(e -> {
            String userName = nameTextField.getText();
           // String serverAddress = serverTextField.getText();
            userNames.add(userName);
            runMainWindow();
            greetingFrame.dispose();

//            Наскольколко верно будет поместить проверку, которая ниже, здесь ?
//            if (transmitter.connect(serverAddress, 123)) {
//                runMainWindow();
//                greetingFrame.dispose();
//            } else {
//                makeConnectionProblemWindow();
//            }
        });

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridwidth = 2;
        panel.add(serverTextField, constraints);

        constraints.gridwidth = 2;
        constraints.gridy = 1;
        panel.add(nameTextField, constraints);

        constraints.fill = GridBagConstraints.NONE;
        constraints.gridwidth = 1;
        constraints.gridy = 2;
        panel.add(button, constraints);

        greetingFrame.add(panel);
        greetingFrame.setMinimumSize(new Dimension(200, 200));
        greetingFrame.setBackground(Color.WHITE);
        greetingFrame.setResizable(false);
        greetingFrame.setVisible(true);
    }

    private void runMainWindow() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        JMenu jMenu = new JMenu("File");
        jMenu.add(exitItem);
        JMenuBar jMenuBar = new JMenuBar();
        jMenuBar.add(jMenu);
        frame.setJMenuBar(jMenuBar);
        frame.setBounds(500, 500, 500, 500);

        JPanel mainPanel = makeJPanel();
        GridBagConstraints constraints = new GridBagConstraints();

        JScrollPane chatWindow = makeChatWindow();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 5;
        constraints.weightx = 2.0;
        constraints.weighty = 5.0;
        mainPanel.add(chatWindow, constraints);

        JScrollPane userListWindow = makeUserListWindow();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridx = 5;
        constraints.gridwidth = 4;
        constraints.gridheight = GridBagConstraints.REMAINDER;
        constraints.weightx = 1.0;
        constraints.weighty = 5.0;
        mainPanel.add(userListWindow, constraints);

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
            chatField.append(message + System.lineSeparator());// вынести в отдельную функцию
            transmitter.sendMessageToServer(message);
            inputTextFieldWindow.setText("");
        });
        mainPanel.add(sendButton, constraints);

        frame.add(mainPanel);
        frame.setPreferredSize(new Dimension(600, 300));
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }

    private JPanel makeJPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        return mainPanel;
    }

    private JScrollPane makeChatWindow() {
        JScrollPane scrollerChat = new JScrollPane();
        chatField = new JTextArea();
        chatField.setEditable(false);
        scrollerChat.setViewportView(chatField);
        return scrollerChat;
    }

    private JScrollPane makeUserListWindow() {
        JScrollPane scrollerUserList = new JScrollPane();
        userList = new JTextArea();
        userList.setEditable(false);
        for (String user : userNames) {
            userList.append(user);
        }
        scrollerUserList.setViewportView(userList);
        return scrollerUserList;
    }

    private void refreshChatWindow() {
        while (true) {
            chatField.append(transmitter.takeMessageFromServer());
        }
    }

    private void makeConnectionProblemWindow() {
        JFrame connectionProblemFrame = new JFrame();
        JLabel connectionProblemMsg = new JLabel("Connection failed");
        connectionProblemFrame.add(connectionProblemMsg);
        connectionProblemFrame.setVisible(true);
        connectionProblemFrame.pack();
    }
}
