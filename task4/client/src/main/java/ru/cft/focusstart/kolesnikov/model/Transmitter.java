package ru.cft.focusstart.kolesnikov.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ru.cft.focusstart.kolesnikov.Message;
import ru.cft.focusstart.kolesnikov.MessageType;
import ru.cft.focusstart.kolesnikov.gui.MainWindow;


import java.io.*;
import java.net.Socket;


public class Transmitter {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private MainWindow mainWindow;
    private ObjectMapper mapper;
    private Runnable refreshingUserList = () -> {
        Message msg = getMessageFromServer();
        while (!Thread.currentThread().isInterrupted()) { // тут исправить
            if (msg != null) {
                switch (msg.getMessageType()) {
                    case USER_NAME_VALID:
                        mainWindow.runMainWindow();
                        msg = getMessageFromServer();
                        break;
                    case USER_NAME_INVALID:
                        mainWindow.makeUserInvalidWindow();
                        msg = getMessageFromServer();
                        break;
                    case USER_CONNECTED:  // моеж сделать как-то по другому
                    case DISCONNECTED:
                        System.out.println(msg);
                        notifyUserList(msg);
                        msg = getMessageFromServer();
                        break;
                    case MESSAGE:
                        notifyMsgField(msg);
                        msg = getMessageFromServer();
                        break;
                }
            }
        }
    };
    public Thread refreshingUserListThread = new Thread(refreshingUserList);

    public Transmitter() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> this.close()));
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }

    public void addObserver(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    public boolean connect(String serverAddress, int port, String name) {
        try {
            if (!refreshingUserListThread.isAlive()) {
                socket = new Socket(serverAddress, port);
                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                userTryingConnect(name);
                refreshingUserListThread.start();
            }
            else {
                userTryingConnect(name);
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public void userTryingConnect(String userName) {
        Message message = new Message(MessageType.TRYING_CONNECT);
        message.setUserName(userName);
        send(message);
    }

    public Message getMessageFromServer() {
        Message message = null;
        try {
            String serMes;
            if (bufferedReader != null) {
                serMes = bufferedReader.readLine();
                message = mapper.readValue(serMes, Message.class);
            }
        } catch (IllegalArgumentException e ){
            throw new IllegalArgumentException("Server send null value, maybe we lost server");

        }catch (IOException e) {
            refreshingUserListThread.interrupt();
        }
        return message;
    }

    public void sendMsgToServer(String msg) {
        Message message = new Message(MessageType.MESSAGE);
        message.setMessageVal(msg);
        send(message);
    }

    private void send(Message msg) {
        try {
            bufferedWriter.write(mapper.writeValueAsString(msg));
            bufferedWriter.write(System.lineSeparator());
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void notifyUserList(Message msg) {
        mainWindow.getUserListField().refresh(msg);
        notifyMsgField(msg);
    }

    public void notifyMsgField(Message msg) {
        mainWindow.getChatField().refresh(msg);
    }

    private void close() {
        try {
            send(new Message(MessageType.DISCONNECTED));
            bufferedReader.close();
            bufferedWriter.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
