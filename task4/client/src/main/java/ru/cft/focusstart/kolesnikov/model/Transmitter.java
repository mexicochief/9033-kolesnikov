package ru.cft.focusstart.kolesnikov.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.log4j.Logger;
import ru.cft.focusstart.kolesnikov.Message;
import ru.cft.focusstart.kolesnikov.MessageType;
import ru.cft.focusstart.kolesnikov.gui.window.generalwindow.GeneralWindow;
import ru.cft.focusstart.kolesnikov.gui.widgets.Observer;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Transmitter {
    private ArrayList<Observer> userListObservers = new ArrayList<>();
    private ArrayList<Observer> messageListObservers = new ArrayList<>();
    private ArrayList<GeneralWindow> generalWindows = new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private ObjectMapper mapper;
    private Runnable refreshingUserList = () -> {
        while (!Thread.currentThread().isInterrupted()) {
            Message msg = getMessageFromServer();
            if (msg != null) {
                switch (msg.getMessageType()) {
                    case USER_NAME_VALID:
                        for (GeneralWindow generalWindow : generalWindows) {
                            generalWindow.getStartWindow().dispose();
                            generalWindow.runWindow();
                        }
                        break;
                    case USER_NAME_INVALID:
                        for (GeneralWindow generalWindow : generalWindows) {
                            generalWindow.getStartWindow().makeUserInvalidWindow();
                        }
                        break;
                    case USER_CONNECTED:  // моеж сделать как-то по другому
                    case DISCONNECTED:
                        System.out.println(msg);
                        notifyUserList(msg);
                        break;
                    case MESSAGE:
                        notifyMsgField(msg);
                        break;
                }
            }
        }
    };
    private Thread refreshingUserListThread = new Thread(refreshingUserList);
    private Logger log = Logger.getLogger("Transmitter: ");

    public Transmitter() {
        Runtime.getRuntime().addShutdownHook(new Thread(()->close()));
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }

    public boolean connect(String serverAddress, int port, String name) {
        try {
            if (!refreshingUserListThread.isAlive()) {
                socket = new Socket(serverAddress, port);
                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                userTryingConnect(name);
                refreshingUserListThread.start();
            } else {
                userTryingConnect(name);
            }
            return true;
        } catch (IOException e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public void addWindow(GeneralWindow generalWindow) {
        generalWindows.add(generalWindow);
    }

    public void addUserListObserver(Observer userListObserver) {
        userListObservers.add(userListObserver);
    }

    public void addMessageListObserver(Observer messageListObserver) {
        messageListObservers.add(messageListObserver);
    }

    private void userTryingConnect(String userName) {
        Message message = new Message(MessageType.TRYING_CONNECT);
        message.setUserName(userName);
        send(message);
    }

    private Message getMessageFromServer() {
        Message message = null;
        try {
            String serMes;
            if (bufferedReader != null) {
                serMes = bufferedReader.readLine();
                message = mapper.readValue(serMes, Message.class);
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Server send null value, maybe we lost server");

        } catch (IOException e) {
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
            log.error(e.getMessage());
        }
    }

    private void notifyUserList(Message msg) {
        for (Observer userListObserver : userListObservers) {
            userListObserver.refresh(msg);
            notifyMsgField(msg);
        }
    }

    private void notifyMsgField(Message msg) {
        for (Observer messageListObserver : messageListObservers) {
            messageListObserver.refresh(msg);
        }
    }

    private void close() {
        try {
            send(new Message(MessageType.DISCONNECTED));
            bufferedReader.close();
            bufferedWriter.close();
            socket.close();
        } catch (IOException e) {
            log.error("IOException when try close");
        }
    }
}
