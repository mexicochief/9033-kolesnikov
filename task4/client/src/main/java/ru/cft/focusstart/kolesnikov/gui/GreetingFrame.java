package ru.cft.focusstart.kolesnikov.gui;

import ru.cft.focusstart.kolesnikov.model.Transmitter;

import javax.swing.*;
import java.awt.*;

public class GreetingFrame {
    private JFrame greetingFrame;
    private JTextField serverNameTextField;
    private JTextField nameTextField;
    private Transmitter transmitter;
    private String userName;
    private String serverAddress;
    private String serverName;
    private int port;

    public GreetingFrame(Transmitter transmitter){
        this.transmitter = transmitter;
    }

    public void runGreetingWindow() {
        makeGreetingWindow();
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        JButton button = makeConnectButton();

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridwidth = 2;
        panel.add(serverNameTextField, constraints);

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

    private void makeGreetingWindow() {
        greetingFrame = new JFrame();
        serverNameTextField = new JTextField();
        nameTextField = new JTextField();
        serverNameTextField.setBorder(BorderFactory.createTitledBorder("Server"));
        nameTextField.setBorder(BorderFactory.createTitledBorder("User name"));
    }

    private JButton makeConnectButton() {
        JButton button = new JButton("Connect");
        button.addActionListener(e -> {
            setCorrectServerInfo();
            if (!transmitter.connect(serverName, port, userName)) {
                makeConnectionProblemWindow();
            }
        });
        return button;
    }

    private void setCorrectServerInfo(){
        userName = nameTextField.getText();
        serverAddress = serverNameTextField.getText();
        serverAddress = serverAddress.replaceAll(" ","");// не уверен, может лучше выдать ошибку если есть пробелы
        int separatorIndex = serverAddress.indexOf(":");
        String strPort;
        if (separatorIndex != -1) {
            serverName = serverAddress.substring(0, separatorIndex);
            strPort = serverAddress.substring(separatorIndex + 1);
            try {
                port = Integer.parseInt(strPort);
            }
            catch (NumberFormatException e){
                throw new NumberFormatException("Illegal server parameters");
            }
        }
    }

    public void makeConnectionProblemWindow() {
        JFrame connectionProblemFrame = new JFrame();
        JLabel connectionProblemMsg = new JLabel("Connection failed");
        connectionProblemFrame.add(connectionProblemMsg);
        connectionProblemFrame.setVisible(true);
        connectionProblemFrame.pack();
    }

    public void disposeGreetingFrame(){
        greetingFrame.dispose();
    }
}
