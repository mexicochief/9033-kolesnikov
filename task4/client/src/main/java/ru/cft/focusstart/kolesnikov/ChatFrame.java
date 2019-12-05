package ru.cft.focusstart.kolesnikov;

import ru.cft.focusstart.kolesnikov.model.Transmitter;

import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;


public class ChatFrame implements Observer {
    private JFrame mainFrame;
    private JFrame greetingFrame;
    private JTextArea chatField;
    private JTextArea userListWindow;
    private JTextField serverTextField;
    private JTextField nameTextField;
    Transmitter transmitter;

    ChatFrame(Transmitter transmitter) {
        this.transmitter = transmitter;
    }

    public void runApp() {
        runGreetingWindow();
        //refreshingChatWindowThread.start();
    }

    private void runGreetingWindow() {
        makeGreetingWindow();
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        JButton button = makeConnectButton();

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

    public void runMainWindow() {
        greetingFrame.dispose();
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

    private void makeGreetingWindow() {
        greetingFrame = new JFrame();
        serverTextField = new JTextField();
        nameTextField = new JTextField();
        serverTextField.setBorder(BorderFactory.createTitledBorder("Server"));
        nameTextField.setBorder(BorderFactory.createTitledBorder("User name"));
    }

    private JButton makeConnectButton() {
        JButton button = new JButton("Connect");
        button.addActionListener(e -> {
            String userName = nameTextField.getText();
            String serverAddress = serverTextField.getText();
            if (!transmitter.connect(serverAddress, 4004, userName)) {
                    makeConnectionProblemWindow();
            }
//            } else if (!transmitter.userNameIsValid()) {
//                makeUserInvalidWindow();
//            } else {
//                runMainWindow();
//                //greetingFrame.dispose();
//            }
        });
        return button;
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
        userListWindow = new JTextArea();
        userListWindow.setEditable(false);
        scrollerUserList.setViewportView(userListWindow);
        return scrollerUserList;
    }

    public void makeConnectionProblemWindow() {
        JFrame connectionProblemFrame = new JFrame();
        JLabel connectionProblemMsg = new JLabel("Connection failed");
        connectionProblemFrame.add(connectionProblemMsg);
        connectionProblemFrame.setVisible(true);
        connectionProblemFrame.pack();
    }

    public void makeUserInvalidWindow() {
        JFrame connectionProblemFrame = new JFrame();
        JLabel connectionProblemMsg = new JLabel("User invalid");
        connectionProblemFrame.add(connectionProblemMsg);
        connectionProblemFrame.setVisible(true);
        connectionProblemFrame.pack();
    }

    public void refreshUserList(ArrayList<String> userList) {
        userListWindow.setText("");
        for (String user : userList) {
            userListWindow.append(user);
            userListWindow.append(System.lineSeparator());
        }
    }

    public void refreshMsgField(String msg){
        chatField.append(msg);
        chatField.append(System.lineSeparator());
    }

    public void writeMessage(String str){
        chatField.append(str);
        chatField.append(System.lineSeparator());
    } // эта хуйня дублируется, убрать

    public void onUserConnected() {
        // проверять валидность имени на сервере скорее всего
        // и не добавлять в список а отправлять на сервер
    }

    @Override
    public void onUserDisconnected(Observed observed) {

    }

    @Override
    public void onMessageReceived(Observed observed) {
    }
}
